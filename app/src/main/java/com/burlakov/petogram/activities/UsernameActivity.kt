package com.burlakov.petogram.activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.webkit.MimeTypeMap
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.burlakov.petogram.R
import com.burlakov.petogram.dialogs.MessageDialog
import com.burlakov.petogram.presenter.UsernamePresenter
import com.burlakov.petogram.utils.LocalizeUtil
import com.burlakov.petogram.view.UsernameView
import com.rengwuxian.materialedittext.MaterialEditText
import de.hdodenhof.circleimageview.CircleImageView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileOutputStream


class UsernameActivity : MvpAppCompatActivity(), UsernameView {

    lateinit var save: Button
    lateinit var load: Button
    lateinit var username: MaterialEditText
    lateinit var image: CircleImageView
    private var imageUri: Uri? = null

    private val usernamePresenter by moxyPresenter { UsernamePresenter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username)

        save = findViewById(R.id.buttonSave)
        load = findViewById(R.id.buttonLoad)
        username = findViewById(R.id.editTextUsername)
        image = findViewById(R.id.image)

        Glide.with(this).load(R.mipmap.ic_launcher_round).into(image)

        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                save.isEnabled = username.length() in 2..24
            }
        })

        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    imageUri = uri
                    Glide.with(this).load(uri).into(image)
                } else {
                    imageUri = null
                    Glide.with(this).load(R.mipmap.ic_launcher_round).into(image)
                }
            }

        load.setOnClickListener {
            getContent.launch("image/*")
        }

        save.setOnClickListener {
            lateinit var file: File
            if (imageUri != null) {
                val mime = MimeTypeMap.getSingleton()
                val type = mime.getExtensionFromMimeType(contentResolver.getType(imageUri!!))
                val image = contentResolver.openInputStream(imageUri!!)
                file = File(filesDir, "avatar.$type")
                FileUtils.copyInputStreamToFile(image, file)
            } else {


                val bitmap: Bitmap = ResourcesCompat.getDrawable(
                    resources,
                    R.mipmap.ic_launcher,
                    null
                )!!
                    .toBitmap()
                file = File(filesDir, "avatar.jpg")
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream((file)))
            }
            usernamePresenter.saveImageAndUsername(file, username.text.toString())
        }


    }

    override fun backToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun showMessage(message: String, isPositive: Boolean) {
        val dialog = MessageDialog().newInstance(LocalizeUtil.localize(message, this), isPositive)
        dialog.show(supportFragmentManager, "message")
    }
    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}