package com.burlakov.petogram.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.R
import com.burlakov.petogram.dialogs.MessageDialog
import com.burlakov.petogram.model.User
import com.burlakov.petogram.presenter.LogInPresenter
import com.burlakov.petogram.presenter.MainPresenter
import com.burlakov.petogram.utils.LocalizeUtil
import com.burlakov.petogram.utils.UserUtil
import com.burlakov.petogram.view.MainView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val mainPresenter by moxyPresenter { MainPresenter() }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (PetogramApplication.user == null) {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        } else mainPresenter.userDataIsOk()



        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
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
        UserUtil.saveUser(user, this)
    }

    override fun showMessage(message: String, isPositive: Boolean) {
        val dialog = MessageDialog().newInstance(LocalizeUtil.localize(message,this), isPositive)
        dialog.show(supportFragmentManager, "message")
    }
}