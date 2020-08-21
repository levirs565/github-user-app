package com.levirs.githubuser.feature.favoritewidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.levirs.githubuser.R
import com.levirs.githubuser.feature.detail.DetailActivity

/**
 * Implementation of App Widget functionality.
 */
class FavoriteWidget : AppWidgetProvider() {
    companion object {
        const val ACTION_RELOAD = "action_reload"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        FavoriteWidgetUpdateService.start(context)
    }

    override fun onDisabled(context: Context) {
        FavoriteWidgetUpdateService.stop(context)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == ACTION_RELOAD) {
            val manager = AppWidgetManager.getInstance(context)
            val widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0)
            manager.notifyAppWidgetViewDataChanged(widgetId, R.id.stack_view)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.widget_favorite)

    val adapterIntent = Intent(context, FavoriteWidgetService::class.java)
    adapterIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    adapterIntent.data = adapterIntent.toUri(Intent.URI_INTENT_SCHEME).toUri()

    views.setRemoteAdapter(R.id.stack_view, adapterIntent)
    views.setEmptyView(R.id.stack_view, R.id.tv_empty)

    val clickIntent = Intent(context, DetailActivity::class.java)
    val pendingIntent =
        PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    views.setPendingIntentTemplate(R.id.stack_view, pendingIntent)

    val refreshIntent = Intent(context, FavoriteWidget::class.java)
    refreshIntent.action = FavoriteWidget.ACTION_RELOAD
    refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    val refreshPendingIntent =
        PendingIntent.getBroadcast(context, appWidgetId, refreshIntent, 0)
    views.setOnClickPendingIntent(R.id.btn_reload, refreshPendingIntent)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}