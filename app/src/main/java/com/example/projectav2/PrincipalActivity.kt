package com.example.projectav2

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.teste.*

class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



        private var userId:String?=null


    //------->
    private var mDatabaseReference: DatabaseReference? = null
    private var mDataBase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        userId = intent.getStringExtra("idUser")



        //-------adicionando menu-------\\
        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        var img = findViewById<ImageView>(R.id.logo_principal)

        img.setOnClickListener {
            drawer_layout.open()
        }
        nav_view.setNavigationItemSelectedListener(this)

        initialize()
        var currentUserDb = mDatabaseReference!!.child(userId!!)
        currentUserDb.child("Name").get().addOnSuccessListener {
            tv_nomeprincipal.text = it.value.toString()
            tv_emailprincipal1.text = mAuth?.currentUser?.email.toString()
        }
        
        btn_hist.setOnClickListener {
           // histAccess()
        }
        btn_map.setOnClickListener {
            val intent = Intent(this@PrincipalActivity,MapsActivity::class.java)
            startActivity(intent)
        }



    }

    private fun initialize(){
        mDataBase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDataBase?.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()






    }



   override fun onNavigationItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.nav_Account -> {

                val intent = Intent (this@PrincipalActivity, ContaActivity::class.java)
                intent.putExtra("userid",userId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)

                true
            }
            R.id.nav_history -> {
               // Toast.makeText(this,"History",Toast.LENGTH_SHORT).show()
                //histAccess()

                true
            }
            R.id.nav_option ->{
                Toast.makeText(this,"Options",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_about ->{
                Toast.makeText(this,"About",Toast.LENGTH_SHORT).show()

                true
            }
            R.id.nav_exit ->{
                finish()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun histAccess(){
        val intent = Intent (this@PrincipalActivity, ListTrackerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}


