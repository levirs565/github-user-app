package com.levirs.githubuser.common.util

import android.view.View

fun View.setVisible(isVisible: Boolean, canGone: Boolean = false) {
    visibility = if (isVisible) View.VISIBLE else if (canGone) View.GONE else View.INVISIBLE
}