package com.example.mishorasextras.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.mishorasextras.R
import com.example.mishorasextras.activities.Login

class SplashScreen : AppCompatActivity() {

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }, 3000) //3 segundos

    }
}