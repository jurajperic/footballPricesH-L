package com.example.footballvalueshl


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemsRecyclerAdapter:
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<PlayingObject> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {

        return PlayingObjectViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent,
                false)
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
                                  position: Int) {

        when(holder) {
            is PlayingObjectViewHolder -> {
                holder.bind(items[position])
            }
        }
    }
    override fun getItemCount(): Int {

        return items.size
    }
    fun postItemsList(data: ArrayList<PlayingObject>) {

        items = data

    }
    class PlayingObjectViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        private val img: ImageView =
            itemView.findViewById(R.id.itemPhoto)
        private val itemName: TextView =
            itemView.findViewById(R.id.itemName)
        private val itemPrice: TextView =
            itemView.findViewById(R.id.itemPrice)

        fun bind(item: PlayingObject) {

            Glide
                .with(itemView.context)
                .load(item.picture)
                .fitCenter()
                .into(img)
            itemName.text = item.name
            itemPrice.text = item.price + "m€"

        }
    }
}