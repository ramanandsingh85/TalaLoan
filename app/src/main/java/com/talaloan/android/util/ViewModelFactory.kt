package com.talaloan.android.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.talaloan.android.TalaApp
import com.talaloan.android.data.local.JSONRepositoryImpl
import com.talaloan.android.ui.MainViewModel
import com.talaloan.android.ui.MainUseCase

class ViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                //These objects can be injected using dagger2 etc
                val repository = JSONRepositoryImpl(application as TalaApp)
                val useCase = MainUseCase(repository)
                MainViewModel(useCase, application) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel not found!")
            }
        }
    }
}