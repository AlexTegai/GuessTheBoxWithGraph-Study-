package com.example.guesstheboxwithgraph.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guesstheboxwithgraph.R
import com.example.guesstheboxwithgraph.databinding.FragmentBoxBinding
import com.example.guesstheboxwithgraph.fragments.contract.HasCustomTitle
import com.example.guesstheboxwithgraph.fragments.contract.navigator

class BoxFragment : Fragment(), HasCustomTitle {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBoxBinding.inflate(inflater, container, false).apply {
        toMainMenuButton.setOnClickListener { onToMainMenuPressed() }
    }.root

    override fun getTitleRes(): Int = R.string.box

    private fun onToMainMenuPressed() {
        navigator().goToMenu()
    }
}