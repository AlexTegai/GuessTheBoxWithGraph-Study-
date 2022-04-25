package com.example.guessthebox.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guessthebox.R
import com.example.guessthebox.databinding.FragmentBoxBinding
import com.example.guessthebox.fragments.contract.HasCustomTitle
import com.example.guessthebox.fragments.contract.navigator

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