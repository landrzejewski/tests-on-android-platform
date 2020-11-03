package pl.training.goodweather.tictactoe

import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class TicTacToe(crossFields: Set<Int> = mutableSetOf(), circleFields: Set<Int> = mutableSetOf()) {

    private val crossFields: MutableSet<Int>
    private val circleFields: MutableSet<Int>
    var player: Player = Player.CROSS
        private set

    private fun validateInitialGameState() {
        require((Collections.disjoint(crossFields, circleFields) || takenFields().size >= BOARD_SIZE))
    }

    val isGameOver: Boolean
        get() = allFieldsAreTaken() || playerTookWinningSequence()

    private fun allFieldsAreTaken() = BOARD_SIZE - takenFields().size == 0

    private fun takenFields() =  Stream.concat(crossFields.stream(), circleFields.stream()).collect(Collectors.toSet())

    private fun playerTookWinningSequence() = winningSequences.stream().anyMatch { playerFields().containsAll(it) }

    private fun playerFields() = if (player === Player.CROSS) crossFields else circleFields

    private fun isOnBoard(field: Int) = field < BOARD_SIZE + 1

    fun makeMove(field: Int): Boolean {
        if (isTaken(field) || !isOnBoard(field)) {
            return false
        }
        playerFields().add(field)
        player = player.reverse()
        return true
    }

    private fun isTaken(field: Int)= takenFields().contains(field)

    fun getCrossFields(): Set<Int> = Collections.unmodifiableSet(crossFields)

    fun getCircleFields(): Set<Int> = Collections.unmodifiableSet(circleFields)

    companion object {

        private const val BOARD_SIZE = 9

        private val winningSequences = setOf(
            setOf(1, 2, 3), setOf(4, 5, 6), setOf(7, 8, 9),
            setOf(1, 4, 7), setOf(2, 5, 8), setOf(3, 6, 9),
            setOf(1, 5, 9), setOf(3, 5, 7)
        )

    }

    init {
        this.crossFields = HashSet(crossFields)
        this.circleFields = HashSet(circleFields)
        validateInitialGameState()
    }

}