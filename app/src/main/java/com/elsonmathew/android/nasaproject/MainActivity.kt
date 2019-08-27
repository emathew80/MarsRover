package com.elsonmathew.android.nasaproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val rovers = listOf("Curiosity", "Opportunity", "Spirit")
		val cameras = listOf("Front Hazard Avoidance Camera", "Rear Hazard Avoidance Camera", "Mast Camera",
			"Chemistry and Camera Complex", "Mars Hand Lens Imager", "Mars Descent Imager", "Navigation Camera", "Panoramic Camera")
		// Mars Rover Dropdown
		val roverDropdown = populateDropDownMenu(this, rovers)
		roverDropdown?.onItemClickListener = AdapterView.OnItemClickListener{
				parent,view,position,id->
			val selectedItem = parent.getItemAtPosition(position).toString()
			// Display the clicked item using toast
			Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
		}
		// Camera Dropdown
	}

	private fun populateDropDownMenu(context: Context, spinnerList: List<String>): AutoCompleteTextView? {
		val arrayAdapter = ArrayAdapter(context, R.layout.dropdown_menu_popup_item, spinnerList)
		val dropdown = findViewById<AutoCompleteTextView>(R.id.filled_exposed_dropdown)
		dropdown.setAdapter(arrayAdapter)
		return dropdown
	}


}
