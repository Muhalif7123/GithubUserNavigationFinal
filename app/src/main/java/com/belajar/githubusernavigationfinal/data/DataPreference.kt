package com.belajar.githubusernavigationfinal.data

import android.content.Context
import com.belajar.githubusernavigationfinal.data.entity.DataModel

class DataPreference(context: Context) {

    private val sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun setData(model: DataModel) {
        val editor = sharedPreference.edit()
        editor.putString(NAME, model.name)
        editor.putString(GITHUB_LINK, model.githubLink)
            .apply()
    }

    fun getData(): DataModel {
        val model = DataModel()
        model.name = sharedPreference.getString(NAME, "")
        model.githubLink = sharedPreference.getString(GITHUB_LINK, "")
        return model
    }

    companion object {
        private const val PREF_NAME = "pref"
        private const val NAME = "name"
        private const val GITHUB_LINK ="github_link"
    }
}