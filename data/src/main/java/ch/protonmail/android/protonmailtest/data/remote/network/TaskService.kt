package ch.protonmail.android.protonmailtest.data.remote.network

import ch.protonmail.android.protonmailtest.data.remote.model.Task
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface TaskService {

    @GET("/tasks.json")
    suspend fun getTasks(): List<Task>

    companion object {

        private const val TIMEOUT = 60L
        private const val API_BASE_URL = "https://proton-android-testcloud.europe-west1.firebasedatabase.app"

        fun create(): TaskService {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build()
                .create(TaskService::class.java)
        }
    }
}
