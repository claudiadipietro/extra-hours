package com.example.mishorasextras.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mishorasextras.models.Horario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.toObjects as toObjects

class horariosViewModel: ViewModel() {
    val db = Firebase.firestore
    private val _horarios:MutableLiveData<List<Horario>> by lazy{
        MutableLiveData<List<Horario>>().also {
            loadHorarios()
        }
    }
    fun getHorarios(): LiveData<List<Horario>>{
        return _horarios
    }

    private fun loadHorarios(){
        db.collection("HORARIOS").get().addOnSuccessListener { documentos ->
            val lista = documentos.toObjects<Horario>()
            _horarios.value = lista
        }
    }
}