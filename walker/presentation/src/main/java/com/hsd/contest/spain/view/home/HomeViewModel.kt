package com.hsd.contest.spain.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.Routes
import com.hsd.contest.domain.usecase.GetRoutes
import kotlinx.coroutines.launch

class HomeViewModel(private val getRoutes: GetRoutes) : ViewModel() {
    private val _routes = MutableLiveData<Routes>()
    val routes: LiveData<Routes> = _routes

    private val _errorCategories = MutableLiveData<ErrorResponse>()
    val errorCategories: LiveData<ErrorResponse> = _errorCategories


    init {
        viewModelScope.launch {
            getRoutes().fold({ error ->
                _errorCategories.postValue(error)
            }, { domainRoutes ->
                _routes.postValue(domainRoutes)
            })
        }
    }
}