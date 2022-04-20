package com.example.guessthebox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.guessthebox.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            openButton.setOnClickListener { onOpenButtonPressed() }
            optionsButton.setOnClickListener { onOptionsButtonPressed() }
            aboutButton.setOnClickListener { onAboutButtonPressed() }
            exitButton.setOnClickListener { onExitButtonPressed() }
        }
    }

    private fun onOpenButtonPressed() {
        Toast.makeText(this,"open", Toast.LENGTH_SHORT).show()
    }

    private fun onOptionsButtonPressed() {
        Toast.makeText(this,"options", Toast.LENGTH_SHORT).show()
    }

    private fun onAboutButtonPressed() {
        Toast.makeText(this,"about", Toast.LENGTH_SHORT).show()
    }

    private fun onExitButtonPressed() {
        finish()
    }
}