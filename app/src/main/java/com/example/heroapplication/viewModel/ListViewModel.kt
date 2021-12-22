package com.example.heroapplication.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.heroapplication.database.HeroDatabase
import com.example.heroapplication.model.HeroModel
import com.example.heroapplication.retrofit.HeroApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    val heroes = MutableLiveData<List<HeroModel>>()
    val loading = MutableLiveData<Boolean>()
    val heroesLoadError = MutableLiveData<Boolean>()

    private val heroesApiService = HeroApiService()
    private val disposable= CompositeDisposable() // avoid memory leaks


    fun refresh() {
        HeroDatabase.getDatabase(getApplication()).heroDao().deleteAll()
        val databaseEmpty = HeroDatabase.getDatabase(getApplication()).heroDao().getAllHeroes().isEmpty()
        if(!databaseEmpty) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    private fun fetchFromDatabase() {
        launch {
            val heroes = HeroDatabase.getDatabase(getApplication()).heroDao().getAllHeroes()
            heroesRetrieved(heroes)
            Log.d("database", "Heroes retrieved from database")
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            heroesApiService.getHeroes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<HeroModel>>() {
                    override fun onSuccess(heroList: List<HeroModel>) {
                        storeHeroesLocally(heroList)
                        Log.d("server", "Heroes retrieved from server")
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error", "error fetching data from server $e")
                        heroesLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun heroesRetrieved(heroList: List<HeroModel>) {
        heroes.value = heroList
        heroesLoadError.value = false
        loading.value = false
    }

    private fun storeHeroesLocally(list: List<HeroModel>) {
        Log.d("database", "Store data in database")
        launch {
            val dao = HeroDatabase.getDatabase(getApplication()).heroDao()
            dao.deleteAll()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while(i < list.size) {
                list[i].id = result[i].toInt()
                ++i
            }
            heroesRetrieved(list)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

