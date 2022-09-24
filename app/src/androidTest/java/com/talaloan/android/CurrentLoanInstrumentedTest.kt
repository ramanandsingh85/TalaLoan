package com.talaloan.android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.talaloan.android.data.Loan
import com.talaloan.android.data.LoanInfo
import com.talaloan.android.data.Locale
import com.talaloan.android.ui.CurrentLoan
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for view logics.
 */
@RunWith(AndroidJUnit4::class)
class CurrentLoanInstrumentedTest {
    @Before
    fun setUp(){
        //Setup common things like class level object for currentLoan
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.talaloan.android", appContext.packageName)
    }

    @Test
    fun getLoanStatusImageResId_isCorrect() {
        // We can use here mockito
        val locale = Locale("KSh ", 30000, 10800000)
        val loanInfo0 = LoanInfo("ke", Loan("due", "silver", 11200, 1562965200000, null), 1562487461740, "test KE")

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val currentLoan0 = CurrentLoan(appContext, locale, loanInfo0)

        Assert.assertEquals("Case 0:", R.drawable.img_loan_status_apply, currentLoan0.getLoanStatusImageResId())

        val loanInfo1 = LoanInfo("ke", Loan("approved", "silver", 11200, 1562965200000, null), 1562487461740, "test KE")
        val currentLoan1 = CurrentLoan(appContext, locale, loanInfo1)
        Assert.assertEquals("Case 1:", R.drawable.img_loan_status_approved, currentLoan1.getLoanStatusImageResId())

        val loanInfo2 = LoanInfo("ke", Loan("paid", "silver", 11200, 1562965200000, null), 1562487461740, "test KE")
        val currentLoan2 = CurrentLoan(appContext, locale, loanInfo2)
        Assert.assertEquals("Case 2:", R.drawable.img_loan_status_paidoff, currentLoan2.getLoanStatusImageResId())
    }

    /**
     * For simplicity just testing for english.
     * If need to support local then read strings from string.xml and format properly.
     */
    @Test
    fun getLoanMessageText_isCorrect() {
        // We can use here mockito
        val repository = ProxyJSONRepositoryImpl()
        val locales = repository.getLocales()!!
        val localeLoans = repository.getLoanInfo()!!
        val locale = locales["ke"]!!

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val currentLoan0 = CurrentLoan(appContext, locale, localeLoans[0])
        Assert.assertEquals("Case 0:", "Qualify for a loans up to KSh  30000 over time.", currentLoan0.getLoanMessageText())

        val currentLoan1 = CurrentLoan(appContext, locale, localeLoans[1])
        Assert.assertEquals("Case 1:", "Apply for another loan any time you want and grow your limit.", currentLoan1.getLoanMessageText())

        val currentLoan2 = CurrentLoan(appContext, locale, localeLoans[2])
        Assert.assertEquals("Case 2:", "Qualify for a loans up to KSh  30000 over time.", currentLoan2.getLoanMessageText())

        val currentLoan3 = CurrentLoan(appContext, locale, localeLoans[3])
        Assert.assertEquals("Case 3:", "You've been approved for KSh  16000.", currentLoan3.getLoanMessageText())
    }
}