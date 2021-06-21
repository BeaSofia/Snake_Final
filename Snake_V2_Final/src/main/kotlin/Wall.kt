import pt.isel.canvas.Canvas
/**
 * Adds to the list of walls, initially empty, the walls that are drawn
 */
fun createWalls(game: Game) = game.copy(wall = game.wall + validWallPosition(game))

/**
 * Draws all the walls of the game
 * @receiver where it will be drawn
 * @param game the information by the game
 */
fun Canvas.drawWalls(game: Game) {
    val str = if (game.level == 1) "bricks.png" else "brickAzul"
    game.wall.forEach { drawImage(str, it.x * CELL_SIDE, it.y * CELL_SIDE, CELL_SIDE, CELL_SIDE) }
}

/**
 * All positions available in the game
 */
val ALL_POSITIONS: List<Position> =
    (0 until GRID_HEIGHT * GRID_WIDTH).map { Position(it % GRID_WIDTH, it / GRID_WIDTH)}

/**
 * Remove from the list of ALL_POSITIONS, the positions of the walls in game, the position of the snake and the fruits,
 * choose the first one randomly
 */
fun validWallPosition(game: Game): Position {
    val appleNotNull = game.apple ?: Position(0, 0)
    val waterMelonNotNull = game.apple ?: Position(0, 0)
    return (ALL_POSITIONS - game.wall - game.snake.body - appleNotNull - waterMelonNotNull).random()
}


// Create a list of the walls in level 1 and level 2, in the begging of which level
fun createInitialWallsLevel1() = listOf(
        //HORIZONTAIS
        Position(0, 0), Position(1, 0), Position(2, 0), Position(17, 0), Position(18, 0),
        Position(19, 0), Position(0, 15), Position(1, 15), Position(2, 15), Position(17, 15),
        Position(18, 15),

        //VERTICAIS
        Position(0, 1), Position(0, 2), Position(0, 3), Position(0, 12), Position(0, 13),
        Position(0, 14), Position(19, 1), Position(19, 2), Position(19, 3),
        Position(19, 12), Position(19, 13), Position(19, 14), Position(19, 15)
    )


fun createInitialWallsLevel2() = listOf(
    Position(0, 0), Position(1, 0),  Position(18, 0), Position(19, 0), Position(0, 15),
    Position(1, 15), Position(18, 15), Position(0, 1), Position(0, 14), Position(19, 1),
    Position(19, 14), Position(19, 15),
    Position(1, 5), Position(2, 5),  Position(3, 5), Position(4, 4), Position(5, 3),
    Position(6, 2), Position(7, 1), Position(8, 1),  Position(10, 2),
    Position(9, 3), Position(10, 5),  Position(11, 5),
    Position(9,4),
    Position(8, 12), Position(9, 12),  Position(10, 12), Position(11, 11),
    Position(12, 10), Position(13, 9), Position(14, 8), Position(15, 8),
    Position(16, 10), Position(16, 11), Position(17, 12),  Position(18, 12),
    Position(17,9)

    )

