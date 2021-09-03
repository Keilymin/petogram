package com.burlakov.petogram.dialogs


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.burlakov.petogram.R


/**
 * Базовый диалог для показа сообщений пользователю
 */
class MessageDialog :
    DialogFragment() {

    lateinit var imageView: ImageView
    lateinit var textView: TextView


    fun newInstance(message: String, isPositive: Boolean): MessageDialog {
            val args = Bundle()
            args.putString("message", message)
            args.putBoolean("isPositive", isPositive)
            val f = MessageDialog()
            f.arguments = args
            return f
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val nullParent: ViewGroup? = null
        builder.setView(inflater.inflate(R.layout.dialog_message, nullParent))
            .setPositiveButton(
                R.string.ok
            ) { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
        return builder.create()
    }

    /**
     * В зависимости от параметра isPositive, показывает положительное или негативное сообщение
     */
    override fun onStart() {
        super.onStart()
        imageView = dialog!!.findViewById(R.id.imageView)
        textView = dialog!!.findViewById(R.id.message)
        if (arguments?.getBoolean("isPositive") == true) {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.success
                )
            )
        }
        textView.text = arguments?.getString("message")
    }
}