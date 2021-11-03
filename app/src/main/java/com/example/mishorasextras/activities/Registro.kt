package com.example.mishorasextras.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mishorasextras.R
import com.example.mishorasextras.databinding.ActivityMain2Binding
import com.example.mishorasextras.models.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val TAG = "REGISTRO_ACTIVITY"
    private lateinit var auth:FirebaseAuth
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        FirebaseApp.initializeApp(this)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        binding.Registrarse.setOnClickListener{
            val nombre = binding.mainRegistroNombre
            val apellido = binding.mainRegistroApellido
            val email = binding.mainRegistroUsuario
            val dni = binding.mainRegistroDNI
            val password = binding.mainRegistroContrasena
            val password2 = binding.mainRepiteContrasena

            if (nombre.getString().isNullOrBlank()){
                Snackbar.make(view,"Revisa los campos", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            if (apellido.getString().isNullOrBlank()){
                Snackbar.make(view,"Revisa los campos", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if (dni.getString().isNullOrBlank()){
                Snackbar.make(view,"Revisa los campos", Snackbar.LENGTH_SHORT).show()

                if (dni.getString() != ("00000000F")){

                }else{
                    Snackbar.make(view,"El DNI es incorrecto", Snackbar.LENGTH_SHORT).show()
                }
                return@setOnClickListener

            }
            if (password.getString().isNullOrBlank()){
                Snackbar.make(view,"Revisa los campos", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if (password2.getString().isNullOrBlank()){
                Snackbar.make(view,"Revisa los campos", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if (password.getString()!= password2.getString()){
                Snackbar.make(view,"Las contraseñas deben ser iguales", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email.getString(), password.getString())
                    .addOnCompleteListener(this) {
                        task ->
                        if (task.isSuccessful){
                            auth.currentUser.apply {
                                val profile = UserProfileChangeRequest.Builder().setDisplayName(
                                        binding.mainRegistroNombre.getString()
                                ).build()
                                updateProfile(profile).addOnCompleteListener{ taskChangeProfile->
                                    if (taskChangeProfile.isSuccessful){
                                     db.collection("USUARIOS")
                                             .document(auth.currentUser.uid)
                                             .set(
                                                 Usuario(
                                                         binding.mainRegistroNombre.getString(),
                                                         binding.mainRegistroApellido.getString(),
                                                         binding.mainRegistroDNI.getString(),
                                                         binding.mainRegistroUsuario.getString(),
                                                         Date(System.currentTimeMillis()),
                                                         Date(System.currentTimeMillis())
                                                         )
                                             )
                                             .addOnCompleteListener{ taskNewUser->
                                                 if (taskNewUser.isSuccessful){
                                                     finish()
                                                 }else{
                                                     Snackbar.make(view,R.string.error_registro,Snackbar.LENGTH_SHORT).show()
                                                 }
                                             }

                                    }
                                    else{
                                        Snackbar.make(view,"No se ha podido actualizar el perfil",Snackbar.LENGTH_SHORT).show()

                                    }


                                }
                            }
                        }else{
                            when (task.exception){
                                is FirebaseAuthWeakPasswordException ->{
                                    Snackbar.make(view,R.string.password_weak,Snackbar.LENGTH_SHORT).show()
                                }
                                else ->{
                                    Snackbar.make(view,R.string.error_registro,Snackbar.LENGTH_SHORT).show()
                                }
                            }

                        }
                    }

        }

    }

    fun TextInputEditText.getString(): String{
        return text.toString()
    }

}