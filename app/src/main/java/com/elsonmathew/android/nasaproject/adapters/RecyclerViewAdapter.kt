package com.elsonmathew.android.nasaproject.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.elsonmathew.android.nasaproject.R
import com.elsonmathew.android.nasaproject.models.Photo

class RecyclerViewAdapter// data is passed into the constructor
internal constructor(context: Context, private val mData: ArrayList<Photo>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext: Context = context
    private var mClickListener: ItemClickListener? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }


    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myTextView.text = "Image ID:" + mData[position].id.toString()
        Glide.with(mContext).load(mData[position].img_src).into(holder.myImgView)
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }


    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var myTextView: TextView = itemView.findViewById(R.id.rv_text_view)
        internal var myImgView: ImageView = itemView.findViewById(R.id.mars_img)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
            Log.d("Click", adapterPosition.toString())
        }
    }

    // convenience method for getting data at click position
    internal fun getItem(id: Int): Photo {
        return mData[id]
    }

    // allows clicks events to be caught
    internal fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}