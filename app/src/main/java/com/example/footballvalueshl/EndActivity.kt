package com.example.footballvalueshl

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class EndActivity : AppCompatActivity() {
    private lateinit var mode:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        mode= intent.getStringExtra("MODE").toString()
        val playAgainBtn = findViewById<Button>(R.id.playAbtn)
        val menuBtn = findViewById<Button>(R.id.menuBtn)
        val score = intent.getIntExtra("SCORE",0)
        val scoreTv = findViewById<TextView>(R.id.score)
        val hscoreTv = findViewById<TextView>(R.id.highscore)
        val prefs = this.getSharedPreferences("scores", Context.MODE_PRIVATE)
        val key= mode+"HS"

        var hScore = prefs.getInt(key,0)


        if(score>hScore){
            hScore=score
            val editor = prefs.edit()
            editor.putInt(key,hScore)
            editor.apply()
        }
        scoreTv.text=score.toString()
        hscoreTv.text=hScore.toString()

        menuBtn.setOnClickListener{
            val menuIntent = Intent(this, MainActivity::class.java)
            startActivity(menuIntent)
        }
        playAgainBtn.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        val playAgainIntent = Intent(this, PlayActivity::class.java)
        playAgainIntent.putExtra("MODE",mode)
        startActivity(playAgainIntent)
    }
}