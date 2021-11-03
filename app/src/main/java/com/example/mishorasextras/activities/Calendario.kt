package com.example.mishorasextras.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mishorasextras.R
import com.example.mishorasextras.databinding.ActivityMain4Binding
import com.example.mishorasextras.models.Registros
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main4.*
import java.text.SimpleDateFormat
import java.util.*

class Calendario : AppCompatActivity() {
    private lateinit var binding: ActivityMain4Binding
    private lateinit var user: String
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        binding = ActivityMain4Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser.uid
        val db = Firebase.firestore
        var horasExtra: Long

        val calendar = Calendar.getInstance()

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            calendarView.date = calendar.timeInMillis



            db.collection("REGISTROS")
                    .orderBy("entradas", Query.Direction.DESCENDING).get().addOnSuccessListener {

                        for (document in it) {
                            val horario = document.toObject<Registros>()
                            val dateFormat = SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.getDefault())
                            val fechaEntrada = dateFormat.parse(horario.entradas.toString())
                            val fechaSalida = dateFormat.parse(horario.salidas!!.toString())
                            val strDate: Date = dateFormat.parse(calendar.time.toString())


                            var difference: Long = Math.abs(fechaSalida.getTime() - fechaEntrada.getTime())

                            difference = difference / (60 * 60 * 1000)

                            horasExtra = difference - 8

                            if (strDate == fechaEntrada) {
                                textviewHoras.text = "El número de horas extra es de "
                                textviewHoras.append(horasExtra.toString())
                            } else {
                                textviewHoras.text = "No hay datos"
                            }


                        }


                    }


        }


    }


}

