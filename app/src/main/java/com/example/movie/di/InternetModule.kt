package com.example.movie.di

import com.example.data.api.MovieApi
import com.example.data.api.UserApi
import com.example.model.response.MovieAccountStateResponse
import com.example.model.response.SearchResult
import com.example.movie.datastore.UserPreferences
import com.example.movie.utils.AccountStateDeserializer
import com.example.movie.utils.Constant
import com.example.movie.utils.SearchResultDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormalGson

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CustomSearchGson

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccountStateGson

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormalRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SearchRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormalAPI

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SearchAPI

@Module
@InstallIn(SingletonComponent::class)
object InternetModule {

    @Provides
    @NormalGson
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @CustomSearchGson
    fun provideSearchCustomGson(): Gson = GsonBuilder()
        .registerTypeAdapter(object : TypeToken<List<SearchResult>>() {}.type, SearchResultDeserializer())
        .create()

    @Provides
    @AccountStateGson
    fun provideAccountStateGson(): Gson = GsonBuilder()
        .registerTypeAdapter(MovieAccountStateResponse::class.java, AccountStateDeserializer())
        .create()

    @Provides
    @NormalRetrofit
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @AccountStateGson gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

//
//    @Provides
//    @SearchRetrofit
//    fun provideSearchRetrofit(@CustomSearchGson gson: Gson, okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(Constant.BASE_URL)
//            .addConverterFactory(ValueExtractorConverterFactory.create(gson))
//            .client(okHttpClient)
//            .build()
//    }




    @Singleton
    @Provides
    fun provideNormalMovieApi(@NormalRetrofit retrofit: Retrofit) : MovieApi {
        return retrofit
            .create(MovieApi::class.java)
    }

//    @Singleton
//    @SearchAPI
//    @Provides
//    fun provideSearchMovieApi(@SearchRetrofit retrofit: Retrofit) : MovieApi {
//        return retrofit
//            .create(MovieApi::class.java)
//    }


    @Named("MainInterceptor")
    @Singleton
    @Provides
    fun provideMainInterceptor(
        userPreferences: UserPreferences,
    ) : Interceptor {

        fun Request.createBuilder(accessToken: String?): Request.Builder{
            val passAccessToken = accessToken ?: Constant.API_READ_ACCESS_TOKEN
            return this.newBuilder()
                .addHeader("Authorization", "Bearer $passAccessToken")
                .addHeader("Accept", "application/json")
        }

        return object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var accessToken: String? = null
                accessToken = userPreferences.accessToken
                val request = chain
                    .request()
                    .createBuilder(accessToken)
                    .build()
                return chain.proceed(request)
            }
        }
    }

    @Named("LoggingInterceptor")
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Named("HandleExceptionInterceptor")
    @Singleton
    @Provides
    fun provideExceptionHandlingInterceptor() : Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                return try {
                    val response = chain.proceed(chain.request())
                    response
                } catch (e: Exception) {
                    println("Exception_is ${e.message}")
                    throw e
                }
            }
        }
    }

    @Singleton
    @Provides
    fun provideOkHttp(@Named("MainInterceptor") interceptor: Interceptor, @Named("LoggingInterceptor") loggingInterceptor: Interceptor, @Named("HandleExceptionInterceptor") handleExeptionInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(handleExeptionInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserApi(@NormalRetrofit retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}