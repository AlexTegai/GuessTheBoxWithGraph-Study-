package com.example.guessthebox.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.guessthebox.R
import com.example.guessthebox.databinding.FragmentOptionsBinding
import com.example.guessthebox.fragments.contract.CustomAction
import com.example.guessthebox.fragments.contract.HasCustomAction
import com.example.guessthebox.fragments.contract.HasCustomTitle
import com.example.guessthebox.fragments.contract.navigator
import com.example.guessthebox.model.Options
import kotlin.IllegalArgumentException

class OptionsFragment : Fragment(), HasCustomAction, HasCustomTitle {

    private lateinit var binding: FragmentOptionsBinding
    private lateinit var options: Options

    private lateinit var boxCountItems: List<BoxCountItem>
    private lateinit var adapter: ArrayAdapter<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_OPTIONS)
            ?: arguments?.getParcelable(ARGS_OPTIONS)
                    ?: throw IllegalArgumentException("You need to specify EXTRA_OPTIONS argument")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOptionsBinding.inflate(inflater, container, false)

        setupSpinner()
        updateUI()

        with(binding) {
            cancelButton.setOnClickListener { onCancelButtonPressed() }
            confirmButton.setOnClickListener { onConfirmPressed() }
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    override fun getTitleRes(): Int = R.string.options

    override fun getCustomAction(): CustomAction {
        return CustomAction(
            iconRes = R.drawable.ic_done,
            textRes = R.string.done.toString(),
            onCustomAction = { onConfirmPressed() }
        )
    }

    private fun updateUI() {
        val currentBox = boxCountItems.indexOfFirst { it.count == options.boxCount }
        binding.boxCountSpinner.setSelection(currentBox)
    }

    private fun setupSpinner() {
        boxCountItems = (1..6).map { BoxCountItem(it, resources.getQuantityString(R.plurals.boxes, it, it)) }

        adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, boxCountItems)
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
        navigator().publishResult(options)
        navigator().goBack()
    }

    private fun onCancelButtonPressed() {
        navigator().goBack()
    }

    companion object {
        private const val ARGS_OPTIONS = "ARGS_OPTIONS"
        private const val KEY_OPTIONS = "KEY_OPTIONS"

        fun newInstance(options: Options): OptionsFragment {
            val args = Bundle()
            args.putParcelable(ARGS_OPTIONS, options)
            val fragment = OptionsFragment()
            fragment.arguments = args
            return fragment
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
}

