package com.burlakov.petogram.presenter

import android.net.Uri
import androidx.core.net.toFile
import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.utils.LocaleException
import com.burlakov.petogram.view.UsernameView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.InputStream


@InjectViewState
class UsernamePresenter : MvpPresenter<UsernameView>() {
    val handler = LocaleException(viewState).handler

    fun saveImageAndUsername(image: File, username: String) =
        CoroutineScope(Dispatchers.Main).launch(handler) {

            val requestFile = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                image
            )

            val part = MultipartBody.Part.createFormData(
                "image", image.name, requestFile
            )
            val curUser = PetogramApplication.user
            curUser?.username = username
            val requestUserId = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                curUser?.id.toString()
            )
            val requestUsername = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                curUser?.username!!
            )
            val answer = PetogramApplication.userService.loadImageAndUsername(
                requestUserId,
                requestUsername,
                part
            )
            if (answer.isPositive) {
                viewState.backToMain()
            } else {
                viewState.showMessage(answer.message, answer.isPositive)
            }

        }
}

