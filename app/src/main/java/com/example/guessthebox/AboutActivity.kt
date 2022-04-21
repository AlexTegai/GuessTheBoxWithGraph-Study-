package com.example.guessthebox


import android.os.Build
import android.os.Bundle
import com.example.guessthebox.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            versionNameTextView.text = getString(R.string.version_name_result)
            versionCodeTextView.text = getString(R.string.version_code_result)
            okButton.setOnClickListener { onOkPressed() }
        }
    }

    private fun onOkPressed() {
        finish()
    }
}