


package com.example.mishorasextras.models

import java.sql.Timestamp
import java.util.*

data class Horario constructor(val fecha:String, val jornada:String, val descanso:String, val horaEntrada:String,
                   val horaSalida:String, val dia: String){


  constructor() : this("", "", "", "", "", "" )

    override fun equals (other: Any?): Boolean{
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Horario
        if (fecha != other.fecha)  return false
        return true
    }

    override fun hashCode(): Int {
        return fecha.hashCode()
    }

}
