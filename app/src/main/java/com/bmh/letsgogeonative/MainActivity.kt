package com.bmh.letsgogeonative

import android.content.Intent
import android.graphics.drawable.AnimatedImageDrawable
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.bmh.letsgogeonative.databinding.ActivityMainBinding
import com.bmh.letsgogeonative.ui.login.SignInActivity
import com.bmh.letsgogeonative.utils.auth.AuthManager
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager
import com.bmh.letsgogeonative.viewModel.LoginViewModel
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.currentUser.observe(this) {
            Log.d("Faris", "IsUserLogin:: ${it != null}")
            if (it == null) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }

            if (it.email.isNotEmpty() && it.image.isNotEmpty()) {
                displayProfile(email = it.email, "",image = it.image)
            }
        }

        FirestoreManager().userProfile(::displayProfile)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        (binding.root.findViewById<ImageView>(R.id.homeBackground).drawable as AnimatedImageDrawable).start()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun displayProfile(email: String, name: String, image: String) {
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.userEmail).text = email
        val imgView = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.imageView)
        if (image.isNotEmpty()) {
            Picasso.get().load(image).into(imgView)
        }
        (binding.navView.background as AnimatedImageDrawable).start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_user_info -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.userProfile)
            }
            R.id.action_logout -> {
                AuthManager(this).logout()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}