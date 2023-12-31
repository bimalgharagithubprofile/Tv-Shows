package com.bimalghara.tv_shows.data.network.retrofit

import com.bimalghara.tv_shows.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


private const val timeoutRead = 30   //In seconds
private const val contentType = "Content-Type"
private const val accept = "accept"
private const val contentTypeValue = "application/json"
private const val authorization = "Authorization"
private const val timeoutConnect = 30   //In seconds


@Singleton
class ApiServiceGenerator @Inject constructor() {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit

    //keys must be stored in safer place and not here (this is just for demo app)
    //safer place like - Android Keychain (TEE) or FCM or AccountManager etc..
    private val bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMzdkZWEzNTc0ZjZhNmI2NGU4OGVjYmMwOTNhODdkNyIsInN1YiI6IjY1Nzk0YmFmZWM4YTQzMDBjMzNhYmI4MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.l3p3LHJVsrwtwoC2j8KycGXHRSeVBWbR-wUz_Emvf9Y"

    private var headerInterceptor = Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
                .header(contentType, contentTypeValue)
                .header(accept, contentTypeValue)
                .addHeader(authorization, "Bearer $bearerToken")
                .method(original.method, original.body)
                .build()

        chain.proceed(request)
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    init {
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun <S> createApiService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}