package com.example.guessthebox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BoxSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_box_selection)
    }

    companion object {
        const val EXTRA_OPTIONS = "EXTRA_OPTIONS"
        private const val KEY_OPTIONS = "KEY_OPTIONS"
    }
}