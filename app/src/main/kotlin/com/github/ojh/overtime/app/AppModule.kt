package com.github.ojh.overtime.app

import android.app.Application
import com.github.ojh.overtime.BuildConfig
import com.github.ojh.overtime.api.FirebaseAPI
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.DataSource
import com.github.ojh.overtime.data.local.LocalDataSource
import com.github.ojh.overtime.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {
    @Singleton
    @Provides
    fun providesApplication(): Application = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Singleton
    @Provides
    fun provideDataSource(firebaseApi: FirebaseAPI): DataSource =
            if (BuildConfig.IS_LOCAL) {
                LocalDataSource()
            } else {
                RemoteDataSource(firebaseApi)
            }

    @Singleton
    @Provides
    fun provideDataManager(dataSource: DataSource): DataManager = DataManager(dataSource)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://overtime-5a599.firebaseio.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideFirebaseApi(retrofit: Retrofit): FirebaseAPI {
        return retrofit.create(FirebaseAPI::class.java)
    }
}