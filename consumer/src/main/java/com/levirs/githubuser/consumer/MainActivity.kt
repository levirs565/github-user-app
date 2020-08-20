package com.levirs.githubuser.consumer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    private val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!mViewModel.isInitialized)
            mViewModel.init()

        if (savedInstanceState == null)
            supportFragmentManager.commit {
                add(R.id.main_frame, MainFragment())
            }
    }
}
