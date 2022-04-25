package com.example.guessthebox.fragments.contract

import android.media.session.PlaybackState

interface HasCustomAction {
    fun getCustomAction(): CustomAction
}

class CustomAction(
    val iconRes: Int,
    val textRes: String,
    val onCustomAction: Runnable
)