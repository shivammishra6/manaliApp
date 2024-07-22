package com.example.manali.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ManaliItem(
    @DrawableRes val image:Int,
    @StringRes val title:Int,
    @StringRes val detail:Int
)
