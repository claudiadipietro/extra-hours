package com.example.mishorasextras.activities

import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mishorasextras.R
import com.example.mishorasextras.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.activity_alert_dialog_contrasena.*
import kotlinx.android.synthetic.main.activity_alert_dialog_contrasena.view.*

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG ="MAIN ACTIVITY"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        binding.registro.setOnClickListener{
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        binding.iniciarsesion.setOnClickListener{
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            val email = binding.mainUser
            val password = binding.mainPassword
            if (email.getString().isNullOrBlank()){
                Snackbar.make(view, R.string.campo_nulo, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.getString().isNullOrBlank()){
                Snackbar.make(view, R.string.campo_nulo, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email.getString(), password.getString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            goToHorarios()
                        } else {
                            when(task.exception){
                                is FirebaseAuthInvalidUserException->(
                                        Snackbar.make(view, "Debes registrarte para poder acceder", Snackbar.LENGTH_SHORT).show()
                                        )else->{
                                Snackbar.make(view, "Error al iniciar sesión", Snackbar.LENGTH_SHORT).show()
                                        }
                            }
                            Log.d(TAG,task.exception.toString())
                            Snackbar.make(view, "Error al iniciar sesión", Snackbar.LENGTH_SHORT).show()
                        }
                    }
        }
        binding.olvidaste.setOnClickListener{
            val alertDialogView = layoutInflater.inflate(R.layout.activity_alert_dialog_contrasena,null)
            val builder = AlertDialog.Builder(this)
                .setView(alertDialogView)
                .setTitle("Escribe tu email")
            val alertDialog = builder.show()
            alertDialogView.buttonRecuperacion.setOnClickListener{
                alertDialog.dismiss()
                val emailR = alertDialogView.emailRecuperacion.text.toString()
                auth.sendPasswordResetEmail(emailR).addOnCompleteListener(this){ taskResetPassword->
                    if (taskResetPassword.isSuccessful) {
                        Snackbar.make(view, "Te enviamos un correo :)", Snackbar.LENGTH_SHORT).show()
                    }else{
                        Snackbar.make(view, "No estás registradx en nuestra base de datos :(", Snackbar.LENGTH_SHORT).show()
                    }

                }

            }
            alertDialogView.buttonCancelar.setOnClickListener{
                alertDialog.dismiss()
            }


        }


    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d(TAG,currentUser.email!!)
            Log.d(TAG,currentUser.displayName!!)
            Log.d(TAG,currentUser.uid)
            goToHorarios()
        }
    }
    private fun goToHorarios(){
        val intent = Intent(this, Horarios::class.java)
        startActivity(intent)
        finish()

    }
    fun TextInputEditText.getString(): String{
        return text.toString()
    }


}