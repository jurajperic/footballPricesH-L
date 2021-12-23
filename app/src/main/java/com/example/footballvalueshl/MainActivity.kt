package com.example.footballvalueshl

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_layout)


        val playersBtn=findViewById<Button>(R.id.playersBtn)

        playersBtn.setOnClickListener {
            val playersIntent = Intent(this, PlayActivity::class.java)
            playersIntent.putExtra("MODE","players")
            startActivity(playersIntent)

        }
    }
}