package com.hsd.contest.spain.view.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.ListMunicipality
import com.hsd.contest.domain.entities.ListProvinces
import com.hsd.contest.domain.entities.WeatherInfo
import com.hsd.contest.domain.usecase.GetMunicipality
import com.hsd.contest.domain.usecase.GetProvinces
import com.hsd.contest.domain.usecase.GetWeather
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getProvinces: GetProvinces,
    private val getMunicipality: GetMunicipality,
    private val getWeather: GetWeather
) : ViewModel() {

    private val _provinces = MutableLiveData<ListProvinces>()
    val provinces: LiveData<ListProvinces> = _provinces

    private val _municipalities = MutableLiveData<ListMunicipality>()
    val municipalities: LiveData<ListMunicipality> = _municipalities


    private val _weather = MutableLiveData<WeatherInfo>()
    val weather: LiveData<WeatherInfo> = _weather

    private val _error = MutableLiveData<ErrorResponse>()
    val error: LiveData<ErrorResponse> = _error

    init {
        viewModelScope.launch {
            getProvinces().fold({ error ->
                _error.postValue(error)
            }, { data ->
                _provinces.postValue(data)
            })
        }
    }

    fun getListMunicipality(code: String) {
        viewModelScope.launch {
            getMunicipality(code = code).fold({ error ->
                _error.postValue(error)
            }, { data ->
                _municipalities.postValue(data)
            })
        }
    }

    fun weather(code: String, position: String) {
        viewModelScope.launch {
            getWeather(code = code, position = position).fold({ error ->
                _error.postValue(error)
            }, { data ->
                _weather.postValue(data)
            })
        }
    }
}