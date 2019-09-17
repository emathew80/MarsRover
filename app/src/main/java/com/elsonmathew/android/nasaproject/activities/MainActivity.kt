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
import com.elsonmathew.android.nasaproject.interfaces.NasaPhotosApiService
import com.elsonmathew.android.nasaproject.interfaces.NasaRoverManifestApiService
import com.elsonmathew.android.nasaproject.models.Photo
import com.elsonmathew.android.nasaproject.models.PhotoManifest
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ProgressBar





class MainActivity : AppCompatActivity() {

	private lateinit var progressBar: ProgressBar
	private var roverDropdown: AutoCompleteTextView? = null
	private var cam_dropdown: AutoCompleteTextView? = null
	private var sol_dropdown: AutoCompleteTextView? = null
	private lateinit var photoManifest: PhotoManifest

	val nasaPhotosService by lazy {
		NasaPhotosApiService.create()
	}
	var nasaPhotosDisposable: Disposable? = null

	val nasaManifestService by lazy {
		NasaRoverManifestApiService.create()
	}
	var nasaManifestDisposable: Disposable? = null


	var camTextInputLayout: TextInputLayout? = null
	var solTextInputLayout: TextInputLayout? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		progressBar = findViewById<View>(R.id.my_progressBar) as ProgressBar


		camTextInputLayout = findViewById(R.id.cam_dropdown_text_input_layout)
		solTextInputLayout = findViewById<TextInputLayout>(R.id.sol)
		val rovers = listOf("Curiosity", "Opportunity", "Spirit")
		// Mars Rover Dropdown
		 roverDropdown = populateDropDownMenu(this, R.id.rover_dropdown, rovers)
		roverDropdown?.onItemClickListener = AdapterView.OnItemClickListener{ parent,view,position,id->
			val rover = parent.getItemAtPosition(position).toString()
			// Display the clicked item using toast
			Toast.makeText(applicationContext,"Selected : $rover",Toast.LENGTH_SHORT).show()
			grabManifestForRover(rover)
			camTextInputLayout?.visibility = View.GONE
			solTextInputLayout?.visibility = View.GONE
		}
	}

	private fun grabManifestForRover(rover: String) {
		progressBar.visibility = View.VISIBLE
		progressBar.isIndeterminate = true
		nasaManifestDisposable =
			nasaManifestService.getManifestForRover(rover)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{ manifestResult -> showManifestResults(manifestResult.photo_manifest) },
					{ error -> showError(error.message) }
				)

	}

	private fun showManifestResults(photoManifest: PhotoManifest) {
		progressBar.visibility = View.INVISIBLE
		this.photoManifest = photoManifest
		val showPhotosButton = findViewById<MaterialButton>(R.id.show_photos_button)

		solTextInputLayout?.visibility = View.VISIBLE
		solTextInputLayout?.hint = "Select a Sol between 1 and " + photoManifest.max_sol + " (Optional)"
		sol_dropdown = populateSolDropDownMenu(this, R.id.sol_dropdown, (1..photoManifest.max_sol).toList())
		val randomSol = (1..photoManifest.max_sol).shuffled().first()
		cam_dropdown = populateDropDownMenu(this, R.id.camera_dropdown, photoManifest.photos[randomSol].cameras)
		val sol = solTextInputLayout?.editText.toString().toIntOrNull()
		sol_dropdown?.onItemClickListener = AdapterView.OnItemClickListener{ parent,view,position,id->
			progressBar.visibility = View.VISIBLE
			val selectedSol = parent.getItemAtPosition(position) as Int
			cam_dropdown = populateDropDownMenu(this, R.id.camera_dropdown, photoManifest.photos[selectedSol].cameras)
			progressBar.visibility = View.INVISIBLE

		}
		camTextInputLayout?.visibility = View.VISIBLE
		showPhotosButton.isEnabled = true

		showPhotosButton.setOnClickListener {
			val rover = roverDropdown?.text.toString()
			val camera = camera_dropdown?.text.toString()
			val sol = sol_dropdown?.text.toString()

			beginSearch(rover, camera , sol)
		}

	}


	private fun populateDropDownMenu(context: Context, resId: Int, list: List<String>?): AutoCompleteTextView? {
		val arrayAdapter = ArrayAdapter(context,
			R.layout.dropdown_menu_popup_item, list)

		val dropdown = findViewById<AutoCompleteTextView>(resId)
		dropdown.setAdapter(arrayAdapter)
		return dropdown
	}

	private fun populateSolDropDownMenu(context: Context, resId: Int, list: List<Int>?): AutoCompleteTextView? {
		val arrayAdapter = ArrayAdapter(context,
			R.layout.dropdown_menu_popup_item, list)

		val dropdown = findViewById<AutoCompleteTextView>(resId)
		dropdown.setAdapter(arrayAdapter)
		return dropdown
	}

	private fun beginSearch(rover: String, cam: String, sol: String) {
		nasaPhotosDisposable =
		nasaPhotosService.getRoverPhotos(rover, sol, cam)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{ result -> showResult(result.photos) },
				{ error -> showError(error.message) }
			)
	}

	private fun showError(message: String?) {
		print(message)


	}

	private fun showResult(photos: List<Photo>) {
		if (photos.isEmpty()) {
			Toast.makeText(applicationContext,"There are no images for this rover on ${sol_dropdown?.text.toString()} ",Toast.LENGTH_LONG).show()
		}
		print(photos)

	}
}
