package com.talaloan.android.data

/**
 * Main JSON repository responsible for reading JSON files stored in asset folder
 */
interface Repository {
    /**
     * Gets the error occurred during JSON file reading
     */
    fun getError(): String

    /**
     * Gets the locales stored in JSON file
     */
    fun getLocales(): Map<String, Locale>?

    /**
     * Gets the loan information stored in JSON file
     */
    fun getLoanInfo(): List<LoanInfo>?
}