package com.example.mishorasextras.models

import java.util.*

data class Registros(val entradas: Date, val salidas: Date?, val userID : String){
   constructor():this(Date(), Date(), String())
}


