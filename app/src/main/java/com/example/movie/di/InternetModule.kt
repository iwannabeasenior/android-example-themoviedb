package com.example.movie.di

import com.example.movie.data.api.MovieApi
import com.example.movie.data.api.UserApi
import com.example.movie.data.response.SearchResult
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
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
    @NormalRetrofit
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @SearchRetrofit
    fun provideSearchRetrofit(@CustomSearchGson gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(ValueExtractorConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @NormalAPI
    fun provideNormalMovieApi(@NormalRetrofit retrofit: Retrofit) : MovieApi {
        return retrofit
            .create(MovieApi::class.java)
    }

    @Singleton
    @SearchAPI
    @Provides
    fun provideSearchMovieApi(@SearchRetrofit retrofit: Retrofit) : MovieApi {
        return retrofit
            .create(MovieApi::class.java)
    }


    @Named("GetOnlyResultInterceptor")
    @Singleton
    @Provides
    fun provideGetOnlyResultInterceptor() : Interceptor {
        return object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${Constant.API_READ_ACCESS_TOKEN}")
                    .addHeader("Accept", "application/json")
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

    @Singleton
    @Provides
    fun provideOkHttp(@Named("GetOnlyResultInterceptor") interceptor: Interceptor, @Named("LoggingInterceptor") loggingInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserApi(@NormalRetrofit retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}