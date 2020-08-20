package com.levirs.githubuser.feature.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.transaction
import com.levirs.githubuser.R

class FavoriteActivity : AppCompatActivity() {
    private val mViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        if (!mViewModel.isInitialized)
            mViewModel.init()

        supportActionBar?.title = getString(R.string.title_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.frame_layout, FavoriteFragment())
            }
        }
    }
}