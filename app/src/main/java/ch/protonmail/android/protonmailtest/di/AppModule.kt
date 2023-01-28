package ch.protonmail.android.protonmailtest.di

import ch.protonmail.android.protonmailtest.data.remote.network.TaskService
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideTaskService() = TaskService.create()
}
