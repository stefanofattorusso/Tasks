package ch.protonmail.android.protonmailtest.data.di

import ch.protonmail.android.protonmailtest.data.repository.TaskRepositoryImpl
import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideTaskRepository(repository: TaskRepositoryImpl): TaskRepository
}
