package com.assignment.gorgeouslyfab.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.gorgeouslyfab.features.presentation.createreview.CreateReviewViewModel
import com.assignment.gorgeouslyfab.features.presentation.reviews.ReviewsViewModel
import com.assignment.gorgeouslyfab.features.presentation.selfie.SelfieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by danieh on 04/08/2019.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReviewsViewModel::class)
    abstract fun bindsReviewsViewModel(reviewsViewModel: ReviewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateReviewViewModel::class)
    abstract fun bindsCreateReviewViewModel(createReviewViewModel: CreateReviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelfieViewModel::class)
    abstract fun bindsSelfieViewModel(selfieViewModel: SelfieViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
