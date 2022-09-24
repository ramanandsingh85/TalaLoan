package com.talaloan.android.ui

import android.app.Application
import androidx.lifecycle.*
import com.talaloan.android.data.LoanInfo
import com.talaloan.android.data.Locale

class MainViewModel(private val useCase: MainUseCase, application: Application): AndroidViewModel(application), MainUseCase.LoanDataListener {
    private val _currentLoan = MutableLiveData<CurrentLoan>()
    val currentLoan: LiveData<CurrentLoan> = _currentLoan
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun start() {
        useCase.start(viewModelScope, this)
    }

    fun fetchNextLoanData() {
        useCase.getNextLoanData(this)
    }

    override fun onCleared() {
        super.onCleared()
        //clear variables if required
        useCase.resetIndexes()
    }

    override fun onSuccess(locale: Locale, loanInfo: LoanInfo) {
        _currentLoan.value = CurrentLoan(getApplication(), locale, loanInfo)
    }

    override fun onError(description: String) {
        _error.value = description
    }
}