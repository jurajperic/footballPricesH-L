package com.example.footballvalueshl

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

class EndActivity : AppCompatActivity(),FragmentCallback {
    private lateinit var mode:String
    private lateinit var playAgainBtn:Button
    private lateinit var menuBtn:Button
    private lateinit var itemsList:FrameLayout
    private var state = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        mode= intent.getStringExtra("MODE").toString()
        val list=intent.getIntegerArrayListExtra("LIST")
        playAgainBtn = findViewById(R.id.playAbtn)
        itemsList= findViewById(R.id.itemsListFragment)
        val moreBtn= findViewById<ImageButton>(R.id.itemHistoryBtn)
        menuBtn = findViewById(R.id.menuBtn)
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

        val sureFragment = FragmentList()
        val bundle = Bundle()
        bundle.putIntegerArrayList("LIST", list)
        bundle.putString("MODE",mode)
        sureFragment.arguments = bundle
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.itemsListFragment, sureFragment)
        fragmentTransaction.commit()

        menuBtn.setOnClickListener{
            val menuIntent = Intent(this, MainActivity::class.java)
            startActivity(menuIntent)
        }
        playAgainBtn.setOnClickListener{
            onBackPressed()
        }

        moreBtn.setOnClickListener {
            playAgainBtn.visibility=View.INVISIBLE
            menuBtn.visibility=View.INVISIBLE
            itemsList.visibility= View.VISIBLE
            state=false

        }
    }

    override fun onBackPressed() {
        if (state) {
            val playAgainIntent = Intent(this, PlayActivity::class.java)
            playAgainIntent.putExtra("MODE", mode)
            startActivity(playAgainIntent)
        }
        else{
            onButtonPressed()
        }
    }

    override fun onButtonPressed(higher: Boolean) {
        state = true
        playAgainBtn.visibility=View.VISIBLE
        menuBtn.visibility=View.VISIBLE
        itemsList.visibility= View.INVISIBLE
    }
}