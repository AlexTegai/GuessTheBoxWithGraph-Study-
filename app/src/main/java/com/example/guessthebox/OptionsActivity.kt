package com.example.guessthebox

import android.os.Bundle
import com.example.guessthebox.databinding.ActivityOptionsBinding
import com.example.guessthebox.model.Options
import kotlin.IllegalArgumentException

class OptionsActivity : BaseActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?:
        intent.getParcelableExtra(EXTRA_OPTIONS) ?:
        throw IllegalArgumentException("You need to specify EXTRA_OPTIONS argument")

        with(binding) {
            cancelButton.setOnClickListener { onCancelButtonPressed() }
            confirmButton.setOnClickListener { onConfirmPressed() }
        }

        updateUI()
        setupCheckBox()
    }

    private fun setupCheckBox() {
        binding.enableTimerCheckBox.setOnClickListener {
            options = options.copy(isTimerEnabled = binding.enableTimerCheckBox.isChecked)
        }
    }

    private fun updateUI() {
        binding.enableTimerCheckBox.isChecked = options.isTimerEnabled

//        val currentIndex =
    }

    private fun onConfirmPressed() {
        intent.putExtra(EXTRA_OPTIONS, options)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onCancelButtonPressed() {
        finish()
    }

    companion object {
        const val EXTRA_OPTIONS = "EXTRA_OPTIONS"
        private const val KEY_OPTIONS = "KEY_OPTIONS"
    }
}