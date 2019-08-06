package com.assignment.gorgeouslyfab

import android.app.Application
import com.assignment.gorgeouslyfab.core.di.ApplicationComponent
import com.assignment.gorgeouslyfab.core.di.ApplicationModule
import com.assignment.gorgeouslyfab.core.di.DaggerApplicationComponent
import com.assignment.gorgeouslyfab.core.di.DataModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by danieh on 04/08/2019.
 */
class GorgeouslyFabApp : Application() {

    companion object {
        var appContext: GorgeouslyFabApp? = null
    }

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .dataModule(DataModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        this.injectMembers()
        this.initializeLeakDetection()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
