import pt.isel.canvas.Canvas

fun Canvas.drawFruits(game: Game) {
     if (game.apple != null && game.level==1)
        drawImage(
        "Snake.png|0,${SPRITE_DIV * 3},$SPRITE_DIV,$SPRITE_DIV",
        game.apple.x * CELL_SIDE, game.apple.y * CELL_SIDE, CELL_SIDE, CELL_SIDE
    )
    else if (game.waterMelon != null && game.level==2)
         drawImage(
             "melancia",
             game.waterMelon.x * CELL_SIDE, game.waterMelon.y * CELL_SIDE, CELL_SIDE, CELL_SIDE
         )
}



fun fruitValidPosition(game: Game, head: Position): Position? {
    val lst = (ALL_POSITIONS - game.wall - game.snake.body - head)
    return if (lst.isEmpty()) null else lst.random()
}

fun createInitialApple()=(ALL_POSITIONS-createInitialWallsLevel1()-Position(GRID_WIDTH/2, GRID_HEIGHT/2)).random()


fun createInitialWatermelon()=(ALL_POSITIONS-createInitialWallsLevel2()-Position(GRID_WIDTH/2, GRID_HEIGHT/2)).random()



