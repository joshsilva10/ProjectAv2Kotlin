package com.example.projectav2

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_principal_test.*

class PrincipalActivityTest : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_test)


        nav_view.setNavigationItemSelectedListener(this)


        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        var img = findViewById<ImageView>(R.id.logo_principal)
        img.setOnClickListener {
            drawer_layout.open()
        }





    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_Account -> {

                true
            }
            R.id.nav_history -> {
                Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show()

                true
            }
            R.id.nav_option -> {
                true
            }
            R.id.nav_about -> {

                true
            }
            R.id.nav_exit -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}