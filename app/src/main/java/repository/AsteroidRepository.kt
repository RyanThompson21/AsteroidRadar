package repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class AsteroidRepository(private val db: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(db.asteroidDao.getAllAsteroids()) {
            it.asDomainModel()
        }

    val today: LiveData<List<Asteroid>> = Transformations.map(
        db.asteroidDao.getTodaysAsteroids(
            getTodaysDate()
        )
    ) {
        it.asDomainModel()
    }

    val hazardous: LiveData<List<Asteroid>> = Transformations.map(
        db.asteroidDao.getPotentiallyHazardousFromToday(
            getTodaysDate(), true
        )
    ) {
        it.asDomainModel()
    }

    // picture of day
    val pic: LiveData<PictureOfDay> = db.asteroidDao.getPic()

    suspend fun refreshAsteroids() {
        val endDate = getNextSevenDaysFormattedDates().last()
        withContext(Dispatchers.IO) {
            val asteroidsJSON = AsteroidApi.retrofitService.getNeoWs(
                getTodaysDate(),
                endDate, Constants.API_KEY
            )
            val asteroids = parseAsteroidsJsonResult(JSONObject(asteroidsJSON))
            db.asteroidDao.insertAllAsteroids(asteroids)

            val picJson = AsteroidApi.retrofitService.getApod(getTodaysDate(), Constants.API_KEY)
            val pic = parsePic(JSONObject(picJson))
            db.asteroidDao.insertPic(pic)
        }
    }
}
