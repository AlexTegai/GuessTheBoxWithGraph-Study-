package com.example.guessthebox.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.guessthebox.R
import com.example.guessthebox.databinding.FragmentBoxSelectionBinding
import com.example.guessthebox.databinding.ItemBoxBinding
import com.example.guessthebox.fragments.contract.HasCustomTitle
import com.example.guessthebox.fragments.contract.navigator
import com.example.guessthebox.model.Options
import kotlin.random.Random

class BoxSelectionFragment : Fragment(), HasCustomTitle {
    private lateinit var binding: FragmentBoxSelectionBinding
    private lateinit var options: Options

    private var boxIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = arguments?.getParcelable(ARGS_OPTIONS)
            ?: throw IllegalArgumentException("Can't launch BoxSelectionActivity without options")

        boxIndex = savedInstanceState?.getInt(KEY_INDEX) ?: Random.nextInt(options.boxCount)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoxSelectionBinding.inflate(inflater, container, false)
        createBoxes()
        return binding.root
    }
    override fun getTitleRes(): Int = R.string.select_box

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
            navigator().showBoxCongratulationsScreen()
        } else {
            Toast.makeText(context, "This box is empty. Try another one", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        private const val ARGS_OPTIONS = "ARGS_OPTIONS"
        private const val KEY_INDEX = "KEY_INDEX"

        fun newInstance(options: Options): BoxSelectionFragment {
            val args = Bundle()
            args.putParcelable(ARGS_OPTIONS, options)
            val fragment = BoxSelectionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}