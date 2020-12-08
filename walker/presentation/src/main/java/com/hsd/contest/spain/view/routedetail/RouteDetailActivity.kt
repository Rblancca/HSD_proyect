package com.hsd.contest.spain.view.routedetail

import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.hsd.contest.spain.databinding.RouteDetailActivityBinding
import com.huawei.hmf.tasks.Task
import com.huawei.hms.ads.HwAds
import com.huawei.hms.common.ApiException
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.*

class RouteDetailActivity : AppCompatActivity() {
    lateinit var userLocation: Location

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationRequest: LocationRequest? = null
    private lateinit var settingsClient: SettingsClient
    private var locationCallback: LocationCallback? = null
    val args: RouteDetailActivityArgs by navArgs()
    private lateinit var binding: RouteDetailActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RouteDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        HwAds.init(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        settingsClient = LocationServices.getSettingsClient(this)
        locationRequest = LocationRequest().apply {
            interval = 36000
            needAddress = true
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null) {
                    val locations: List<Location> =
                        locationResult.locations
                    if (locations.isNotEmpty()) {
                        for (location in locations) {
                            userLocation = location
                            Log.i(
                                "TAG",
                                "onLocationResult location[Longitude,Latitude,Accuracy]:${location.longitude} , ${location.latitude} , ${location.accuracy}"
                            )
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

        requestLocationUpdatesWithCallback()
    }


    private fun requestLocationUpdatesWithCallback() {
        try {
            val builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(locationRequest)
            val locationSettingsRequest = builder.build()
            val locationSettingsResponseTask: Task<LocationSettingsResponse> =
                settingsClient.checkLocationSettings(locationSettingsRequest)

            locationSettingsResponseTask.addOnSuccessListener { locationSettingsResponse: LocationSettingsResponse? ->
                Log.i("TAG", "check location settings success  {$locationSettingsResponse}")
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
                    .addOnSuccessListener {
                        Log.i("TAG", "requestLocationUpdatesWithCallback onSuccess")
                    }
                    .addOnFailureListener { e ->
                        Log.e(
                            "TAG",
                            "requestLocationUpdatesWithCallback onFailure:${e.message}"
                        )
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
}