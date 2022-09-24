package com.talaloan.android.data.local

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.talaloan.android.Constant
import com.talaloan.android.TalaApp
import com.talaloan.android.data.LoanInfo
import com.talaloan.android.data.Locale
import com.talaloan.android.data.Repository
import java.io.IOException


class JSONRepositoryImpl(private val talaApp: TalaApp): Repository {
    private val gson = Gson()
    private var error = ""

    override fun getError() = error

    override fun getLocales(): Map<String, Locale>? {
        val json = getJsonFromAssets(Constant.LOCALE_JSON)
        Log.d(TAG, "getLocales json=$json")
        val listType = object : TypeToken<Map<String, Locale>?>() {}.type
        return gson.fromJson(json, listType)
    }

    override fun getLoanInfo(): List<LoanInfo>? {
        val json = getJsonFromAssets(Constant.DATA_JSON)
        Log.d(TAG, "getLoanInfo json=$json")
        val listType = object : TypeToken<List<LoanInfo?>?>() {}.type
        return gson.fromJson(json, listType)
    }

    private fun getJsonFromAssets(fileName: String): String? {
        return try {
            val inputStream = talaApp.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, charset("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            error = e.message ?: "Failed to load file $fileName"
            null
        }
    }

    companion object {
        private const val TAG = "JSONRepositoryImpl"
    }
}