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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartupActivity : AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding
    private lateinit var job: Job
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SplashScreenBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)

        (binding.imageView4.drawable as AnimatedImageDrawable).start()
            .let {
                job = CoroutineScope(Dispatchers.Default).launch {
                    delay(3000)
                    withContext(Dispatchers.Main) {
                        navigateToSelection()
                    }
                }
            }
    }

    override fun onStop() {
        super.onStop()
        if (job.isActive) {
            job.cancel()
        }
    }

    private fun navigateToSelection() {
        val intent = Intent(this, SelectionActivity::class.java)
        startActivity(intent)
        finish()
    }
}