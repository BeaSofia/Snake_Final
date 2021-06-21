import pt.isel.canvas.Canvas
import pt.isel.canvas.*

const val CELL_SIDE = 32
const val GRID_WIDTH = 20
const val GRID_HEIGHT = 16
const val SPRITE_DIV = 64
const val STATUS_BAR = CELL_SIDE
const val TEXT_BASE = 6 // Vertical separation of text from base


/**
 * Represents the game composed by the snake, the walls, the fruit, the score, the commands and the level of the game
 * @property snake the snake in game
 * @property wall the list of all walls in the game
 * @property apple the position of the apple, which can be null
 * @property score the score of the game
 * @property waterMelon the position of the apple, which can be null
 * @property commands  the commands of the game
 * @property level the level that we are
 */
data class Game(val snake: Snake, val wall: List<Position>, val apple: Position?, val score: Int,
                val waterMelon: Position?, val commands: Commands, val level: Int)

/**
 * Represents the commands of the game
 * @property sound turn off or on the sound of the snake eating the apple
 * @property grid show or hide the grid
 * @property stages show or hide the stage that we are in
 */
data class Commands (val sound: Boolean, val grid: Boolean, val stages: Boolean)


/**
 * The function responsible for major game modifications
 * @receiver Game
 * @return Game
 */

fun Game.moveSnake(): Game {

    return when (val newHead = snake.body.first() + snake.direction) {
        in wall, in snake.body -> copy(snake = snake.copy(stopped = true))
        apple -> {
                if (this.commands.sound) playSound("eat")
                copy(
                    snake = snake.move(newHead, snake.toGrow + 5), apple = fruitValidPosition(this, newHead),
                    score = score + 1) }
        waterMelon -> {
            if (this.commands.sound) playSound("eat")
            copy(
                snake = snake.move(newHead, snake.toGrow + 5), waterMelon = fruitValidPosition(this, newHead),
                score = score + 1)
        }
            else -> copy(snake = snake.copy(stopped = false).move(newHead, snake.toGrow))
    }
}

//Function that determines whether game features are turned on or off
fun Game.turnOnAndOff (ke: KeyEvent): Game =
    when {
        ke.char == 's' -> copy(commands = commands.copy (sound = false))
        ke.char =='S' -> copy(commands = commands.copy (sound = true))
        ke.char =='g' -> copy(commands = commands.copy (grid =  false))
        ke.char =='G' -> copy(commands = commands.copy (grid = true))
        ke.char =='l' -> copy(commands = commands.copy (stages= false))
        ke.char =='L' -> copy(commands = commands.copy (stages = true))
        ke.code == 32 -> if (snake.body.size >= 60 && snake.stopped && level!=2) nextLevel() else this
        else -> this
    }

//Function that shows game status based on size, level and win or lose
fun Canvas.gameStatus(g: Game) {
    val win = if (g.level == 1)g.snake.body.size >= 60 else g.snake.body.size >= 70 && g.snake.stopped

    drawRect(0, height - STATUS_BAR, width, STATUS_BAR, 0x333333)
    drawText(CELL_SIDE, height - TEXT_BASE, "Size: ${g.snake.body.size}", if (win) GREEN else WHITE, 25)
    drawText(5 * CELL_SIDE, height - TEXT_BASE, "Score: ${g.score}", WHITE, 25)
    if (g.snake.stopped && g.lose() && !win) drawText(15 * CELL_SIDE, height - TEXT_BASE, "You Lose", YELLOW, 25)
    if (win && g.snake.stopped) drawText(15 * CELL_SIDE, height - TEXT_BASE, "You Win", YELLOW, 25)
    if (g.commands.stages)
    drawText(CELL_SIDE*10, height - TEXT_BASE, "Level: ${g.level}", WHITE, 25)

}

// Create the grid of the game
fun Canvas.drawGrid() {
        (CELL_SIDE..height step CELL_SIDE).forEach {
            drawLine(0, it, width, it, WHITE, 1)
        }
        (CELL_SIDE..width step CELL_SIDE).forEach {
            drawLine(it, 0, it, width, WHITE, 1)
        }
}

//Function that creates the next level
fun Game.nextLevel (): Game{
    val commands = Commands (false, false, false)
    return Game(Snake(
        listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)),
        Direction.RIGHT, stopped = false, 4
    ), createInitialWallsLevel2(),null, score, createInitialWatermelon(),commands,2
    )
}


/**
 * Draws the entire area of the game: Snake(head, body and tail),
 * Walls, Fruits and the Bar with the status
 */

fun Canvas.drawGame(game: Game) {
    erase()
    if (game.commands.grid) drawGrid()
    drawHead(game.snake)
    drawBody(game.snake)
    drawTail(game.snake)
    drawWalls(game)
    drawFruits(game)
    gameStatus(game)
}



