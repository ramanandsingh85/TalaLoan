package com.talaloan.android.data

import com.talaloan.android.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

data class LoanInfo(val locale: String, val loan: Loan?, val timestamp: Long, val username: String)

data class Loan(val status: String, val level: String, val due: Int?, val dueDate: Long?, val approved: Int?)