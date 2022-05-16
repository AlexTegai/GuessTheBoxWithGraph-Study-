package com.example.guesstheboxwithgraph.fragments.contract

import androidx.annotation.StringRes

interface HasCustomTitle {

    @StringRes
    fun getTitleRes(): Int

}