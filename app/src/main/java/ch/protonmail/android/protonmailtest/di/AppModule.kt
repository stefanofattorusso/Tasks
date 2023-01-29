package ch.protonmail.android.protonmailtest.di

import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.android.protonmailtest.data.remote.network.TaskService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideTaskService() = TaskService.create()

    @Provides
    @Singleton
    fun provideCryptoLibrary() = CryptoLib()
}
