package com.example.projectav2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_conta.*

class ContaActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private var userId:String? =null

    private var TAG = "ContaActivity"
    private var nome : String? = null
    private var email : String? = null
    private var cep : String? = null
    private var telefone : String? = null
    private var endereco : String? = null
    private var numeroRes : String? = null
    private var complemento : String? = null

    //---------

    private var mDatabaseReference: DatabaseReference? = null
    private var mDataBase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    var storage : FirebaseStorage? = null
    var storageReference:StorageReference? = null

    private var imaget:Uri? =null
    private var imaget1:Bitmap? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta)

        userId = intent.getStringExtra("userid")



        var nameacc : TextView = findViewById(R.id.tv_nomeconta)
        var adress : TextView = findViewById(R.id.tv_ender)
        //var imaes : ImageView = findViewById(R.id.img_conta)

        initialize()

        var currentUserDb = mDatabaseReference!!.child(userId!!)
        //var currentImageDb = storageReference!!.child(userId!!)
        currentUserDb.child("Name").get().addOnSuccessListener {
            nameacc.text = it.value.toString()
        }
        currentUserDb.child("Endereco").get().addOnSuccessListener {
            adress.text = it.value.toString()

        }

        btn_camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,10)
        }




        btn_galery.setOnClickListener {
            /*val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(Intent.createChooser(intent,"Escolha uma Imagem"), 11)*/

            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type= "image/*"
                startActivityForResult(it,11)
            }
        }

        btn_confirmphoto.setOnClickListener {

            //val trans: ByteArrayOutputStream? = null

           // currentImageDb.child("ImgUser").putFile(imaget!!)

        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 11){
                if (data != null){
                    val uri = data.data
                    imaget = uri
                    img_conta.setImageURI(uri)
                }
            }else if (requestCode == 10){
                    if (data != null){
                         val uri = data?.extras?.get("data") as Bitmap
                         imaget1 = uri
                         img_conta.setImageBitmap(uri)
                }

        }

        }




    }

    private fun initialize(){
        mDataBase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDataBase?.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage?.reference!!.child("ImageUser")





    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }


}