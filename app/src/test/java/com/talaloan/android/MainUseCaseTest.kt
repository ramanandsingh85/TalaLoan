package com.talaloan.android

import com.talaloan.android.ui.MainUseCase
import org.junit.Assert
import org.junit.Test

class MainUseCaseTest {
    @Test
    fun getNextLoanIndex_isCorrect() {
        val repository = ProxyJSONRepositoryImpl()
        val useCase = MainUseCase(repository)

        Assert.assertEquals("Case 0: ", 0, useCase.getNextLoanIndex("ke", repository.getLocales()!!, repository.getLoanInfo()!!))
        Assert.assertEquals("Case 1: ", 1, useCase.getNextLoanIndex("ke", repository.getLocales()!!, repository.getLoanInfo()!!))
        Assert.assertEquals("Case 2: ", 2, useCase.getNextLoanIndex("ke", repository.getLocales()!!, repository.getLoanInfo()!!))
        Assert.assertEquals("Case 3: ", 3, useCase.getNextLoanIndex("ke", repository.getLocales()!!, repository.getLoanInfo()!!))
        Assert.assertEquals("Case 4: ", 0, useCase.getNextLoanIndex("ke", repository.getLocales()!!, repository.getLoanInfo()!!))
    }
}