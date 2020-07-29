package com.levirs.githubuser.core.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.view.setMargins
import com.levirs.githubuser.R
import com.levirs.githubuser.core.extension.setVisible
import com.levirs.githubuser.core.model.DataState
import kotlinx.android.synthetic.main.content_error.view.*

/**
 * Kelas view group yang dapat menampilkan progress dan error.
 * View group ini harus boleh berisi satu view untuk menampilkan data
 */
class DataStatefulView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private lateinit var mDataView: View
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mErrorLayout: View
    private var mReloadAction: (() -> Unit)? = null
    private var mContentName = ""

    fun initView() {
        mDataView = getChildAt(0)
        mProgressBar = ProgressBar(context, null, android.R.attr.progressBarStyle)
        mProgressBar.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        ).apply {
            setMargins(resources.getDimensionPixelSize(R.dimen.baseline_spacing))
        }
        addView(mProgressBar)

        mErrorLayout = LayoutInflater.from(context)
            .inflate(R.layout.content_error, this, false)
        mErrorLayout.btn_reload.setOnClickListener {
            mReloadAction?.invoke()
        }
        addView(mErrorLayout)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    fun <T> updateViewState(state: DataState<T>) {
        val isDataLoaded = state.data != null
        val isError = state.error != null

        if (isError)
            mErrorLayout.tv_err_message.text = context.getString(
                R.string.message_cannot_load, mContentName, state.error)

        mDataView.setVisible(isDataLoaded)
        mErrorLayout.setVisible(isError)
        mProgressBar.setVisible(!isError && !isDataLoaded)
    }

    fun setReloadAction(l: (() -> Unit)?) {
        mReloadAction = l
    }

    fun setContentName(name: String) {
        mContentName = name
    }

    fun setGravity(gravity: Int) {
        (mProgressBar.layoutParams as LayoutParams).gravity = gravity
        (mErrorLayout.layoutParams as LayoutParams).gravity = gravity
    }
}