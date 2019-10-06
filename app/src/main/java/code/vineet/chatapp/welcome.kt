package code.vineet.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.signup.*
import kotlinx.android.synthetic.main.activity_welcome.*

class welcome : AppCompatActivity() {
        var mAuth:FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        mAuth = FirebaseAuth.getInstance()
        var myEmail:String?=null

        var b: Bundle? = intent.extras
        myEmail = b!!.getString("email")


        tvWelcome.text = "Signed in as $myEmail \n\n" +
                "Login Successful successfull"


        btLogout.setOnClickListener{
            mAuth!!.signOut()



            }


        }

    }









