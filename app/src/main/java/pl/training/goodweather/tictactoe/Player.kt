package pl.training.goodweather.tictactoe

enum class Player {

    CIRCLE, CROSS;

    fun reverse(): Player {
        return if (this == CROSS) CIRCLE else CROSS
    }

}