package com.example.projectav2

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.util.*

class ListTrackerActivity : AppCompatActivity() {

    var lat : Double? = null
    var longi : Double? = null

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tracker)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        var btn_map: Button = findViewById(R.id.btn_map)
        btn_map.setOnClickListener {
            permChecked()


        }


    }

    private fun newActivity(lat:Double, longi:Double, city:String, country:String) {
        val intent = Intent (this@ListTrackerActivity, MapsActivity::class.java)
        intent.putExtra("lat",lat)
        intent.putExtra("longi",longi)
        intent.putExtra("city",city)
        intent.putExtra("country", country)
        Toast.makeText(this, "${lat} ${longi}", Toast.LENGTH_LONG).show()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    private fun getCity(lat:Double, longi:Double):String{
        var city = ""

        var geoCod = Geocoder(this, Locale.getDefault())
        var adres = geoCod.getFromLocation(lat,longi,1)
        city = adres.get(0).locality
        return city
    }
    private fun getCountry(lat:Double, longi:Double):String{
        var country = ""

        var geoCod = Geocoder(this, Locale.getDefault())
        var adress = geoCod.getFromLocation(lat,longi,1)
        country = adress.get(0).countryName
        //var teste = adress.get(0).postalCode
        return country
    }

    private fun permChecked() {

        val task:Task<Location> = this.fusedLocationProviderClient.lastLocation

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
            return
        }

        task.addOnSuccessListener {
            if (it != null){
                //Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_LONG).show()
                lat = it.latitude
                longi = it.longitude
                var city = getCity(it.latitude,it.longitude)
                var country = getCountry(it.latitude,it.longitude)
                //Toast.makeText(this,"${city} ${country}",Toast.LENGTH_LONG).show()
                newActivity(it.latitude,it.longitude,city,country)
            }
        }
    }
}