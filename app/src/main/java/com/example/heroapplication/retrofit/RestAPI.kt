package com.example.heroapplication.retrofit

import com.example.heroapplication.model.HeroModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RestAPI {

    @GET("/api/heroStats")
    fun getAllHeroes() : Single<List<HeroModel>>

    companion object {
        const val BASE_URL = "https://api.opendota.com"
    }
//    companion object{
//        const val BASE_URL = "https://api.opendota.com"
//
//        fun create(): RestAPI {
//            var rf = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build()
//
//            return rf.create(RestAPI::class.java)
//        }
//    }
}