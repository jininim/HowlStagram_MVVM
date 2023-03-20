package com.mvvm.mvvmhowlstagram.login

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mvvm.mvvmhowlstagram.R

@SuppressLint("StaticFieldLeak")
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var auth  : FirebaseAuth = FirebaseAuth.getInstance()
    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")


    var showInputNumberActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindIdActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    private val context: Context = getApplication<Application>().applicationContext

    private var googleSignInClient : GoogleSignInClient
    init {
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context,gso)
    }

    fun loginWithSignupEmail(){
        auth.createUserWithEmailAndPassword(id.value.toString(),password.value.toString()).addOnCompleteListener{
            if(it.isSuccessful){
                showInputNumberActivity.value = true
            }else{
                //아이디가 있을경우
            }
        }

    }
    fun loginGoogle(view : View){
        var i = googleSignInClient.signInIntent
        (view.context as? LoginActivity)?.googleLoginResult?.launch(i)

    }
    fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                showInputNumberActivity.value = true
            }else{
                //아이디가 있을경우
            }
        }
    }

}