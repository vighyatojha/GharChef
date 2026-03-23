package com.example.gharchef

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.Locale

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var tvAddress: TextView
    private lateinit var btnMyLocation: Button
    private lateinit var btnConfirm: Button
    private lateinit var progressBar: ProgressBar

    private val db   = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private var selectedLat = 0.0
    private var selectedLng = 0.0
    private var selectedAddress = ""

    // Default centre: India
    private val defaultLatLng = LatLng(20.5937, 78.9629)

    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        tvAddress     = findViewById(R.id.tvAddress)
        btnMyLocation = findViewById(R.id.btnMyLocation)
        btnConfirm    = findViewById(R.id.btnConfirmLocation)
        progressBar   = findViewById(R.id.progressBar)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<android.widget.ImageView>(R.id.ivBack)
            .setOnClickListener { finish() }

        // Init map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnMyLocation.setOnClickListener { requestCurrentLocation() }
        btnConfirm.setOnClickListener    { confirmLocation() }
    }

    // ── MAP READY ────────────────────────────────────────────────────────
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled     = true
        map.uiSettings.isMyLocationButtonEnabled = false

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 5f))

        map.setOnCameraIdleListener {
            val center = map.cameraPosition.target
            selectedLat = center.latitude
            selectedLng = center.longitude
            reverseGeocode(center)
        }

        if (hasLocationPermission()) {
            enableMapLocation()
            requestCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
        }
    }

    // ── PERMISSION RESULT ────────────────────────────────────────────────
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            enableMapLocation()
            requestCurrentLocation()
        } else {
            Toast.makeText(
                this,
                "Location permission needed to detect your address",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun hasLocationPermission() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    @Suppress("MissingPermission")
    private fun enableMapLocation() {
        if (hasLocationPermission()) map.isMyLocationEnabled = true
    }

    // ── FETCH CURRENT LOCATION ───────────────────────────────────────────
    @Suppress("MissingPermission")
    private fun requestCurrentLocation() {
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST
            )
            return
        }
        progressBar.visibility = View.VISIBLE
        tvAddress.text = "Fetching your location…"

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                progressBar.visibility = View.GONE
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                } else {
                    Toast.makeText(this, "Could not get location. Move map manually.", Toast.LENGTH_LONG).show()
                    tvAddress.text = "Move the map to set your location"
                }
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Location error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // ── REVERSE GEOCODE ──────────────────────────────────────────────────
    private fun reverseGeocode(latLng: LatLng) {
        progressBar.visibility = View.VISIBLE
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            progressBar.visibility = View.GONE
            if (!addresses.isNullOrEmpty()) {
                val addr = addresses[0]
                selectedAddress = buildString {
                    if (!addr.subThoroughfare.isNullOrEmpty()) append("${addr.subThoroughfare}, ")
                    if (!addr.thoroughfare.isNullOrEmpty())    append("${addr.thoroughfare}, ")
                    if (!addr.subLocality.isNullOrEmpty())     append("${addr.subLocality}, ")
                    if (!addr.locality.isNullOrEmpty())        append("${addr.locality}")
                }.trimEnd(',', ' ')
                tvAddress.text = selectedAddress.ifEmpty { "Location selected" }
                tvAddress.setTextColor(android.graphics.Color.parseColor("#1A1A2E"))
            } else {
                tvAddress.text  = "Address not found — pinch to adjust"
                selectedAddress = "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}"
            }
        } catch (e: Exception) {
            progressBar.visibility = View.GONE
            tvAddress.text = "Move the map to set your location"
        }
    }

    // ── CONFIRM & SAVE ───────────────────────────────────────────────────
    private fun confirmLocation() {
        if (selectedAddress.isEmpty()) {
            Toast.makeText(this, "Please move the map to select a location", Toast.LENGTH_SHORT).show()
            return
        }
        val uid = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show(); return
        }

        btnConfirm.isEnabled = false
        btnConfirm.text      = "Saving…"

        val data = hashMapOf(
            "latitude"  to selectedLat,
            "longitude" to selectedLng,
            "address"   to selectedAddress,
            "city"      to extractCity()
        )

        // ── FIX: use set() with merge so it works even if document doesn't exist yet ──
        db.collection("users").document(uid)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(this, "Location saved! 📍", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener { e ->
                btnConfirm.isEnabled = true
                btnConfirm.text      = "Confirm This Location"
                Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun extractCity(): String {
        return try {
            val geocoder  = Geocoder(this, Locale.getDefault())
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(selectedLat, selectedLng, 1)
            addresses?.firstOrNull()?.locality ?: ""
        } catch (e: Exception) { "" }
    }
}