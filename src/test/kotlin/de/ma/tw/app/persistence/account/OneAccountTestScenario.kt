package de.ma.tw.app.persistence.account

import de.ma.tw.app.web.utils.IScenario
import java.util.*

class OneAccountTestScenario: IScenario {

    data class Account(
        val id: UUID = UUID.fromString("53a5388c-42fb-4b35-86f8-2fc37497750e"),
        val username: String = "freiheit",
        val password: String = "freiheit"
    )

    val account = Account()

    override fun before(): List<String> {
        return listOf("one_account_testcase.sql")
    }


}