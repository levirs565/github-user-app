package com.levirs.githubuser.feature.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import coil.api.load
import com.levirs.githubuser.R
import com.levirs.githubuser.core.model.User
import com.levirs.githubuser.core.model.UserDetails
import com.levirs.githubuser.core.extension.setVisible
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.context_error.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private val mViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        val user: User? = intent.getParcelableExtra(EXTRA_USER)
        // cek apakah pertama kali dijalankan
        if (user != null && savedInstanceState == null) {
            mViewModel.setUser(user)
        }

        mViewModel.userDetails.observe(this, Observer {
            val showError = it.error != null
            layout_err.setVisible(showError)
            if (!showError) {
                val dataLoaded = it.data != null
                layout_detail.setVisible(dataLoaded)
                progressBar.setVisible(!dataLoaded)

                if (dataLoaded) bindDetails(it.data!!)
            } else {
                layout_detail.setVisible(false)
                progressBar.setVisible(false)
                tv_err_message.text = getString(
                    R.string.message_cannot_load,
                    getString(R.string.user_details),
                    it.error
                )
            }
        })

        btn_reload.setOnClickListener {
            mViewModel.load()
        }

        view_pager.adapter = DetailPagerAdapter(supportFragmentManager, this)
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun bindDetails(user: UserDetails) {
        with(user) {
            collapsing_toolbar.title = name
            img_avatar.load(avatar)
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
}
