package com.elsonmathew.android.nasaproject.interfaces

import com.elsonmathew.android.nasaproject.models.NasaPhotosJsonModel
import com.elsonmathew.android.nasaproject.models.NasaRoverManifest
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by elson.mathew on 2019-09-04.
 */

interface NasaRoverManifestApiService {




    @GET("{id}")
    fun getManifestForRover(@Path("id") id: String, @Query("api_key") api_key: String = "a8Eux1uQBnXfRb8evH1HZ1UaW9RtbmZe534Z1hji"):
            Observable<NasaRoverManifest>

    companion object {
        private val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/manifests/"

        fun create(): NasaRoverManifestApiService {

            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val client : OkHttpClient = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
            }.build()


            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

            return retrofit.create(NasaRoverManifestApiService::class.java)
        }
    }
}
