package com.example.projectav2

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

public  data class orders(
        val code:String="",
        val desc:String=""
)


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var lat: Double? = null //37.3569
    var longi : Double? = null //-121.9479
    var country : String? = null
    var city : String? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var data = testSend()
    private var codeList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    //val db1= FirebaseFirestore.getInstance()
    //val db = Firebase.firestore
    lateinit var view:MenuView.ItemView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        lat = intent.getDoubleExtra("lat", 0.0)
        longi = intent.getDoubleExtra("longi", 0.0)
       // city = intent.getStringExtra("city")
       // country = intent.getStringExtra("country")
        //Toast.makeText(this, "${lat} ${longi}",Toast.LENGTH_LONG).show()
        /*val query = db.collection("orders")
        val option = FirestoreRecyclerAdapter
        val cls = orders("","")*/






        val rv : RecyclerView = findViewById(R.id.rv_recycler)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = RecyclerAdapter(codeList,descList)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getLoc.setOnClickListener {
            checkPerm()
        }

        Listar()


    }


    fun Listar(){
        val tamanho = data.lastIndex
        for (n:Int in 0..tamanho){


            addToList(data[n].code, data[n].desc)

        }
    }

    fun addToList( tittle: String,  desc: String){

        codeList.add(tittle)
        descList.add(desc)

    }

    private fun checkPerm() {

        val task: Task<Location> = this.fusedLocationProviderClient.lastLocation

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
                val mapM = supportFragmentManager
                        .findFragmentById(R.id.map) as SupportMapFragment
                mapM.getMapAsync(this)
                city = getCity(it.latitude,it.longitude)
                country = getCountry(it.latitude,it.longitude)
                //Toast.makeText(this,"${city} ${country}",Toast.LENGTH_LONG).show()
                //newActivity(it.latitude,it.longitude,city,country)
            }
        }
    }


    //pegando nome da cidade e estado

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


    override fun onResume() {
        super.onResume()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var marc = city+" - "+country
       // var t = orders().code
       // Toast.makeText(this,t,Toast.LENGTH_LONG).show()
        // Add a marker in Sydney and move the camera
        val test = LatLng(lat!!, longi!!)
        //Toast.makeText(this,"${lat}",Toast.LENGTH_LONG).show()
        mMap.addMarker(MarkerOptions().position(test).title(marc))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(test,15f))
    }
}