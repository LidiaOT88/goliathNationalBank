package com.example.goliathnationalbank.di

import android.content.Context
import androidx.room.Room
import com.example.goliathnationalbank.data.datasource.LocalDataSource
import com.example.goliathnationalbank.data.datasource.LocalDataSourceImpl
import com.example.goliathnationalbank.data.datasource.RemoteDataSource
import com.example.goliathnationalbank.data.datasource.RemoteDataSourceImpl
import com.example.goliathnationalbank.data.local.AppDatabase
import com.example.goliathnationalbank.data.local.dao.RateDao
import com.example.goliathnationalbank.data.local.dao.TransactionDao
import com.example.goliathnationalbank.data.remote.ApiService
import com.example.goliathnationalbank.data.repository.Repository
import com.example.goliathnationalbank.data.repository.RepositoryImpl
import com.example.goliathnationalbank.data.usecase.*
import com.example.goliathnationalbank.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    lateinit var database: AppDatabase

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        database =  Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
        return database
    }

    @Singleton
    @Provides
    fun provideRateDao(database: AppDatabase): RateDao {
        return database.getRateDao()
    }

    @Singleton
    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.getTransactionDao()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        val builder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        builder.apply {
            addInterceptor(httpLoggingInterceptor)
            connectTimeout(20, TimeUnit.SECONDS)
        }

        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(Constants.BASE_URL)
            .client(builder.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource = remoteDataSource

    @Provides
    @Singleton
    fun provideLocalDataSource(
        rateDao: RateDao,
        transactionDao: TransactionDao
    ): LocalDataSource = LocalDataSourceImpl(rateDao, transactionDao)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSourceImpl,
                          localDataSource: LocalDataSourceImpl) =
        RepositoryImpl(remoteDataSource, localDataSource) as Repository

    @Singleton
    @Provides
    fun provideGetInitialDataUseCase(repository: Repository) = GetInitialDataUseCaseImpl(repository) as GetInitialDataUseCase

    @Singleton
    @Provides
    fun provideGetProductBySkuUseCase(repository: Repository) = GetProductBySkuUseCaseImpl(repository) as GetProductBySkuUseCase

/*    @Singleton
    @Provides
    fun provideGetProducsUseCase(repository: Repository) = GetProductsUseCaseImpl(repository) as GetProductsUseCase*/

}