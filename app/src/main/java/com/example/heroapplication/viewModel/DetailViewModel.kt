package com.example.heroapplication.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.heroapplication.database.HeroDatabase
import com.example.heroapplication.model.HeroModel
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val heroLiveData = MutableLiveData<HeroModel>()

    fun fetch(id: Int) {
        launch {
            val hero = HeroDatabase.getDatabase(getApplication()).heroDao().getHero(id)
            heroLiveData.value = hero
        }
    }
}