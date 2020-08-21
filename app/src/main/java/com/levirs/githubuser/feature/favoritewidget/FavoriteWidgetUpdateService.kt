package com.levirs.githubuser.feature.favoritewidget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.levirs.githubuser.R
import com.levirs.githubuser.common.data.favorite.FavoriteUserRepository
import com.levirs.githubuser.data.DataProvider

class FavoriteWidgetUpdateService : Service() {
    companion object {
        private val TAG = FavoriteWidgetUpdateService::class.java.simpleName

        fun start(context: Context) {
            try {
                context.startService(Intent(context, FavoriteWidgetUpdateService::class.java))
            } catch (e: IllegalStateException) {
                Log.d(TAG, "start: failed")
                /**
                 * Terjadi java.lang.IllegalStateException: Not allowed to start service Intent app is in background
                 * Saat menjalankan service saat tidak ada activity aplikasi ini yang tampil
                 * yaitu menambah dan menghapus widget
                 */
                e.printStackTrace()
            }
        }

        fun stop(context: Context) {
            try {
                context.stopService(Intent(context, FavoriteWidgetUpdateService::class.java))
            } catch (e: IllegalStateException) {
                Log.d(TAG, "stop: failed")
                e.printStackTrace()
            }
        }
    }

    private lateinit var mUserRepository: FavoriteUserRepository
    private lateinit var mObserver: FavoriteContentObserver
    private var mIsStarted = false

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        if (!mIsStarted) {
            Log.d(TAG, "starting")
            mUserRepository = DataProvider.provideFavoriteUserRepository(applicationContext)
            mObserver = FavoriteContentObserver(
                AppWidgetManager.getInstance(this),
                ComponentName(this, FavoriteWidget::class.java),
                Handler()
            )
            mUserRepository.registerObserver(mObserver)
            mIsStarted = true
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        if (mIsStarted) {
            mUserRepository.unregisterObserver(mObserver)
            mIsStarted = false
        }
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