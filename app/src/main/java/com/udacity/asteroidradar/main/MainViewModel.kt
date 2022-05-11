package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.launch
import repository.AsteroidRepository
import java.lang.Exception

class MainViewModel(
    application: Application) : AndroidViewModel(application){

    // repository
    private val repo = AsteroidRepository(AsteroidDatabase.getInstance(application))

    // pic of the day
    val pic: LiveData<PictureOfDay> = repo.pic

    private val todaysAsteroids: LiveData<List<Asteroid>> = repo.today

    // potentially hazardous asteroids
    private val hazard: LiveData<List<Asteroid>> = repo.hazardous

    // all asteroids
    private val all: LiveData<List<Asteroid>> = repo.asteroids

    // single asteroid
    private val _asteroid = MutableLiveData<Asteroid>()
    val asteroid get() = _asteroid

    // network request status
    private val _status = MutableLiveData<String>()
    private val status: LiveData<String> = _status

    private var filter: MutableLiveData<Int> = MutableLiveData()
    val filteredAsteroids: LiveData<List<Asteroid>> =
    filter.switchMap {
        when (it) {
            0 -> todaysAsteroids
            1 -> all
            2 -> hazard
            else -> {
                all
            }
        }
    }

    fun selectFilter(selectedFilter: Int) {
        filter.value = selectedFilter
    }

    init {
        refresh()
        selectFilter(1)
    }

    val title = Transformations.map(pic) {
        it?.title ?: "Rabbit in a snow storm"
    }


    fun onAsteroidClicked(aster: Asteroid) {
        _asteroid.value = aster
    }

    private fun refresh() {
        viewModelScope.launch {
            try {
                repo.refreshAsteroids()
            } catch (e: Exception) {
                _status.value = e.message
            }
        }
    }
}