import pt.isel.canvas.*

/**
 * The main function of the game
 */

fun main() {
    onStart {
        loadSounds("eat")
        val cv = Canvas(GRID_WIDTH * CELL_SIDE, GRID_HEIGHT * CELL_SIDE + STATUS_BAR, BLACK)
        val commands = Commands (sound = false, grid = false, stages = false)
        var game = Game(
            Snake(
                listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)),
                Direction.RIGHT, stopped = false, 4
            ), createInitialWallsLevel1(), createInitialApple(), 0,null,commands,1
        )
        cv.drawGame(game)


        cv.onKeyPressed {
            println(it.code)
            game = game.copy(game.snake.copy(direction = trapped(game, it.code)))
            game = game.turnOnAndOff(it)
        }

        cv.onTimeProgress(250) {
            game = game.moveSnake()
            cv.drawGame(game)

        }

        cv.onTimeProgress(5000) {
            game = createWalls(game)
            cv.drawGame(game)
        }

    }
    onFinish { }
}



