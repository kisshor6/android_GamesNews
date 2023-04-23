package com.example.march24news

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GameListRecycler(private val listener : GamesItemClicked) : RecyclerView.Adapter<GameListRecycler.viewHolder>() {
    private val items : ArrayList<Games> = ArrayList()

    class viewHolder (itemview : View): RecyclerView.ViewHolder(itemview){
        val title = itemview.findViewById<TextView>(R.id.title)
        val image = itemview.findViewById<ImageView>(R.id.image)
        val author = itemview.findViewById<TextView>(R.id.author)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.games, parent, false)
        val viewHolder =viewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItems = items[position]
        holder.title.text = currentItems.title
        holder.author.text = currentItems.author
        Glide.with(holder.itemView.context).load(currentItems.imageUrl).into(holder.image)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateGames(updateGames : ArrayList<Games>){
        items.clear()
        items.addAll(updateGames)

        notifyDataSetChanged()
    }
}
interface GamesItemClicked{
    fun onItemClicked(item : Games)
}