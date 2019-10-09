package com.elsonmathew.android.nasaproject.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.elsonmathew.android.nasaproject.R
import com.elsonmathew.android.nasaproject.models.Photo

import kotlinx.android.synthetic.main.activity_results.*



class ResultsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        setSupportActionBar(toolbar)
        val photos = intent.getBundleExtra(INTENT_BUNDLE).get(INTENT_PHOTOS) as ArrayList<Photo>

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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
