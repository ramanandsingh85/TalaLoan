package com.talaloan.android

import com.talaloan.android.data.Loan
import com.talaloan.android.data.LoanInfo
import com.talaloan.android.data.Locale
import com.talaloan.android.data.Repository

class ProxyJSONRepositoryImpl: Repository {
    override fun getError(): String {
        return ""
    }

    override fun getLocales(): Map<String, Locale>? {
        val map = mutableMapOf<String, Locale>()
        map["ke"] = Locale("KSh ", 30000, 10800000)
        return map
    }

    override fun getLoanInfo(): List<LoanInfo>? {
        val list = mutableListOf<LoanInfo>()
        list.add(LoanInfo("ke", Loan("due", "silver", 11200, 1562965200000, null), 1562487461740, "test KE"))
        list.add(LoanInfo("ke", Loan("paid", "silver", null, null, null), 1562746791839, "test KE"))
        list.add(LoanInfo("ke", Loan("due", "silver", 11200, 1562965200000, null), 1561018532937, "test KE"))
        list.add(LoanInfo("ke", Loan("approved", "silver", null, null, 16000), 1560332572283, "test KE"))
        return list
    }
}