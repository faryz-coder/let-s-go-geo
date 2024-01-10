package com.bmh.letsgogeonative

import android.content.Intent
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bmh.letsgogeonative.databinding.SplashScreenBinding
import com.bmh.letsgogeonative.ui.login.SelectionActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SplashScreenBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)
        (binding.imageView4.drawable as AnimatedImageDrawable).start()
            .let {
                CoroutineScope(Dispatchers.Default).launch {
                    delay(3000)
                    withContext(Dispatchers.Main) {
                        navigateToSelection()
                    }
                }
            }
    }

    private fun navigateToSelection() {
        val intent = Intent(this, SelectionActivity::class.java)
        startActivity(intent)
        finish()
    }
}