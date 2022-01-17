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
        val value= arguments?.getString("PRICE")?.toFloat()



        Glide
            .with(view.context)
            .load(arguments?.getString("IMAGE"))
            .centerCrop()
            .into(photo)
        name.text = arguments?.getString("NAME")



        higherBtn.visibility = View.GONE
        lowerBtn.visibility = View.GONE
        price.text =getString(R.string.default_price,value)
        if(isFirst==false){
            higherBtn.visibility=View.VISIBLE
            lowerBtn.visibility=View.VISIBLE
            price.visibility=View.INVISIBLE
        }

        fun checkPrice(higher: Boolean){
            Thread(Runnable {
                var zeroVal = 0.00
                val add=value!!.div(75)
                while (zeroVal< value) {
                    Thread.sleep(20)
                    zeroVal+=add
                    activity?.runOnUiThread{price.text=getString(R.string.default_price,zeroVal)}

                }
                activity?.runOnUiThread{price.text=getString(R.string.default_price,value)}
                Thread.sleep(200)
                activity?.runOnUiThread{fragmentCallback.onButtonPressed(higher)}


            }).start()

        }


                higherBtn.setOnClickListener {

                    higherBtn.visibility = View.GONE
                    lowerBtn.visibility = View.GONE
                    price.visibility = View.VISIBLE

                    checkPrice(true)


                }
                lowerBtn.setOnClickListener{

                        higherBtn.visibility = View.GONE
                        lowerBtn.visibility = View.GONE
                        price.visibility = View.VISIBLE

                        checkPrice(false)


                }





        return view
    }


}
