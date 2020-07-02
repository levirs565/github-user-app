package com.levirs.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.levirs.githubuser.repository.DataRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var dataRepository: DataRepository
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataRepository = DataRepository(this)
        adapter = MainAdapter()
        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.adapter = adapter
        loadData()
    }

    private fun loadData() {
        adapter.list = dataRepository.getUserList()
    }
}
