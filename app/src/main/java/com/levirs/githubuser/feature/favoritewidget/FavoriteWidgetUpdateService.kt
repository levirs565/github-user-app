package com.levirs.githubuser.feature.favoritewidget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.database.ContentObserver
import android.os.Handler
import android.os.IBinder
import com.levirs.githubuser.R
import com.levirs.githubuser.common.data.favorite.FavoriteUserRepository
import com.levirs.githubuser.data.DataProvider

class FavoriteWidgetUpdateService : Service() {
    companion object {
        const val ACTION_START = "action_start"
        const val ACTION_STOP = "action_stop"
    }

    private lateinit var mUserRepository: FavoriteUserRepository
    private lateinit var mObserver: FavoriteContentObserver
    private var mIsStarted = false

    override fun onCreate() {
        super.onCreate()
        mUserRepository = DataProvider.provideFavoriteUserRepository(applicationContext)
        mObserver = FavoriteContentObserver(
            AppWidgetManager.getInstance(this),
            ComponentName(this, FavoriteWidget::class.java),
            Handler()
        )
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                if (!mIsStarted) {
                    mUserRepository.registerObserver(mObserver)
                    mIsStarted = true
                }
            }
            ACTION_STOP -> {
                if (mIsStarted) {
                    mUserRepository.unregisterObserver(mObserver)
                    mIsStarted = false
                    stopSelf(startId)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        if (mIsStarted)
            mUserRepository.unregisterObserver(mObserver)
        super.onDestroy()
    }

    inner class FavoriteContentObserver(
        private val appWidgetManager: AppWidgetManager,
        private val componentName: ComponentName,
        handler: Handler?
    ) : ContentObserver(handler) {

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            appWidgetManager.notifyAppWidgetViewDataChanged(
                appWidgetManager.getAppWidgetIds(componentName),
                R.id.stack_view
            )
        }

    }
}