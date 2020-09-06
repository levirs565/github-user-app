package com.levirs.githubuser.feature.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import com.levirs.githubuser.R
import com.levirs.githubuser.common.model.User
import com.levirs.githubuser.common.model.UserDetails
import com.levirs.githubuser.common.util.setVisible
import com.levirs.githubuser.feature.favoritewidget.FavoriteWidgetUpdateService
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_AVATAR_TRANSITION_NAME = "extra_avatar_transition_name"
    }

    private val mViewModel: DetailViewModel by viewModels()
    private lateinit var mAvatarPreDrawListener: ViewTreeObserver.OnPreDrawListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        img_avatar.transitionName = intent.getStringExtra(EXTRA_AVATAR_TRANSITION_NAME)
        postponeEnterTransition()

        if (!mViewModel.isInitialized)
            mViewModel.init()

        val user: User? = intent.getParcelableExtra(EXTRA_USER)
        // cek apakah pertama kali dijalankan
        if (user != null && savedInstanceState == null) {
            mViewModel.setUser(user)
        }

        mViewModel.userDetails.observe(this, Observer {
            if (it.data != null)
                bindDetails(it.data!!)
            state_view.updateViewState(it)
        })
        mViewModel.isFavorite.observe(this, Observer {
            fab_favorite.setImageResource(if (it)
                R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_not_favorite_24)
        })
        fab_favorite.setOnClickListener {
            mViewModel.toggleFavorite()
        }

        state_view.setReloadAction {
            mViewModel.load()
        }
        state_view.setContentName(getString(R.string.user_details))

        view_pager.adapter = DetailPagerAdapter(supportFragmentManager, this)
        tab_layout.setupWithViewPager(view_pager)


        FavoriteWidgetUpdateService.start(this)
    }

    private fun schedulePostponedTransition() {
        mAvatarPreDrawListener = ViewTreeObserver.OnPreDrawListener {
            img_avatar.viewTreeObserver.removeOnPreDrawListener(mAvatarPreDrawListener)
            startPostponedEnterTransition()
            true
        }
        img_avatar.viewTreeObserver.addOnPreDrawListener(mAvatarPreDrawListener)
    }

    private fun bindDetails(user: UserDetails) {
        with(user) {
            collapsing_toolbar.title = name
            img_avatar.load(avatar) {
                listener(
                    onSuccess = { _, _ ->
                        schedulePostponedTransition()
                    },
                    onError = { _, _ ->
                        schedulePostponedTransition()
                    }
                )
            }
            tv_name.text = name
            tv_user_name.text = userName

            val showCompany = company != null
            ic_company.setVisible(showCompany, true)
            tv_company.setVisible(showCompany, true)
            tv_company.text = company

            val showLocation = location != null
            ic_location.setVisible(showLocation, true)
            tv_location.setVisible(showLocation, true)
            tv_location.text = location

            tv_repository.text = getString(R.string.details_repo_count, repositoryCount)
            tv_follower.text = getString(R.string.details_follower_count, followersCount)
            tv_following.text = getString(R.string.details_following_count, followingCount)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finishAfterTransition()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
