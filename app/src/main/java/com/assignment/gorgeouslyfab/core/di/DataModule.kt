package com.assignment.gorgeouslyfab.core.di

import com.assignment.gorgeouslyfab.features.data.repository.ReviewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by danieh on 04/08/2019.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideReviewsRepository(dataSource: ReviewsRepository.MemoryDisk): ReviewsRepository = dataSource
}
