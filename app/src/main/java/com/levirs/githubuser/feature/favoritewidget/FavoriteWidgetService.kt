package com.levirs.githubuser.feature.favoritewidget

import android.content.Intent
import android.widget.RemoteViewsService

class FavoriteWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        FavoriteWidgetFactory(applicationContext, intent!!)
}