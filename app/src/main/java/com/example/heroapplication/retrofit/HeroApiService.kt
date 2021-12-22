package com.example.heroapplication.retrofit

import com.example.heroapplication.model.HeroModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HeroApiService {

    private val api = Retrofit.Builder()
        .baseUrl(RestAPI.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RestAPI::class.java)

    fun getHeroes(): Single<List<HeroModel>> {
        return api.getAllHeroes()
    }
}