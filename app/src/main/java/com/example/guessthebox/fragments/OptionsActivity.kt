package com.example.guessthebox.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.guessthebox.R
import com.example.guessthebox.databinding.ActivityOptionsBinding
import com.example.guessthebox.model.Options
import kotlin.IllegalArgumentException

class OptionsActivity : BaseActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var options: Options

    private lateinit var boxCountItems: List<BoxCountItem>
    private lateinit var adapter: ArrayAdapter<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: intent.getParcelableExtra(
            EXTRA_OPTIONS
        ) ?: throw IllegalArgumentException("You need to specify EXTRA_OPTIONS argument")

        setupSpinner()
        updateUI()

        with(binding) {
            cancelButton.setOnClickListener { onCancelButtonPressed() }
            confirmButton.setOnClickListener { onConfirmPressed() }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun updateUI() {
        val currentIndex = boxCountItems.indexOfFirst { it.count == options.boxCount }
        binding.boxCountSpinner.setSelection(currentIndex)
    }

    private fun setupSpinner() {
        boxCountItems = (1..6).map {
            BoxCountItem(it, resources.getQuantityString(R.plurals.boxes, it, it))
        }

        adapter = ArrayAdapter(this, R.layout.item_spinner, boxCountItems)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        binding.boxCountSpinner.adapter = adapter
        binding.boxCountSpinner.onItemSelectedListener = object : AdapterView
        .OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val count = boxCountItems[position].count
                options = options.copy(boxCount = count)
            }
        }
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

class BoxCountItem(
    val count: Int,
    private val optionTitle: String
) {
    override fun toString(): String {
        return optionTitle
    }
}