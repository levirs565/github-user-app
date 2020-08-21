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
        val intent = Intent(context, FavoriteWidgetUpdateService::class.java)
        intent.action = FavoriteWidgetUpdateService.ACTION_START
        context.startService(intent)
    }

    override fun onDisabled(context: Context) {
        val intent = Intent(context, FavoriteWidgetUpdateService::class.java)
        intent.action = FavoriteWidgetUpdateService.ACTION_STOP
        context.startService(intent)
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

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}