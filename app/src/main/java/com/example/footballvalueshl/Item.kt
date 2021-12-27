package com.example.footballvalueshl


import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide



class Item : Fragment() {
    private lateinit var fragmentCallback: FragmentCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_item, container,
            false)
        fragmentCallback = context as FragmentCallback


        val higherBtn=view.findViewById<Button>(R.id.higherBtn)
        val lowerBtn=view.findViewById<Button>(R.id.lowerBtn)
        val price=view.findViewById<TextView>(R.id.itemPrice)
        val photo = view.findViewById<ImageView>(R.id.itemPicture)
        val name = view.findViewById<TextView>(R.id.itemName)



        val isFirst = arguments?.getBoolean("FIRST")
        val value= arguments?.getString("PRICE")?.toDouble()



        Glide
            .with(view.context)
            .load(arguments?.getString("IMAGE"))
            .centerCrop()
            .into(photo)
        name.text = arguments?.getString("NAME")



        higherBtn.visibility = View.GONE
        lowerBtn.visibility = View.GONE
        price.text = "${value}m€"
        if(isFirst==false){
            higherBtn.visibility=View.VISIBLE
            lowerBtn.visibility=View.VISIBLE
            price.visibility=View.INVISIBLE
        }

        fun CheckPrice(higher: Boolean){
            Thread(Runnable {
                var zeroVal = 0.00
                while (zeroVal< value!!) {
                    Thread.sleep(20)
                    zeroVal+=1
                    activity?.runOnUiThread{price.text="${zeroVal}m€"}

                }
                Thread.sleep(400)
                activity?.runOnUiThread{fragmentCallback.onButtonPressed(higher)}


            }).start()

        }


                higherBtn.setOnClickListener {

                    higherBtn.visibility = View.GONE
                    lowerBtn.visibility = View.GONE
                    price.visibility = View.VISIBLE

                    CheckPrice(true)


                }
                lowerBtn.setOnClickListener{

                        higherBtn.visibility = View.GONE
                        lowerBtn.visibility = View.GONE
                        price.visibility = View.VISIBLE

                        CheckPrice(false)


                }





        return view
    }


}
