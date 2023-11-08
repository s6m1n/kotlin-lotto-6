package lotto.controller

import lotto.model.LottoNumberDispenser
import lotto.model.domain.Lotto
import lotto.model.domain.Money
import lotto.view.InputConsole
import lotto.view.InputConsole.getNumber
import lotto.view.OutputConsole
import lotto.view.OutputConsole.printErrorMessage
import lotto.view.OutputConsole.promptForMoney

class LottoController {

    fun start() {
        promptForMoney()
        val money = getValidMoney()

        val lottoTickets = buyLottoTickets(money)

        OutputConsole.promptForWinningNumbers()
        val winningNumbers = getValidWinningNumbers()
    }

    private fun getValidMoney(): Money =
        try {
            Money(getNumber())
        } catch (exception: IllegalArgumentException) {
            printErrorMessage(exception)
            getValidMoney()
        }

    private fun buyLottoTickets(money: Money): List<Lotto> {
        val numberDispenser = LottoNumberDispenser()
        return numberDispenser.buyTickets(money.amount).also {
            OutputConsole.printLottoTickets(it)
        }
    }

    private fun getValidWinningNumbers(): Lotto =
        try {
            val numbers = InputConsole.getParsedNumbers()
            Lotto(numbers)
        } catch (exception: IllegalArgumentException) {
            printErrorMessage(exception)
            getValidWinningNumbers()
        }
}