package com.example.footballvalueshl


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide



class Item : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, container,
            false)

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



        if(isFirst == true){
            higherBtn.visibility=View.INVISIBLE
            lowerBtn.visibility=View.INVISIBLE
            price.text = "${value}m€"
        }
        else{
            price.visibility=View.INVISIBLE
        }

        fun CheckPrice(){
            Thread(Runnable {
                var zeroVal = 0.00
                while (zeroVal< value!!) {
                    zeroVal+=1
                    activity?.runOnUiThread{price.text="${zeroVal}m€"}
                    Thread.sleep(20)
                }
            }).start()
        }

        higherBtn.setOnClickListener {
            higherBtn.visibility=View.INVISIBLE
            lowerBtn.visibility=View.INVISIBLE
            price.visibility=View.VISIBLE

            CheckPrice()



        }

        lowerBtn.setOnClickListener {
            higherBtn.visibility=View.INVISIBLE
            lowerBtn.visibility=View.INVISIBLE
            price.visibility=View.VISIBLE

            CheckPrice()

        }


        return view
    }


}
