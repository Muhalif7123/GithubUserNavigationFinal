package com.belajar.githubusernavigationfinal.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel(
    var name: String? = "",
    var githubLink: String? = ""
) : Parcelable
