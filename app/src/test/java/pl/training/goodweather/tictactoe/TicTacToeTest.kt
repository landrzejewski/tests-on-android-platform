package pl.training.goodweather.tictactoe

import org.junit.Assert.*
import org.junit.Test

class TicTacToeTest {

    private var ticTacToe = TicTacToe()

    @Test
    fun should_end_game_when_all_fields_are_taken() {
        ticTacToe = TicTacToe(setOf(1, 3, 5, 8), setOf(2, 4, 6, 7))
        ticTacToe.makeMove(9)
        assertTrue(ticTacToe.isGameOver)
    }

    @Test
    fun should_end_game_when_player_took_winning_sequence() {
        ticTacToe = TicTacToe(setOf(1, 2, 3), setOf(4, 8, 9))
        assertTrue(ticTacToe.isGameOver)
    }

    @Test
    fun should_allow_only_free_fields_to_be_taken() {
        ticTacToe.makeMove(1)
        assertFalse(ticTacToe.makeMove(1))
    }

    @Test
    fun should_allow_only_on_board_fields_to_be_taken() {
        assertFalse(ticTacToe.makeMove(10))
    }

    @Test
    fun should_change_player_after_field_is_taken() {
        val player = ticTacToe.player
        ticTacToe.makeMove(1)
        assertNotEquals(ticTacToe.player, player)
    }

    @Test
    fun should_not_change_player_after_field_is_not_taken() {
        ticTacToe.makeMove(1)
        val player = ticTacToe.player
        ticTacToe.makeMove(1)
        assertEquals(ticTacToe.player, player)
    }

    @Test(expected = IllegalArgumentException::class)
    fun should_throw_exception_when_initial_game_state_is_invalid() {
        TicTacToe(setOf(1, 3, 5, 8), setOf(1, 4, 6, 7, 9))
    }

}