package com.burlakov.petogram.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.R
import com.burlakov.petogram.view.SettingsView
import de.hdodenhof.circleimageview.CircleImageView

class AvatarDialog : DialogFragment() {

    lateinit var avatar: CircleImageView
    lateinit var curAvatar: String
    lateinit var newAvatar: Uri
    lateinit var load: Button
    lateinit var ok: Button
    lateinit var click: View.OnClickListener

    private lateinit var requestOptions: RequestOptions

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val nullParent: ViewGroup? = null
        builder.setView(inflater.inflate(R.layout.avatar_dialog, nullParent))
            .setPositiveButton(
                R.string.ok
            ) { dialog: DialogInterface, _: Int ->
                (parentFragment as SettingsView).changeAvatar(newAvatar)
                dialog.cancel()
            }.setNegativeButton(R.string.cancel) { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }

        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    newAvatar = uri
                    Glide.with(this).load(uri).into(avatar)
                    ok.isEnabled = true
                } else {
                    Glide.with(this).load(curAvatar).apply(requestOptions).into(avatar)
                    ok.isEnabled = false
                }
            }
        click = View.OnClickListener {
            getContent.launch("image/*")
        }
        return builder.create()
    }

    override fun onStart() {
        super.onStart()

        avatar = dialog!!.findViewById(R.id.image)
        load = dialog!!.findViewById(R.id.buttonLoad)
        requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)

        val user = PetogramApplication.user!!
        curAvatar = "${PetogramApplication.baseUrl}images/${user.id}/${user.avatar}"
        Glide.with(this).load(curAvatar).apply(requestOptions).into(avatar)


        ok = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
        ok.isEnabled = false


        load.setOnClickListener(click)
    }
}