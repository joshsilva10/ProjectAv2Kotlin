package com.example.projectav2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_initial2.*

class InitialActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial2)


        btn_app.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


        sobrenos.setOnClickListener {

            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)

        }


    }


}




