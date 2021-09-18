package com.burlakov.petogram.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.R
import com.burlakov.petogram.view.SettingsView
import com.rengwuxian.materialedittext.MaterialEditText

class UsernameDialog : DialogFragment() {

    lateinit var username: MaterialEditText
    lateinit var curName: String
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val nullParent: ViewGroup? = null
        builder.setView(inflater.inflate(R.layout.username_dialog, nullParent))
            .setPositiveButton(
                R.string.ok
            ) { dialog: DialogInterface, _: Int ->
                (parentFragment as SettingsView).changeUsername(username.text.toString())
                dialog.cancel()
            }.setNegativeButton(R.string.cancel) { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        curName = PetogramApplication.user?.username!!
        username = dialog!!.findViewById(R.id.editTextUsername)
        username.setText(curName)


        val ok: Button = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
        ok.isEnabled = username.text.toString().length in 2..24 && username.text.toString() != curName
        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                ok.isEnabled = username.text.toString().length in 2..24 && username.text.toString() != curName
            }
        })
    }
}