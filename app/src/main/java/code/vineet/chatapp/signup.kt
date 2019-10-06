package code.vineet.chatapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.signup.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class signup : AppCompatActivity() {

    private var mAuth:FirebaseAuth?=null

    private var mStorage:FirebaseStorage?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        mAuth = FirebaseAuth.getInstance()
        mStorage = FirebaseStorage.getInstance()
        progressBar.visibility = View.INVISIBLE



        btSignup.setOnClickListener {
            var mail = etEmail.text.toString()
            val pass = etPassword.text.toString()

            SignuptoFirebase(mail,pass)

            progressBar.visibility = View.VISIBLE



        }

        btImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)

        }


        tvLogin.setOnClickListener {
            var intent = Intent(this, signin::class.java)
            startActivity(intent)
            finish()

        }



    }
    var imageSelected:Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            imageSelected = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageSelected)

            ivImage.setImageBitmap(bitmap)

            btImage.alpha = 0f

//            val bitmapDrawable = BitmapDrawable(bitmap)
//
//            btImage.setBackgroundDrawable(bitmapDrawable)

        }
    }


    fun SignuptoFirebase(email:String,pass:String){
        mAuth!!.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){ task ->

            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Successfull User Creation.",Toast.LENGTH_SHORT).show()
                var currentUser = mAuth!!.currentUser

                uploadImagetoFb()


            }else{
                Toast.makeText(applicationContext,"Failed to SignUp.",Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }




        }



    }

    private fun uploadImagetoFb(){
        val filename = UUID.randomUUID().toString()
        val ref = mStorage!!.getReference("/images/$filename")

        ref.putFile(imageSelected!!)
            .addOnSuccessListener{
                toast("image uploaded successfully")
                progressBar.visibility = View.INVISIBLE


            }
    }

    override fun onStart(){
        super.onStart()
        LoadMain()
    }


    fun LoadMain(){
        var currentUser = mAuth!!.currentUser

        if(currentUser!=null) {

            //save in database



            var intent = Intent(this, welcome::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)

            startActivity(intent)

        }
    }

    fun toast(toast:String)
    {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()

    }
}
