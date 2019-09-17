package com.elsonmathew.android.nasaproject

/**
 * Created by elson.mathew on 2019-09-04.
 */
class NasaProjectEnums {
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
}