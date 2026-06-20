package com.example.appointment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import core.sync.manager.SyncManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        /*Handler(Looper.getMainLooper()).post {
            val syncManager: SyncManager by lazy { entryPoint.syncManager() }
            syncManager.startPeriodic()
        }*/

        registerActivityLifecycleCallbacks(
            object : ActivityLifecycleCallbacks {
                @SuppressLint("SourceLockedOrientationActivity")
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }

                override fun onActivityStarted(activity: Activity) {}

                override fun onActivityResumed(activity: Activity) {}

                override fun onActivityPaused(activity: Activity) {}

                override fun onActivityStopped(activity: Activity) {}

                override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {}

                override fun onActivityDestroyed(activity: Activity) {}
            }
        )
    }
}
