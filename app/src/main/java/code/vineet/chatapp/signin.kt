package code.vineet.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*

class signin : AppCompatActivity() {
    var mAuth:FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        mAuth = FirebaseAuth.getInstance()


        // on login button clicked
        btLogin.setOnClickListener {
            var mail = etSigninEmail.text.toString()
            val pass = etSigninPassword.text.toString()
            LogintoFirebase(mail,pass)



        }

        tvcreateac.setOnClickListener {
            var intent = Intent(this, signup::class.java)
            startActivity(intent)
            finish()

        }


    }



    fun LogintoFirebase(email:String,pass:String){
        mAuth!!.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this){ task ->

            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Successfull Login.", Toast.LENGTH_SHORT).show()
                var currentUser = mAuth!!.currentUser
                var i = Intent(this, welcome::class.java)
                i.putExtra("email", currentUser!!.email)
                i.putExtra("uid", currentUser.uid)
                startActivity(i)
                finish()

            }else{
                Toast.makeText(applicationContext,"Failed to Login.", Toast.LENGTH_SHORT).show()
            }




        }



    }


}
