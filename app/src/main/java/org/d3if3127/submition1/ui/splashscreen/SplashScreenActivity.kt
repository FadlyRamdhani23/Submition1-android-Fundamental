package org.d3if3127.submition1.ui.splashscreen

    import android.content.Intent
    import android.os.Bundle
    import android.os.Handler
    import android.os.Looper
    import androidx.appcompat.app.AppCompatActivity
    import org.d3if3127.submition1.R
    import org.d3if3127.submition1.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {


        private val SPLASH_DISPLAY_LENGTH = 2000

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash_screen)

            Handler(Looper.getMainLooper()).postDelayed({
                // Intent untuk memulai MainActivity
                val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(mainIntent)
                finish() // Menutup SplashScreenActivity
            }, SPLASH_DISPLAY_LENGTH.toLong())
        }
    }
