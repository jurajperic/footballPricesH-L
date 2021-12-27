package com.example.footballvalueshl

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback

class EndActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        val playAgainBtn = findViewById<Button>(R.id.playAbtn)
        val menuBtn = findViewById<Button>(R.id.menuBtn)
        val score = intent.getIntExtra("SCORE",0)
        val scoreTv = findViewById<TextView>(R.id.score)
        val HscoreTv = findViewById<TextView>(R.id.highscore)
        var prefs = this.getSharedPreferences("scores", Context.MODE_PRIVATE)
        var hScore = prefs.getInt("playersHS",0)

        Log.d("HSCORE",hScore.toString())
        if(score>hScore){
            hScore=score
            val editor = prefs.edit()
            editor.putInt("playersHS",hScore)
            editor.commit()
        }
        scoreTv.text=score.toString()
        HscoreTv.text=hScore.toString()

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
        playAgainIntent.putExtra("MODE","players")
        startActivity(playAgainIntent)
    }
}