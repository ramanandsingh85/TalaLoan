package com.talaloan.android.ui

import android.util.Log
import com.talaloan.android.data.LoanInfo
import com.talaloan.android.data.Locale
import com.talaloan.android.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainUseCase(private val repository: Repository) {
    private var locales: Map<String, Locale>? = null
    private var loans: List<LoanInfo>? = null

    private var localeIndex = 0
    private val localeLoanIndexes = mutableMapOf<String, Int>()

    fun start(scope: CoroutineScope, listener: LoanDataListener) {
        scope.launch {
            locales = repository.getLocales()
            loans = repository.getLoanInfo()

            Log.d(TAG, "start locales=$locales, loans=$loans")
            getNextLoanData(listener)
        }
    }

    fun getNextLoanData(listener: LoanDataListener) {
        if (locales?.isNullOrEmpty() == true) {
            listener.onError(repository.getError())
            Log.d(TAG, "getNextLoanData error=${repository.getError()}")
        } else if (loans?.isNullOrEmpty() == true) {
            listener.onError(repository.getError())
            Log.d(TAG, "getNextLoanData error=${repository.getError()}")
        } else {
            val nextLocale = locales!!.keys.elementAt(localeIndex)
            val localeLoans = getLocaleLoans(nextLocale, loans!!)
            locales!![nextLocale]?.let {
                val nextLoanIndex = getNextLoanIndex(nextLocale, locales!!, localeLoans)
                listener.onSuccess(it, localeLoans[nextLoanIndex])
            }
            Log.d(TAG, "getNextLoanData nextLocale=$nextLocale, localeLoans=$localeLoans")
        }
    }

    fun resetIndexes() {
        localeIndex = 0
        localeLoanIndexes.clear()
        Log.d(TAG, "resetIndexes")
    }

    fun getNextLoanIndex(locale: String, locales: Map<String, Locale>, loans: List<LoanInfo>): Int {
        //Get existing index if any and increment it
        var index = -1
        localeLoanIndexes[locale]?.let {
            index = it
        }
        index++

        if (index == loans.size) {
            // reset the index
            index %= loans.size

            //Move to next locale
            localeIndex++
            if (localeIndex == locales.size) {
                localeIndex = 0
            }
        }

        //Save the index for next iteration
        localeLoanIndexes[locale] = index

        return index
    }

    private fun getLocaleLoans(locale: String, loans: List<LoanInfo>): List<LoanInfo> {
        return loans.filter { it.locale == locale }
    }

    interface LoanDataListener {
        fun onSuccess(locale: Locale, loanInfo: LoanInfo)
        fun onError(description: String)
    }

    companion object {
        private const val TAG = "MainUseCase"
    }
}