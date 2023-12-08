package test.teamfresh.dawnmarket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.teamfresh.dawnmarket.data.remote.RetrofitNetworkApi
import test.teamfresh.dawnmarket.data.repo.DefaultProductRepository
import test.teamfresh.dawnmarket.data.repo.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideBaseUrl() = "https://smapistage.teamfresh.co.kr/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitNetworkApi(retrofit: Retrofit): RetrofitNetworkApi {
        return retrofit.create(RetrofitNetworkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepository(
        remoteDataSource: RetrofitNetworkApi,
    ): ProductRepository {
        return DefaultProductRepository(remoteDataSource)
    }
}