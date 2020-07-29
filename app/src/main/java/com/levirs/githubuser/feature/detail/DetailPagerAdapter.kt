package com.levirs.githubuser.feature.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.levirs.githubuser.R
import com.levirs.githubuser.feature.detail.fragment.FollowerFragment
import com.levirs.githubuser.feature.detail.fragment.FollowingFragment

class DetailPagerAdapter(
    fm: FragmentManager,
    private val ctx: Context
): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = when (position) {
        0 -> FollowingFragment()
        1 -> FollowerFragment()
        else -> throw IllegalArgumentException("item $position not found")
    }

    override fun getPageTitle(position: Int): CharSequence? = when(position) {
        0 -> ctx.getString(R.string.following)
        1 -> ctx.getString(R.string.follower)
        else -> throw IllegalArgumentException("item $position not found")
    }

    override fun getCount(): Int = 2

}