package com.bmh.letsgogeonative.ui.login

import android.content.Intent
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bmh.letsgogeonative.databinding.UsersSelectionBinding

class SelectionActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: UsersSelectionBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UsersSelectionBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)

        binding.userAdmin.setOnClickListener(this)
        binding.userNormal.setOnClickListener(this)
        (binding.selectionBackgroudImg.drawable as AnimatedImageDrawable).start()
    }

    // False = Normal
    // True = Admin
    override fun onClick(btn: View) {
        when (btn.id) {
            binding.userAdmin.id -> {
                val intent = Intent(this, SignInActivity::class.java)
                intent.putExtra("type", true)
                startActivity(intent)
            }
            binding.userNormal.id -> {
                val intent = Intent(this, SignInActivity::class.java)
                intent.putExtra("type", false)
                startActivity(intent)
            }
        }
    }
}