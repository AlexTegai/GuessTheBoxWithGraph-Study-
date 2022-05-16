package com.example.guesstheboxwithgraph.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guesstheboxwithgraph.databinding.FragmentMenuBinding
import com.example.guesstheboxwithgraph.fragments.contract.navigator
import com.example.guesstheboxwithgraph.model.Options

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        navigator().listenResult(options::class.java, viewLifecycleOwner) {
            this.options = it
        }

        with(binding) {
            openButton.setOnClickListener { onOpenButtonPressed() }
            optionsButton.setOnClickListener { onOptionsButtonPressed() }
            aboutButton.setOnClickListener { onAboutButtonPressed() }
            exitButton.setOnClickListener { onExitButtonPressed() }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun onOpenButtonPressed() {
        navigator().showBoxSelectionScreen(options)
    }

    private fun onOptionsButtonPressed() {
        navigator().showOptionsScreen(options)
    }

    private fun onAboutButtonPressed() {
        navigator().showAboutScreen()
    }

    private fun onExitButtonPressed() {
        navigator().goBack()
    }

    companion object {
        private const val KEY_OPTIONS = "OPTIONS"
    }
}

