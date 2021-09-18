package com.burlakov.petogram.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.R
import com.burlakov.petogram.dialogs.MessageDialog
import com.burlakov.petogram.model.User
import com.burlakov.petogram.presenter.MainPresenter
import com.burlakov.petogram.utils.LocalizeUtil
import com.burlakov.petogram.utils.UserUtil
import com.burlakov.petogram.view.MainView
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity(), MainView {


    private lateinit var mainPresenter: MainPresenter

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var image: CircleImageView
    private lateinit var email: TextView
    private lateinit var username: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainPresenter = MainPresenter(this)
        if (PetogramApplication.user == null) {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        } else mainPresenter.userDataIsOk()


        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val hView: View = navView.getHeaderView(0)
        image = hView.findViewById(R.id.image)
        email = hView.findViewById(R.id.email)
        username = hView.findViewById(R.id.username)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }

    override fun userDataError(message: String) {
        val intent = Intent(this, LogInActivity::class.java)
        intent.putExtra("message", message)
        UserUtil.clear(this)
        startActivity(intent)
    }

    override fun saveUser(user: User) {
        PetogramApplication.user = user
        UserUtil.saveUser(user, this)
    }

    override fun toSetUsername() {
        val intent = Intent(this, UsernameActivity::class.java)
        startActivity(intent)
    }

    override fun setAvatarAndUserData() {
        val user = PetogramApplication.user

        email.text = user!!.email
        username.text = user.username

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)

        Glide.with(this).load("${PetogramApplication.baseUrl}images/${user.id}/${user.avatar}")
            .apply(requestOptions)
            .into(
                image
            )
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