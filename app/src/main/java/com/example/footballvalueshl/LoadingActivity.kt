package com.example.footballvalueshl

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class LoadingActivity : AppCompatActivity() {
    private var players :MutableList<PlayingObject?> = ArrayList()
    private var clubs :MutableList<PlayingObject?> = ArrayList()
    private var countries :MutableList<PlayingObject?> = ArrayList()
    private val database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)


        var myRef = database.getReference("players")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children){
                    players.add(snapshot.getValue(PlayingObject::class.java))
                }
                SharedData.players=players
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })
        myRef = database.getReference("clubs")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children){
                    clubs.add(snapshot.getValue(PlayingObject::class.java))
                }
                SharedData.clubs=clubs
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })

        myRef = database.getReference("countries")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children){
                    countries.add(snapshot.getValue(PlayingObject::class.java))
                }
                SharedData.countries=countries
                toMenu()

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })


    }

    fun toMenu(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finishAffinity()
        exitProcess(0)
    }

}