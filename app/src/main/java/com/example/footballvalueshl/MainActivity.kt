package com.example.footballvalueshl


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_layout)

        val playersBtn=findViewById<Button>(R.id.playersBtn)
        val clubsBtn = findViewById<Button>(R.id.clubsBtn)
        val countriesBtn=findViewById<Button>(R.id.countriesBtn)
        val cvcButton = findViewById<Button>(R.id.multipleGameBtn)
        val intent = Intent(this, PlayActivity::class.java)
        playersBtn.setOnClickListener {
            intent.putExtra("MODE","PLAYERS")
            startActivity(intent)

        }
        clubsBtn.setOnClickListener {
            intent.putExtra("MODE","CLUBS")
            startActivity(intent)

        }
        countriesBtn.setOnClickListener {
            intent.putExtra("MODE","COUNTRIES")
            startActivity(intent)

        }
        cvcButton.setOnClickListener {
            intent.putExtra("MODE","CVC")
            startActivity(intent)

        }
    }

    override fun onBackPressed() {
        finishAffinity()
        exitProcess(0)
    }
}