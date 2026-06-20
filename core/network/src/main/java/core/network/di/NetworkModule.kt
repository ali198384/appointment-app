package core.network.di

import core.network.api.AppointmentApi
import core.network.api.DoctorApi
import core.network.api.ProfileApi
import core.network.api.SpecialtyApi
import core.network.interceptor.provideLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TIMEOUT = 30L

    @Provides
    fun provideBaseUrl(): String = "http://api.hospital.com/"

    @Provides
    fun provideLogging(): HttpLoggingInterceptor = provideLoggingInterceptor()

    @Provides
    fun provideOkHttp(
        logging: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

    @Provides
    fun provideRetrofit(
        okHttp: OkHttpClient,
        baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideProfileApi(
        retrofit: Retrofit
    ): ProfileApi = retrofit.create(ProfileApi::class.java)

    @Provides
    fun provideSpecialtyApi(
        retrofit: Retrofit
    ): SpecialtyApi = retrofit.create(SpecialtyApi::class.java)

    @Provides
    fun provideDoctorApi(
        retrofit: Retrofit
    ): DoctorApi = retrofit.create(DoctorApi::class.java)

    @Provides
    fun provideAppointmentApi(
        retrofit: Retrofit
    ): AppointmentApi = retrofit.create(AppointmentApi::class.java)
}
