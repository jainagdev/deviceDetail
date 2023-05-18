package com.jai.deviceid.view.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import com.jai.deviceid.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

        setContentView(R.layout.activity_splash)
        Handler(Handler().looper).postDelayed(Runnable {
            intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)
    }
}