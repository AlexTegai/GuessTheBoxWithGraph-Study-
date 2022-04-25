package com.example.guessthebox.fragments.contract

import androidx.annotation.StringRes

interface HasCustomTitle {

    @StringRes
    fun getTitleRes() : Int
}