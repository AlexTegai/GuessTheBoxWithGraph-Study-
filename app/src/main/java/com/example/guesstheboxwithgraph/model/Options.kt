package com.example.guesstheboxwithgraph.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Options(
    val boxCount: Int,
    val isTimerEnabled: Boolean
) : Parcelable {

    companion object {
         val DEFAULT = Options(3, false)
    }
}
