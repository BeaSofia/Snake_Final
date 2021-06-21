import pt.isel.canvas.DOWN_CODE
import pt.isel.canvas.LEFT_CODE
import pt.isel.canvas.RIGHT_CODE
import pt.isel.canvas.UP_CODE

/**
 * All the possible directions
 *
 * @property dx X-axis offset of the direction
 * @property dy Y-axis offset of the direction
 */
enum class Direction(val dx: Int, val dy: Int) {
    LEFT(-1, 0), UP(0, -1), RIGHT(1, 0), DOWN(0, 1)
}

/**
 *  Returns the direction associated with the indicated key.
 *  @param s The snake
 *  @param ke The key code
 *  @return Snake associated with the key
 */
fun newDirection(s: Snake, ke: Int): Snake {
    return when (ke) {
        UP_CODE    -> s.copy(direction = Direction.UP)
        DOWN_CODE  -> s.copy(direction = Direction.DOWN)
        LEFT_CODE  -> s.copy(direction = Direction.LEFT)
        RIGHT_CODE -> s.copy(direction = Direction.RIGHT)
        else -> s
    }
}

fun trapped(g: Game, ke: Int): Direction {
    val head = g.snake.body.first() + newDirection(g.snake, ke).direction
    return if (head in g.snake.body || head in g.wall) g.snake.direction
    else newDirection(g.snake, ke).direction
}

/**
 * Performs the movement of the snake in the game
 */

fun Snake.move(head: Position, grow: Int): Snake =
    if (grow > 0) copy(body = listOf(head) + body, toGrow = grow - 1)
    else copy(body = listOf(head) + body.dropLast(1), toGrow = grow)

fun Game.lose(): Boolean = snake.body.first() + Direction.UP in snake.body + wall &&
    snake.body.first() + Direction.DOWN in snake.body + wall &&
    snake.body.first() + Direction.RIGHT  in snake.body + wall &&
    snake.body.first() + Direction.LEFT in snake.body + wall
