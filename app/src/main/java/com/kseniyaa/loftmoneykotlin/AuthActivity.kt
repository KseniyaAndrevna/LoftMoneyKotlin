package com.kseniyaa.loftmoneykotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AuthActivity : AppCompatActivity() {

    private var googleSignInClient: GoogleSignInClient? = null
    private var auth: FirebaseAuth? = null
    private var api: Api? = null
    private var userId: String? = null
    private var token: String? = null
    private var sharedPreferences: SharedPreferences? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        api = (application as App).api

        val signInButton = sign_in_button

        val version = version
        version.text = getString(R.string.tv_version_title) + BuildConfig.VERSION_NAME + "\n" + getString(R.string.tv_version_year)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener { signIn() }

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
    }


    private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                userId = account.id
                getToken()
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)

            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth?.signInWithCredential(credential)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        //val user = auth?.currentUser
                        val intent = Intent(this@AuthActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(this@AuthActivity, "Authentication Failed.", Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun getToken() {
        val call = api!!.getAuthToken(userId)
        call.enqueue(object : Callback<LinkedHashMap<String, String>> {
            override fun onResponse(call: Call<LinkedHashMap<String, String>>, response: Response<LinkedHashMap<String, String>>) {
                assert(response.body() != null)
                val authData = response.body()
                token = authData?.get(getString(R.string.auth_token)) as String
                saveToken(token)
            }

            override fun onFailure(call: Call<LinkedHashMap<String, String>>, t: Throwable) {

            }
        })
    }

    fun saveToken(token: String?) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences!!.edit()
        editor.putString(SAVE_TOKEN, token)
        editor.apply()
    }

    companion object {
        private const val RC_SIGN_IN = 99
        private const val TAG = "Error"
        const val SAVE_TOKEN = "token"
    }
}
