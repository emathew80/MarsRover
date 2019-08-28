package com.elsonmathew.android.nasaproject.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.elsonmathew.android.nasaproject.R
import com.elsonmathew.android.nasaproject.fragments.DatePickerFragment


class MainActivity : AppCompatActivity() {

	enum class Rovers(var roverName: String) {
		CURIOSITY("Curiosity"),
		OPPORTUNITY("Opportunity"),
		SPIRIT("Spirit")
	}

	enum class Cameras(var camName: String) {
		FHAZ("Front Hazard Avoidance Camera"),
		RHAZ("Rear Hazard Avoidance Camera"),
		MAST("Mast Camera"),
		CHEMCAM("Chemistry and Camera Complex"),
		MAHLI("Mars Hand Lens Imager"),
		MARDI("Mars Descent Imager"),
		NAVCAM("Navigation Camera"),
		PANCAM("Panoramic Camera"),
		MINITES("Miniature Thermal Emission Spectrometer (Mini-TES)")
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val rovers = listOf(Rovers.CURIOSITY.roverName, Rovers.OPPORTUNITY.roverName, Rovers.SPIRIT.roverName)
		// Mars Rover Dropdown
		val roverDropdown = populateDropDownMenu(this,
			R.id.filled_exposed_dropdown, rovers)
		roverDropdown?.onItemClickListener = AdapterView.OnItemClickListener{
				parent,view,position,id->
			val rover = parent.getItemAtPosition(position).toString()
			// Display the clicked item using toast
			Toast.makeText(applicationContext,"Selected : $rover",Toast.LENGTH_SHORT).show()
			populateDropDownMenu(this,
				R.id.camera_dropdown,  getCamerasforRover(rover)?.map { it.camName })
		}
		// Camera Dropdown
	}

	private fun getCamerasforRover(rover: String): List<Cameras>? {
		var roverCameraList: List<Cameras>? = listOf()
		when (rover){
			Rovers.CURIOSITY.roverName -> roverCameraList = listOf(
				Cameras.FHAZ,
				Cameras.RHAZ,
				Cameras.MAST,
				Cameras.CHEMCAM,
				Cameras.MAHLI,
				Cameras.MARDI,
				Cameras.NAVCAM
			)
			Rovers.OPPORTUNITY.roverName -> roverCameraList = listOf(
				Cameras.FHAZ,
				Cameras.RHAZ,
				Cameras.NAVCAM,
				Cameras.PANCAM,
				Cameras.MINITES
			)
			Rovers.SPIRIT.roverName -> roverCameraList = listOf(
				Cameras.FHAZ,
				Cameras.RHAZ,
				Cameras.NAVCAM,
				Cameras.PANCAM,
				Cameras.MINITES
			)
		}
		return roverCameraList
	}

	private fun populateDropDownMenu(context: Context, resId: Int, roverList: List<String>?): AutoCompleteTextView? {
		val arrayAdapter = ArrayAdapter(context,
			R.layout.dropdown_menu_popup_item, roverList)

		val dropdown = findViewById<AutoCompleteTextView>(resId)
		dropdown.setAdapter(arrayAdapter)
		return dropdown
	}

	fun showDatePickerDialog(v: View) {
		val newFragment = DatePickerFragment()
		newFragment.show(supportFragmentManager, "datePicker")
	}
}
