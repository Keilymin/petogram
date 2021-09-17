package com.burlakov.petogram.activities.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.burlakov.petogram.R
import com.burlakov.petogram.dialogs.MessageDialog
import com.burlakov.petogram.utils.LocalizeUtil
import com.burlakov.petogram.view.SettingsView

class SettingsFragment : Fragment(), SettingsView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        showMessage("hello", true)
        return root
    }

    override fun showMessage(message: String, isPositive: Boolean) {
        val dialog = MessageDialog().newInstance(
            LocalizeUtil.localize(message, requireContext()),
            isPositive
        )
        dialog.show(childFragmentManager, "message")
    }
}