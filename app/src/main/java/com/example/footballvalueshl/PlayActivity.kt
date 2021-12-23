package com.example.footballvalueshl



import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random


interface FragmentCallback {
    fun onButtonPressed(higher: Boolean)
}

class PlayActivity : AppCompatActivity() {
    private var items :MutableList<Player?> = ArrayList()
    private val database = Firebase.database
    private var usedItems:MutableList<Int> = ArrayList()
    private lateinit var firstItem : Item
    private lateinit var secondItem : Item
    private lateinit var thirdItem : Item
    private var playerOne : Player? = null
    private var playerTwo : Player? = null
    private var playerQueue : Player? = null

    private var score = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        val playMode = intent.getStringExtra("MODE").toString()



        val myRef = database.getReference(playMode)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children){
                    items.add(snapshot.getValue(Player::class.java))
                }
                Play()
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })


    }


    private fun getRandomPlayer(): Player? {
        var randomIndex : Int
        do{
           randomIndex = Random.nextInt(items.size-1)
        }while (randomIndex in usedItems)
        usedItems.add(randomIndex)
        Log.d("LENGTH","LENGTH = ${randomIndex}")
        return items[randomIndex]

    }

    private fun InitialTransaction(){
        firstItem = Item()
        secondItem = Item()
        thirdItem = Item()
        val bundleFirst = Bundle()
        val bundleSecond = Bundle()
        val bundleQueued = Bundle()

        playerOne = getRandomPlayer()
        playerTwo = getRandomPlayer()
        playerQueue = getRandomPlayer()

        bundleFirst.putBoolean("FIRST",true)
        bundleFirst.putString("NAME",playerOne?.name)
        bundleFirst.putString("IMAGE",playerOne?.picture)
        bundleFirst.putString("PRICE",playerOne?.price)
        firstItem.arguments=bundleFirst


        bundleSecond.putString("NAME",playerTwo?.name)
        bundleSecond.putString("IMAGE",playerTwo?.picture)
        bundleSecond.putString("PRICE",playerTwo?.price)
        secondItem.arguments=bundleSecond

        bundleQueued.putString("NAME",playerQueue?.name)
        bundleQueued.putString("IMAGE",playerQueue?.picture)
        bundleQueued.putString("PRICE",playerQueue?.price)
        thirdItem.arguments=bundleQueued

        val fragmentTransaction: FragmentTransaction? =
            supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.firstItemFragment, firstItem)
        fragmentTransaction?.replace(R.id.secondItemFragment,secondItem)
        fragmentTransaction?.replace(R.id.queueItemFragment,thirdItem)
        fragmentTransaction?.commit()
    }

    private fun OnCorrectAnswer(){
        score+=1

    }

    fun CheckAnswer(higher:Boolean):Boolean{
        if((playerOne?.price?.toDouble()!! >= playerTwo?.price?.toDouble()!!) && higher) return true
        if((playerOne?.price?.toDouble()!! <= playerTwo?.price?.toDouble()!!) && !higher) return true
        return false
    }
    private fun Play(){
        InitialTransaction()
        OnCorrectAnswer()


    }

}