package com.example.mishorasextras.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mishorasextras.R
import com.example.mishorasextras.databinding.ActivityMain3Binding
import com.example.mishorasextras.models.Registros
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main3.*
import java.util.*

class Horarios : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var user: String
    private lateinit var auth: FirebaseAuth
    private val TAG = "HORARIOS_ACTIVITY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


        binding = ActivityMain3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser.uid
        val db = Firebase.firestore
        

        binding.Entrada.setOnClickListener{


            Salida.setEnabled(true)
            Entrada.setEnabled(false)


                db.collection("REGISTROS")
                        .orderBy("entradas", Query.Direction.DESCENDING).get().addOnSuccessListener {
                            for (document in it){
                                val horario = document.toObject<Registros>()
                                Log.d(TAG, horario.toString())

                                if (horario.userID == user && horario.salidas == null){
                                    Snackbar.make(view,getString(R.string.error_entrada_salida_previa),Snackbar.LENGTH_SHORT).show()
                                    return@addOnSuccessListener
                                }
                            }
                            db.collection("REGISTROS").add(Registros(Date(System.currentTimeMillis()), null, user))
                                .addOnCompleteListener{ taskNewRegistroEntrada->
                                    if (taskNewRegistroEntrada.isSuccessful){
                                        Snackbar.make(view,R.string.entrada_succesful,Snackbar.LENGTH_SHORT).show()
                                    }else{
                                        Snackbar.make(view,R.string.error_horas,Snackbar.LENGTH_SHORT).show()
                                    }

                                }
                            }

            }

        binding.Salida.setOnClickListener{


            Entrada.setEnabled(true)
            Salida.setEnabled(false)

            db.collection("REGISTROS").orderBy("entradas", Query.Direction.DESCENDING).get().addOnSuccessListener {
                for (document in it){
                    val horario = document.toObject<Registros>()
                    Log.d(TAG, horario.toString())

                    if (horario.salidas == null && horario.userID == user){
                        db.collection("REGISTROS").document(document.id).update("salidas", Date(System.currentTimeMillis()))
                                .addOnCompleteListener{ taskNewRegistroSalida ->
                                    if (taskNewRegistroSalida.isSuccessful){
                                        Snackbar.make(view,R.string.salida_succesful,Snackbar.LENGTH_SHORT).show()
                                    }else{
                                        Snackbar.make(view,R.string.error_horas,Snackbar.LENGTH_SHORT).show()
                                    }

                                }
                    }
                }

            }


        }

        binding.calcularHoras.setOnClickListener{
            val intent = Intent(this, Calendario::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener{
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()

        }


    }

    }





