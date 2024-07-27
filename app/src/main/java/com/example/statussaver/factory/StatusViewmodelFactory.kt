package com.example.statussaver.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.statussaver.repository.StatusRepo
import com.example.statussaver.viewmodel.StatusViewModel

class StatusViewmodelFactory(private val repo: StatusRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatusViewModel(repo) as T
    }
}