package com.nals.demo.application

import android.app.Application
import androidx.room.Room
import com.nals.demo.BuildConfig
import com.nals.demo.util.ApiService
import com.nals.demo.util.Constant
import com.nals.demo.util.ResponseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DemoApplicationModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL + "api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ResponseInterceptor())
                    .build()
            )
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): DemoApplicationDatabase {
        return Room
            .databaseBuilder(application, DemoApplicationDatabase::class.java, Constant.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}