package com.example.footballvalueshl



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.animation.AnticipateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.FragmentTransaction





class PlayActivity : AppCompatActivity(),FragmentCallback {

    private lateinit var endGameIntent :Intent
    private lateinit var playMode:String
    private var items :MutableList<PlayingObject?> = ArrayList()
    private var usedItems:MutableList<Int> = ArrayList()
    private lateinit var firstItem : Item
    private lateinit var secondItem : Item
    private lateinit var thirdItem : Item
    private var playerOne : PlayingObject? = null
    private var playerTwo : PlayingObject? = null
    private var playerThree : PlayingObject? = null
    private lateinit var vsTv:TextView
    private  var token: Int =0

    private var score = 0

    override fun onButtonPressed(higher: Boolean) {
        val correctAnswer:Boolean
        if((playerOne?.price?.toDouble()!! >= playerTwo?.price?.toDouble()!!) && !higher) correctAnswer= true
        else correctAnswer = (playerOne?.price?.toDouble()!! <= playerTwo?.price?.toDouble()!!) && higher

        if(correctAnswer) {
            onCorrectAnswer()

        }
        else onFalseAnswer()
        vsTv.text=score.toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        playMode=intent.getStringExtra("MODE").toString()
        endGameIntent= Intent(this, EndActivity::class.java)
        endGameIntent.putExtra("MODE", playMode)

        when(playMode){
            "PLAYERS" -> items = SharedData.players
            "CLUBS" -> items = SharedData.clubs
            "COUNTRIES" -> items = SharedData.countries
            "CVC"  -> items = (SharedData.countries + SharedData.clubs) as MutableList<PlayingObject?>
        }

        vsTv=findViewById(R.id.tvvs)
        initialTransaction()


    }

    private fun uniqueRandomIndex(lowerBound:Int, higherBound:Int):Int{
        var randomIndex : Int
        do {
            randomIndex =(lowerBound until higherBound).random()
        } while (randomIndex in usedItems)
        usedItems.add(randomIndex)
        if(usedItems.size==items.size)usedItems.clear()
        return randomIndex
    }


    private fun getRandomPlayer(): PlayingObject? {


        if(playMode=="CVC" && usedItems.size!=0){
            val middle=(items.size/2)
            if (usedItems[usedItems.size-1] < middle)return items[uniqueRandomIndex(middle,items.size)]

            return items[uniqueRandomIndex(0,middle)]
        }

       return items[uniqueRandomIndex(0,items.size)]


    }

    private fun initialTransaction(){
        firstItem = Item()
        secondItem = Item()
        thirdItem = Item()

        val bundleFirst = Bundle()
        val bundleSecond = Bundle()
        val bundleQueued = Bundle()


        playerOne = getRandomPlayer()
        playerTwo = getRandomPlayer()
        playerThree = getRandomPlayer()

        bundleFirst.putBoolean("FIRST",true)
        bundleFirst.putString("NAME",playerOne?.name)
        bundleFirst.putString("IMAGE",playerOne?.picture)
        bundleFirst.putString("PRICE",playerOne?.price)
        firstItem.arguments=bundleFirst


        bundleSecond.putString("NAME",playerTwo?.name)
        bundleSecond.putString("IMAGE",playerTwo?.picture)
        bundleSecond.putString("PRICE",playerTwo?.price)
        secondItem.arguments=bundleSecond

        bundleQueued.putString("NAME",playerThree?.name)
        bundleQueued.putString("IMAGE",playerThree?.picture)
        bundleQueued.putString("PRICE",playerThree?.price)
        thirdItem.arguments=bundleQueued


        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.firstItemFragment, firstItem)
        fragmentTransaction.add(R.id.secondItemFragment,secondItem)
        fragmentTransaction.add(R.id.thirdItemFragment,thirdItem)
        fragmentTransaction.commit()
    }



    private fun onCorrectAnswer(){
        score+=1

        playerOne = playerTwo
        playerTwo = playerThree
        playerThree = getRandomPlayer()

        val bundleQueued = Bundle()
        bundleQueued.putString("NAME",playerThree?.name)
        bundleQueued.putString("IMAGE",playerThree?.picture)
        bundleQueued.putString("PRICE",playerThree?.price)


        val queueItem = Item()
        queueItem.arguments=bundleQueued


        when(token){
            0 ->
            {
                swapFrames(R.layout.activity_play_second)
                makeTransaction(R.id.zeroItemFragment,queueItem)

            }
            1 ->
            {
                swapFrames(R.layout.activity_play_third)
                makeTransaction(R.id.firstItemFragment,queueItem)

            }

            2 ->
            {
                swapFrames(R.layout.activity_play_fourth)
                makeTransaction(R.id.secondItemFragment,queueItem)


            }
            3 ->
            {
                swapFrames(R.layout.activity_play)
                makeTransaction(R.id.thirdItemFragment,queueItem)


            }
        }


    }

    private fun makeTransaction(id:Int, item:Item){
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(id,item)
        fragmentTransaction.commit()

    }
    private fun onFalseAnswer(){
        val history= usedItems.slice(0..usedItems.size-2) as ArrayList<Int>
        endGameIntent.putExtra("SCORE", score)
        endGameIntent.putExtra("LIST", history)
        vsTv.setBackgroundResource(R.drawable.circle_text_view_false)
        Handler().postDelayed({
            startActivity(endGameIntent)
        }, 500)

    }

    private fun swapFrames(layoutId: Int){

        val constraintSet = ConstraintSet()
        constraintSet.clone(this,layoutId)

        val transition=ChangeBounds()
        transition.interpolator=DecelerateInterpolator()
        transition.duration=300


        TransitionManager.beginDelayedTransition(findViewById(R.id.constraintLayout),transition)
        constraintSet.applyTo(findViewById(R.id.constraintLayout))

        when(token){
            0 -> token++
            1 -> token++
            2 -> token++
            3 -> token=0

        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}