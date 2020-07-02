package com.levirs.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import com.levirs.githubuser.model.User
import com.levirs.githubuser.model.UserDetails
import com.levirs.githubuser.repository.DataRepository
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }
    private lateinit var mDataRepository: DataRepository
    private lateinit var mUserDetails: UserDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mDataRepository = DataRepository(this)
        val user: User? = intent.getParcelableExtra(EXTRA_USER)
        if (user != null) {
            mUserDetails = mDataRepository.getUserDetails(user.id)
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
                tv_repository.text = getString(R.string.details_repo_count, repository)
                tv_follower.text = getString(R.string.details_follower_count, follower)
                tv_following.text = getString(R.string.details_following_count, following)
            }
        }
    }
}
