package com.hsd.contest.spain.view

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.hsd.contest.spain.R
import com.hsd.contest.spain.component.bottomview.BottomNavigationConfigurationModel
import com.hsd.contest.spain.component.bottomview.BottomNavigationDefaultModel
import com.hsd.contest.spain.component.bottomview.BottomNavigationManager
import com.hsd.contest.spain.component.bottomview.MyBottomNavigationView
import com.hsd.contest.spain.databinding.MenuActivityBinding
import com.huawei.hmf.tasks.Task
import com.huawei.hms.common.ApiException
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.*

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: MenuActivityBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationRequest: LocationRequest? = null
    private lateinit var settingsClient: SettingsClient
    private var locationCallback: LocationCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            bottomNavigationManager.init()
        }
        bottomNavigationManager.initListeners()
        // create fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // create settingsClient
        settingsClient = LocationServices.getSettingsClient(this)
        locationRequest = LocationRequest().apply {
            // set the interval for location updates, in milliseconds
            interval = 1000
            needAddress = true
            // set the priority of the request
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null) {
                    val locations: List<Location> =
                        locationResult.locations
                    if (locations.isNotEmpty()) {
                        for (location in locations) {
                            Log.i(
                                "TAG",
                                "onLocationResult location[Longitude,Latitude,Accuracy]:${location.longitude} , ${location.latitude} , ${location.accuracy}"
                            )
                            //removeLocationUpdatesWithCallback()
                        }
                    }
                }
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                locationAvailability?.let {
                    val flag: Boolean = locationAvailability.isLocationAvailable
                    Log.i("TAG", "onLocationAvailability isLocationAvailable:$flag")
                }
            }
        }

        verifyPermissions()
        requestLocationUpdatesWithCallback()
    }

    private fun verifyPermissions() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                requestPermissions(strings, 1)
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                )
                requestPermissions(strings, 2)
            }
        }
    }

    private val bottomNavigationManager: BottomNavigationManager by lazy {
        BottomNavigationManager(
            defaultModel = BottomNavigationDefaultModel(
                bottomIdGraphId = mapOf(
                    MyBottomNavigationView.Selected.NONE to R.navigation.home_navigation,
                    MyBottomNavigationView.Selected.START to R.navigation.weather_navigation,
                    MyBottomNavigationView.Selected.END to R.navigation.profile_navigation,
                ),
                mainBottomId = MyBottomNavigationView.Selected.NONE,
                containerId = binding.fcvActivityMenu.id,
                fragmentManager = supportFragmentManager,
                bottomNavigation = binding.mbnvActivityMenu,
                centerButton = binding.fabActivityMenu
            ),
            configurationModel = BottomNavigationConfigurationModel(
                keepWhenSelect = true,
                resetWhenReselect = true,
                goMainWhenBackPressed = true
            )
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mbnvActivityMenu.onSaveInstanceState(outState = outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.mbnvActivityMenu.onRestoreInstanceState(savedInstanceState = savedInstanceState)
        bottomNavigationManager.restore()
    }

    override fun onBackPressed() {
        if (!bottomNavigationManager.backPressed()) {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "onRequestPermissionsResult: apply LOCATION PERMISSION successful")
            } else {
                Log.i("TAG", "onRequestPermissionsResult: apply LOCATION PERMISSSION  failed")
            }
        }
        if (requestCode == 2) {
            if (grantResults.size > 2 && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i(
                    "TAG",
                    "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION successful"
                )
            } else {
                Log.i("TAG", "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION  failed")
            }
        }
    }

    /**
     * function:Requests location updates with a callback on the specified Looper thread.
     * first:use SettingsClient object to call checkLocationSettings(LocationSettingsRequest locationSettingsRequest) method to check device settings.
     * second: use  FusedLocationProviderClient object to call requestLocationUpdates (LocationRequest request, LocationCallback callback, Looper looper) method.
     */
    private fun requestLocationUpdatesWithCallback() {
        try {
            val builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(locationRequest)
            val locationSettingsRequest = builder.build()
            // check devices settings before request location updates.
            //Before requesting location update, invoke checkLocationSettings to check device settings.
            val locationSettingsResponseTask: Task<LocationSettingsResponse> =
                settingsClient.checkLocationSettings(locationSettingsRequest)

            locationSettingsResponseTask.addOnSuccessListener { locationSettingsResponse: LocationSettingsResponse? ->
                Log.i("TAG", "check location settings success  {$locationSettingsResponse}")
                // request location updates
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
                    .addOnSuccessListener {
                        Log.i("TAG", "requestLocationUpdatesWithCallback onSuccess")
                    }
                    .addOnFailureListener { e ->
                        Log.e("TAG", "requestLocationUpdatesWithCallback onFailure:${e.message}")
                    }
            }
                .addOnFailureListener { e: Exception ->
                    Log.e("TAG", "checkLocationSetting onFailure:${e.message}")
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(
                                this, 0
                            )
                        } catch (sie: IntentSender.SendIntentException) {
                            Log.e("TAG", "PendingIntent unable to execute request.")
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e("TAG", "requestLocationUpdatesWithCallback exception:${e.message}")
        }
    }

    private fun removeLocationUpdatesWithCallback() {
        try {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                .addOnSuccessListener {
                    Log.i(
                        "TAG",
                        "removeLocationUpdatesWithCallback onSuccess"
                    )
                }
                .addOnFailureListener { e ->
                    Log.e(
                        "TAG",
                        "removeLocationUpdatesWithCallback onFailure:${e.message}"
                    )
                }
        } catch (e: Exception) {
            Log.e(
                "TAG",
                "removeLocationUpdatesWithCallback exception:${e.message}"
            )
        }
    }

}