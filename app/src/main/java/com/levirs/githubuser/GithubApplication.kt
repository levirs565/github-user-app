package com.levirs.githubuser

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import com.levirs.githubuser.feature.favoritewidget.FavoriteWidget
import com.levirs.githubuser.feature.favoritewidget.FavoriteWidgetUpdateService

class GithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Cek apakah ada widget yang aktif
        val ids = AppWidgetManager.getInstance(this).getAppWidgetIds(
            ComponentName(this, FavoriteWidget::class.java)
        )
        if (ids.isNotEmpty()) {
            val intent = Intent(this, FavoriteWidgetUpdateService::class.java)
            intent.action = FavoriteWidgetUpdateService.ACTION_START
            startService(intent)
        }
    }

}