package com.burlakov.petogram.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.burlakov.petogram.R
import com.burlakov.petogram.utils.EmailValidator
import com.burlakov.petogram.view.SettingsView
import com.rengwuxian.materialedittext.MaterialEditText

class EmailDialog
    : DialogFragment() {

    lateinit var email: MaterialEditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val nullParent: ViewGroup? = null
        builder.setView(inflater.inflate(R.layout.email_dialog, nullParent))
            .setPositiveButton(
                R.string.ok
            ) { dialog: DialogInterface, _: Int ->
                (parentFragment as SettingsView).changeEmail(email.text.toString())
                dialog.cancel()
            }.setNegativeButton(R.string.cancel) { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
        return builder.create()
    }

    override fun onStart() {
        super.onStart()

        email = dialog!!.findViewById(R.id.editTextEmailAddress)

        val ok: Button = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
        EmailValidator.setValidateMaterialEditViewAndButton(email, ok, requireContext(),true)
    }
}