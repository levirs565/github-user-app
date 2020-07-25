package com.levirs.githubuser.feature.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import com.levirs.githubuser.R
import com.levirs.githubuser.core.model.User
import com.levirs.githubuser.core.model.UserDetails
import com.levirs.githubuser.core.repository.GithubRepository
import com.levirs.githubuser.core.service.GithubService
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }
//    private lateinit var mDataRepository: DataRepository
    private lateinit var mUserDetails: UserDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

//        mDataRepository = DataRepository(this)
        val user: User? = intent.getParcelableExtra(EXTRA_USER)
        if (user != null) {
//            mUserDetails = mDataRepository.getUserDetails(user.id)
            val service = GithubService.newInstance()
            val repository = GithubRepository(service)
            runBlocking(Dispatchers.IO) {
                mUserDetails = repository.getUserDetails(user.userName)
            }
        }

        bindDetails()
    }

    private fun bindDetails() {
        if (::mUserDetails.isInitialized) {
            with (mUserDetails) {
                title = name
                img_avatar.load(avatar)
                tv_name.text = name
                tv_user_name.text = userName
                tv_company.text = company
                tv_location.text = location
                tv_repository.text = getString(R.string.details_repo_count, repositoryCount)
                tv_follower.text = getString(R.string.details_follower_count, followersCount)
                tv_following.text = getString(R.string.details_following_count, followingCount)
            }
        }
    }
}
