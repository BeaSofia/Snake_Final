import pt.isel.canvas.Canvas

/**
 * Represents the snake composed by head´s and tail's position and their direction
 * @property body the list of the snake's positions
 * @property direction the direction of the snake
 * @property stopped show if the snake is moving or stopped
 * @property toGrow the amount of body parts that need to be add to the snake's body
 */
data class Snake(val body: List<Position>, val direction: Direction, val stopped: Boolean, val toGrow: Int)

/**
 * Draw the head of the Snake
 * @receiver where it will be draw
 * @param snake Snake to be draw
 */
fun Canvas.drawHead(snake: Snake) {
    val pos = snake.body.first()
    val posAfter = if (snake.body.size > 1) snake.body[1] else pos
    val x = snake.body.first().x * CELL_SIDE
    val y = snake.body.first().y * CELL_SIDE
    val dir2 = pos - posAfter  //entrada

    // The right snakeHead image for each direction
    val (spriteX, spriteY) = when (dir2) {
        Direction.LEFT  -> Pair(3, 1)
        Direction.RIGHT -> Pair(4, 0)
        Direction.UP  -> Pair(3, 0)
        Direction.DOWN -> Pair(4, 1)
        else -> Pair(0, 3)
    }

    if (snake.body.size >1) drawImage(
        "snake.png|${spriteX*SPRITE_DIV},${spriteY*SPRITE_DIV},$SPRITE_DIV,$SPRITE_DIV",
        x ,
        y ,
        CELL_SIDE,
        CELL_SIDE)
}

/**
 * Draw the body of the Snake
 * @receiver where it will be draw
 * @param snake Snake to be draw
 */
fun Canvas.drawBody(snake: Snake) {
    val body = snake.body
    body.forEachIndexed { index, pos ->
        if (index in 1 until body.size - 1) drawSlice(pos, body[index - 1], body[index + 1])
    }
}


// Function that tells us which part of the body will be drawn according to the anterior and posterior

fun Canvas.drawSlice(pos: Position, posBefore: Position, posAfter: Position) {
    val dir1 = pos - posBefore  //entrada
    val dir2 = pos - posAfter // saida

    if (dir1 == null || dir2 == null) return Unit

    val (spriteX, spriteY) = when {
        dir1.dx == 0 && dir2.dx == 0                      -> Pair(2, 1)
        dir1.dy == 0 && dir2.dy == 0                      -> Pair(1, 0)
        dir1 == Direction.LEFT && dir2 == Direction.DOWN  -> Pair(0, 1)
        dir1 == Direction.RIGHT && dir2 == Direction.DOWN -> Pair(2, 2)
        dir1 == Direction.LEFT && dir2 == Direction.UP    -> Pair(0, 0)
        dir1 == Direction.RIGHT && dir2 == Direction.UP   -> Pair(2, 0)
        dir1 == Direction.UP && dir2 == Direction.LEFT    -> Pair(0, 0)
        dir1 == Direction.DOWN && dir2 == Direction.LEFT  -> Pair(0, 1)
        dir1 == Direction.UP && dir2 == Direction.RIGHT   -> Pair(2, 0)
        dir1 == Direction.DOWN && dir2 == Direction.RIGHT -> Pair(2, 2)
        else -> Pair(0, 3)
    }
    drawImage(
        "snake.png|${SPRITE_DIV * spriteX},${SPRITE_DIV * spriteY},$SPRITE_DIV,$SPRITE_DIV",
        pos.x *CELL_SIDE,
        pos.y*CELL_SIDE,
        CELL_SIDE,
        CELL_SIDE
    )
}

// Function that tells us which part of the body will be drawn according to the anterior and posterior
fun Canvas.drawTail(snake: Snake) {
    val pos = snake.body.last()
    val posBefore = if (snake.body.size > 1) snake.body[snake.body.lastIndex-1] else pos
    val dir1 = pos - posBefore ?: return   //entrada

    val (spriteX, spriteY) = when (dir1) {
        Direction.RIGHT -> Pair(3, 3)
        Direction.LEFT  -> Pair(4, 2)
        Direction.DOWN  -> Pair(3, 2)
        Direction.UP    -> Pair(4,3)
    }

    if (snake.body.size >1) drawImage(
        "snake.png|${spriteX*SPRITE_DIV},${spriteY*SPRITE_DIV},$SPRITE_DIV,$SPRITE_DIV",
        pos.x * CELL_SIDE,
        pos.y * CELL_SIDE,
        CELL_SIDE,
        CELL_SIDE
    )
}










