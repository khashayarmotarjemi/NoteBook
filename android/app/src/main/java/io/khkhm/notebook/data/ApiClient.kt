package io.khkhm.notebook.data

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import com.google.gson.Gson




/**
 * Created by khashayar on 7/28/18.
 */
class ApiClient {
    companion object {
        private var retrofit: Retrofit? = null
        private val REQUEST_TIMEOUT = 60
        private var okHttpClient: OkHttpClient? = null

        fun getClient(context: Context): Retrofit {
            if (okHttpClient == null) {
                initOkHttp(context)
            }

            val gson = GsonBuilder().
                    setLenient()
                    .create()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(Const.BASE_URL)
                        .client(okHttpClient)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit!!
        }

        private fun initOkHttp(context: Context) {
            val httpClient = OkHttpClient().newBuilder()
                    .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(interceptor)

            okHttpClient = httpClient.build()
        }


    }

}