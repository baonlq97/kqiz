package com.brandon.kqiz.di

import android.content.Context
import androidx.room.Room
import com.brandon.data.local.QuestionDatabase
import com.brandon.data.local.dao.QuestionDao
import com.brandon.data.helper.JsonLoader
import com.brandon.data.local.datasource.LocalDataSource
import com.brandon.data.mapper.Mapper
import com.brandon.data.repositories.QuestionRepositoryImpl
import com.brandon.domain.repositories.QuestionRepository
import com.brandon.domain.usecases.GetQuestionTypesAndCountUseCase
import com.brandon.domain.usecases.GetQuestionsUseCase
import com.brandon.domain.usecases.InsertQuestionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuestionDatabase {
        return Room.databaseBuilder(
            context,
            QuestionDatabase::class.java,
            "app_database.db"
        ).build()
    }

    @Provides
    fun provideQuestionDao(database: QuestionDatabase): QuestionDao {
        return database.questionDao()
    }

    @Provides
    @Singleton
    fun provideJsonUtil(@ApplicationContext context: Context): JsonLoader {
        return JsonLoader(context)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(questionDao: QuestionDao): LocalDataSource {
        return LocalDataSource(questionDao)
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(
        localDataSource: LocalDataSource,
        jsonLoader: JsonLoader
    ): QuestionRepository {
        return QuestionRepositoryImpl(localDataSource, jsonLoader)
    }
}