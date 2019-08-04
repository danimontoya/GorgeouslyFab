package com.assignment.gorgeouslyfab.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by danieh on 04/08/2019.
 */
@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun context() = context
}
