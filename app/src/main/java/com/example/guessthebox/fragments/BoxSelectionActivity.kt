package com.example.guessthebox.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.guessthebox.R
import com.example.guessthebox.databinding.ActivityBoxSelectionBinding
import com.example.guessthebox.databinding.ItemBoxBinding
import com.example.guessthebox.model.Options
import kotlin.random.Random

class BoxSelectionActivity : BaseActivity() {
    private lateinit var binding: ActivityBoxSelectionBinding
    private lateinit var options: Options

    private var boxIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoxSelectionBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        options = intent.getParcelableExtra(EXTRA_OPTIONS)
            ?: throw IllegalArgumentException("Can't launch BoxSelectionActivity without options")

        boxIndex = savedInstanceState?.getInt(KEY_INDEX) ?: Random.nextInt(options.boxCount)

        createBoxes()
    }

    private fun createBoxes() {
        val boxes = (0 until options.boxCount).map { index ->
            val box = ItemBoxBinding.inflate(layoutInflater)
            box.root.id = View.generateViewId()
            box.boxTitleTextView.text = getString(R.string.box_title, index + 1)
            box.root.setOnClickListener { view -> onBoxSelected(view) }
            box.root.tag = index
            binding.root.addView(box.root)
            box
        }

        binding.flow.referencedIds = boxes.map { it.root.id }.toIntArray()
    }

    private fun onBoxSelected(view: View) {
        if (view.tag as Int == boxIndex) {
            val intent = Intent(this, BoxActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(
                this,
                "This box is empty. Try another one",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val EXTRA_OPTIONS = "EXTRA_OPTIONS"
        private const val KEY_INDEX = "KEY_INDEX"
    }

}