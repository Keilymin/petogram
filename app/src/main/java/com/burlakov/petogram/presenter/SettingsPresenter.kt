package com.burlakov.petogram.presenter


import android.content.Context
import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.R
import com.burlakov.petogram.utils.LocaleException
import com.burlakov.petogram.view.SettingsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class SettingsPresenter(val viewState: SettingsView, val context: Context) {
    val handler = LocaleException(viewState).handler
    val user = PetogramApplication.user!!

    fun changeUsername(username: String) =
        CoroutineScope(Dispatchers.Main).launch(handler) {
            val u = user
            u.username = username
            val answer = PetogramApplication.userService.changeUsername(u)

            if (answer.isPositive) {
                viewState.saveUser(answer.user!!)
                viewState.showMessage(
                    context.getString(R.string.username_changed),
                    answer.isPositive
                )
            } else viewState.showMessage(answer.message, answer.isPositive)
        }

    fun changeEmail(email: String) =
        CoroutineScope(Dispatchers.Main).launch(handler) {
            val u = user
            u.email = email

            val answer = PetogramApplication.userService.changeEmail(u)

            if (answer.isPositive) {
                viewState.LogOut(true)
            } else viewState.showMessage( context.getString(R.string.mail_exist), answer.isPositive)
        }

    fun changePassword(oldPassword: String, newPassword: String) =
        CoroutineScope(Dispatchers.Main).launch(handler) {
            val u = user
            u.password = oldPassword
            u.verificationCode = newPassword
            val answer = PetogramApplication.userService.changePassword(u)

            if (answer.isPositive) {
                viewState.saveUser(answer.user!!)
                viewState.showMessage(
                    context.getString(R.string.password_changed),
                    answer.isPositive
                )
            } else viewState.showMessage(
                context.getString(R.string.password_change_error),
                answer.isPositive
            )
        }

    fun changeAvatar(image: File) =
        CoroutineScope(Dispatchers.Main).launch(handler) {

            val requestFile = image
                .asRequestBody("multipart/form-data".toMediaTypeOrNull())

            val part = MultipartBody.Part.createFormData(
                "image", image.name, requestFile
            )

            val requestUserId = user.id.toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val answer = PetogramApplication.userService.changeAvatar(
                requestUserId,
                part
            )


            if (answer.isPositive) {
                viewState.saveUser(answer.user!!)
                viewState.showMessage(context.getString(R.string.avatar_changed), answer.isPositive)
            }

        }
}