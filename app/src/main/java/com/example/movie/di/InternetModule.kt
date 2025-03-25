package com.example.movie.di

import androidx.compose.ui.unit.Constraints
import com.example.movie.data.api.MovieApi
import com.example.movie.data.repo.MovieRepository
import com.example.movie.domain.repo.MovieRepo
import com.example.movie.utils.ConnectivityManagerNetWorkMonitor
import com.example.movie.utils.Constant
import com.example.movie.utils.NetWorkMonitor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InternetModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
//            .addConverterFactory(ValueExtractorConverterFactory(gson))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit) : MovieApi {
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

    @Singleton
    @Provides
    fun provideOkHttp(@Named("GetOnlyResultInterceptor") interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
//    @Singleton
//    @Provides
//    fun provideMovieRepo(repo: MovieRepository): MovieRepo = repo
//    @Singleton
//    @Provides
//    fun bindsNetworkMonitor(netWorkMonitor: ConnectivityManagerNetWorkMonitor): NetWorkMonitor = netWorkMonitor

}