package com.example.guessthebox.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guessthebox.R
import com.example.guessthebox.databinding.FragmentAboutBinding
import com.example.guessthebox.fragments.contract.HasCustomTitle
import com.example.guessthebox.fragments.contract.navigator

class AboutFragment : Fragment(), HasCustomTitle {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAboutBinding.inflate(inflater, container, false).apply {
        versionNameTextView.text = getString(R.string.version_name_result)
        versionCodeTextView.text = getString(R.string.version_code_result)
        okButton.setOnClickListener { onOkPressed() }
    }.root

    override fun getTitleRes(): Int = R.string.about

    private fun onOkPressed() {
        navigator().goBack()
    }
}