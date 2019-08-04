package com.assignment.gorgeouslyfab.core.di

import com.assignment.gorgeouslyfab.GorgeouslyFabApp
import com.assignment.gorgeouslyfab.core.di.viewmodel.ViewModelModule
import com.assignment.gorgeouslyfab.features.presentation.MainActivity
import com.assignment.gorgeouslyfab.features.presentation.reviews.ReviewsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by danieh on 04/08/2019.
 */
@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(application: GorgeouslyFabApp)

    fun inject(mainActivity: MainActivity)

    fun inject(reviewsFragment: ReviewsFragment)
}
