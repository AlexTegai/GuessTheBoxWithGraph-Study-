package com.example.guessthebox.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.guessthebox.databinding.ActivityMenuBinding
import com.example.guessthebox.model.Options

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var options: Options

    private var launcher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            openButton.setOnClickListener { onOpenButtonPressed() }
            optionsButton.setOnClickListener { onOptionsButtonPressed() }
            aboutButton.setOnClickListener { onAboutButtonPressed() }
            exitButton.setOnClickListener { onExitButtonPressed() }
        }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT

        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                options = result.data?.getParcelableExtra(OptionsActivity.EXTRA_OPTIONS)
                    ?: throw IllegalArgumentException("No")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun onOpenButtonPressed() {
        val intent = Intent(this, BoxSelectionActivity::class.java)
        intent.putExtra(BoxSelectionActivity.EXTRA_OPTIONS, options)
        startActivity(intent)
    }

    private fun onOptionsButtonPressed() {
        val intent = Intent(this, OptionsActivity::class.java)
        intent.putExtra(OptionsActivity.EXTRA_OPTIONS, options)
        launcher?.launch(intent)
    }

    private fun onAboutButtonPressed() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun onExitButtonPressed() {
        finish()
    }

    companion object {
        private const val KEY_OPTIONS = "OPTIONS"
//        private const val OPTIONS_REQUEST_CODE = 1
    }
}

