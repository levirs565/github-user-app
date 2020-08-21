package com.levirs.githubuser.feature.favoritewidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.request.ErrorResult
import coil.request.GetRequest
import coil.transform.CircleCropTransformation
import com.levirs.githubuser.R
import com.levirs.githubuser.common.model.User
import com.levirs.githubuser.data.DataProvider
import com.levirs.githubuser.feature.detail.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FavoriteWidgetFactory(val context: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {
    private val mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0)
    private val mRepository = DataProvider.provideFavoriteUserRepository(context)
    private lateinit var mFavoriteList: List<User>

    override fun onCreate() {
        // Do nothing
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() = runBlocking(Dispatchers.IO) {
        mFavoriteList = mRepository.getAllFavorite()
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.item_widget_favorite)

        if (mFavoriteList.isEmpty())
            return remoteViews

        val user = mFavoriteList[position]

        remoteViews.setTextViewText(R.id.tv_user_name, user.userName)
        val requestResult = runBlocking {
            val getRequest = GetRequest.Builder(context)
                .data(user.avatar)
                .transformations(CircleCropTransformation())
                .build()
            val result = Coil.imageLoader(context).execute(getRequest)

            if (result is ErrorResult) {
                result.throwable.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Error load image: ${result.throwable.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            result
        }
        remoteViews.setImageViewBitmap(R.id.img_avatar, requestResult.drawable?.toBitmap())

        val intent = Intent()
        intent.putExtra(DetailActivity.EXTRA_USER, user)
        remoteViews.setOnClickFillInIntent(R.id.img_avatar, intent)

        return remoteViews
    }

    override fun getCount(): Int = mFavoriteList.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

    }
}