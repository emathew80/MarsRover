package com.elsonmathew.android.nasaproject.interfaces

import com.elsonmathew.android.nasaproject.models.NasaPhotosJsonModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Path


/**
 * Created by elson.mathew on 2019-08-29.
 */

interface NasaPhotosApiService {

    @GET("{rover}/photos")
    fun getRoverPhotos(@Path("rover") rover: String, @Query("sol") sol: String,
                             @Query("camera") camera: String,
                             @Query("api_key") api_key: String = "a8Eux1uQBnXfRb8evH1HZ1UaW9RtbmZe534Z1hji"):
            Observable<NasaPhotosJsonModel>

    companion object {
        private val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"

        fun create(): NasaPhotosApiService {

            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val client : OkHttpClient = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
            }.build()


            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).client(client)
                .build()

            return retrofit.create(NasaPhotosApiService::class.java)
        }
    }
}

