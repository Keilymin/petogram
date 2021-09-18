package com.burlakov.petogram.activities.ui.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import androidx.fragment.app.Fragment
import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.R
import com.burlakov.petogram.activities.LogInActivity
import com.burlakov.petogram.dialogs.*
import com.burlakov.petogram.model.User
import com.burlakov.petogram.presenter.SettingsPresenter
import com.burlakov.petogram.presenter.UsernamePresenter
import com.burlakov.petogram.utils.LocalizeUtil
import com.burlakov.petogram.utils.UserUtil
import com.burlakov.petogram.view.MainView
import com.burlakov.petogram.view.SettingsView
import org.apache.commons.io.FileUtils
import java.io.File

class SettingsFragment : Fragment(), SettingsView {

    lateinit var changeUsername: Button
    lateinit var changeEmail: Button
    lateinit var changePassword: Button
    lateinit var changeAvatar: Button
    lateinit var logOut: Button

    lateinit var settingsPresenter: SettingsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        settingsPresenter = SettingsPresenter(this, requireContext())

        changeUsername = root.findViewById(R.id.changeUsernameButton)
        changeUsername.setOnClickListener {
            val dialog = UsernameDialog()
            dialog.show(childFragmentManager, "username")
        }
        changeEmail = root.findViewById(R.id.changeEmailButton)
        changeEmail.setOnClickListener {
            val dialog = EmailDialog()
            dialog.show(childFragmentManager, "email")
        }
        changePassword = root.findViewById(R.id.changePasswordButton)
        changePassword.setOnClickListener {
            val dialog = PasswordDialog()
            dialog.show(childFragmentManager, "password")
        }
        changeAvatar = root.findViewById(R.id.changeAvatarButton)
        changeAvatar.setOnClickListener {
            val dialog = AvatarDialog()
            dialog.show(childFragmentManager, "avatar")
        }

        logOut = root.findViewById(R.id.logOut)
        logOut.setOnClickListener {
            LogOut(false)
        }
        return root
    }

    override fun changeUsername(username: String) {
        settingsPresenter.changeUsername(username)
    }

    override fun changeEmail(email: String) {
        settingsPresenter.changeEmail(email)
    }

    override fun changePassword(oldPassword: String, newPassword: String) {
        settingsPresenter.changePassword(oldPassword, newPassword)
    }

    override fun changeAvatar(uri: Uri) {
        val mime = MimeTypeMap.getSingleton()
        val type = mime.getExtensionFromMimeType(activity?.contentResolver?.getType(uri))
        val image = activity?.contentResolver?.openInputStream(uri)
        val file = File(activity?.filesDir, "avatar.$type")
        FileUtils.copyInputStreamToFile(image, file)
        settingsPresenter.changeAvatar(file)
    }

    override fun showMessage(message: String, isPositive: Boolean) {
        val dialog = MessageDialog().newInstance(
            LocalizeUtil.localize(message, requireContext()),
            isPositive
        )
        dialog.show(childFragmentManager, "message")
    }

    override fun saveUser(user: User) {
        PetogramApplication.user = user
        UserUtil.saveUser(user, requireContext())
        (activity as MainView).setAvatarAndUserData()
    }

    override fun LogOut(email: Boolean) {
        UserUtil.clear(requireContext())
        val intent = Intent(requireContext(), LogInActivity::class.java)
        if (email) {
            intent.putExtra("message", getString(R.string.email_sended_for_change))
        }
        startActivity(intent)
    }
}
