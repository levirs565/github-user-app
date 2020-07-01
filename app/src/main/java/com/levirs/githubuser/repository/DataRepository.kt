package com.levirs.githubuser.repository

import android.content.Context
import com.levirs.githubuser.R
import com.levirs.githubuser.model.User
import com.levirs.githubuser.model.UserDetails

class DataRepository(private val ctx: Context) {

    fun getUserList() = with (ctx.resources)  {
        val userNameList = getStringArray(R.array.data_username)
        val nameList = getStringArray(R.array.data_name)
        val avatarList = obtainTypedArray(R.array.data_avatar)
        val companyList = getStringArray(R.array.data_company)
        val locationList = getStringArray(R.array.data_location)
        val result = ArrayList<User>()

        for (index in userNameList.indices) {
            result.add(User(
                userName = userNameList[index],
                name = nameList[index],
                avatar = avatarList.getResourceId(index, 0),
                company = companyList[index],
                location = locationList[index]
            ))
        }

        avatarList.recycle()
        result.toTypedArray()
    }

    fun getUserDetails(id: Int) = with (ctx.resources) {
        val userNameList = getStringArray(R.array.data_username)
        val nameList = getStringArray(R.array.data_name)
        val avatarList = obtainTypedArray(R.array.data_avatar)
        val companyList = getStringArray(R.array.data_company)
        val locationList = getStringArray(R.array.data_location)
        val repositoryList = getIntArray(R.array.data_repository)
        val followingList = getIntArray(R.array.data_following)
        val followerList = getIntArray(R.array.data_follower)

        val result = UserDetails(
            userName = userNameList[id],
            name = nameList[id],
            avatar = avatarList.getResourceId(id, 0),
            company = companyList[id],
            location = locationList[id],
            repository = repositoryList[id],
            following = followingList[id],
            follower = followerList[id]
        )

        avatarList.recycle()
        result
    }
}