package com.example.footballvalueshl

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentList : Fragment() {
    private lateinit var itemsAdapter: ItemsRecyclerAdapter
    private lateinit var fragmentCallback: FragmentCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        fragmentCallback = context as FragmentCallback

        view.findViewById<ImageButton>(R.id.exitListBtn).setOnClickListener {
            fragmentCallback.onButtonPressed()
        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.itemsList).apply {
            itemsAdapter = ItemsRecyclerAdapter()
            itemsAdapter.postItemsList(toItemList())
            layoutManager = LinearLayoutManager(context)
            adapter = itemsAdapter
        }

    }

    fun toItemList() : ArrayList<PlayingObject>{
        val mode = arguments?.getString("MODE")
        val indexes = arguments?.getIntegerArrayList("LIST")
        val itemList : MutableList<PlayingObject?> =ArrayList()
        val resourceList : MutableList<PlayingObject?> = when(mode){
            "PLAYERS" -> SharedData.players
            "CLUBS" -> SharedData.clubs
            "COUNTRIES" -> SharedData.countries
            else -> (SharedData.countries + SharedData.clubs) as MutableList<PlayingObject?>

        }
        if (indexes != null) {
            for (index in indexes){
                itemList.add(resourceList[index])
            }
        }

        return itemList as ArrayList<PlayingObject>
    }



    }

