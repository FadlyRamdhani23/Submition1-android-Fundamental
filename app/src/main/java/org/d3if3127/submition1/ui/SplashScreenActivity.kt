package org.d3if3127.submition1.ui

    import android.content.Intent
    import android.os.Bundle
    import android.os.Handler
    import androidx.appcompat.app.AppCompatActivity
    import org.d3if3127.submition1.R

class SplashScreenActivity : AppCompatActivity() {

        // Waktu tampilan splash screen (dalam milidetik)
        private val SPLASH_DISPLAY_LENGTH = 2000

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash_screen)

            // Handler untuk menunda tindakan selama beberapa detik
            Handler().postDelayed({
                // Intent untuk memulai MainActivity
                val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(mainIntent)
                finish() // Menutup SplashScreenActivity
            }, SPLASH_DISPLAY_LENGTH.toLong())
        }
    }
