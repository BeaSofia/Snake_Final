/**
 * Represents a position in a two-dimensional space
 * @property x value from 0 to the maximum width of the space
 * @property y value from 0 to the maximum height of the space
 */
data class Position(val x: Int, val y: Int)



operator fun Position.plus(dir: Direction) = Position(x + dir.dx, y + dir.dy).normalize()

/**
 * Verify if the position is within the limits of the game and, if not, return the correct position
 */
fun Position.normalize(): Position = when {
    x < 0             -> Position(GRID_WIDTH - 1, y)
    x >= GRID_WIDTH   -> Position(0, y)
    y < 0             -> Position(x, GRID_HEIGHT - 1)
    y >= GRID_HEIGHT  -> Position(x, 0)
    else -> this
}

operator fun Position.minus(pos: Position) : Direction? {
    val dx = x - pos.x
    val dy = y - pos.y
    return when {
        dx == 0 && (dy == 1 || dy == -(GRID_HEIGHT-1)) -> Direction.DOWN
        dx == 0 && (dy == -1 || dy == GRID_HEIGHT-1)   -> Direction.UP
        (dx == 1 || dx == -(GRID_WIDTH-1)) && dy ==0   -> Direction.RIGHT
        (dx == -1 || dx == GRID_WIDTH-1) && dy ==0     -> Direction.LEFT
        else -> null
    }
}



