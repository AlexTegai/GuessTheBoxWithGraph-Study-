package com.example.guesstheboxwithgraph.fragments.contract

interface HasCustomAction {
    fun getCustomAction(): CustomAction
}

class CustomAction(
    val iconRes: Int,
    val textRes: String,
    val onCustomAction: Runnable
)