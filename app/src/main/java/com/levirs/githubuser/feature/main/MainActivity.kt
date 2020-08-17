package com.levirs.githubuser.feature.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.levirs.githubuser.R
import com.levirs.githubuser.feature.favorite.FavoriteActivity
import com.levirs.githubuser.feature.settings.SettingsActivity

class MainActivity : AppCompatActivity(), MainFragment.Listener {
    private val mViewModel: MainViewModel by viewModels()
    private lateinit var mSearchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_layout, MainFragment())
                .commit()
            mViewModel.fetchUserList()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.app_bar_search)
        mSearchView = searchItem.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                load(newText)
                return true
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_favorite -> {
                val favoriteIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(favoriteIntent)
                return true
            }
            R.id.action_settings -> {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun load(newText: String?) {
        if (newText != null && newText.isNotEmpty())
            mViewModel.searchUser(newText)
        else mViewModel.fetchUserList()
    }

    override fun onReloadList() {
        load(mSearchView.query?.toString())
    }
}
