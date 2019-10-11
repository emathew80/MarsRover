package com.elsonmathew.android.nasaproject.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elsonmathew.android.nasaproject.R
import com.elsonmathew.android.nasaproject.adapters.RecyclerViewAdapter
import com.elsonmathew.android.nasaproject.models.Photo
import android.util.Log
import android.view.View


class ResultsActivity : AppCompatActivity() {

    lateinit var adapter: RecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val recyclerView: RecyclerView = findViewById(R.id.rv_cell)
        val numColumn = 2

        recyclerView.layoutManager = GridLayoutManager(this, numColumn)
        val photos = intent.getBundleExtra(INTENT_BUNDLE).get(INTENT_PHOTOS) as ArrayList<Photo>

        adapter = RecyclerViewAdapter(this, photos)
        recyclerView.adapter = adapter


    }


    companion object {

        private val INTENT_PHOTOS = "photos_for_intent"
        private val INTENT_BUNDLE = "bundle_for_intent"

        fun getRegisterIntent(context: Context, photos: ArrayList<Photo>): Intent {
            val intent = Intent(context, ResultsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelableArrayList(INTENT_PHOTOS, photos)
            intent.putExtra(INTENT_BUNDLE, bundle)
            return intent
        }
    }

}
