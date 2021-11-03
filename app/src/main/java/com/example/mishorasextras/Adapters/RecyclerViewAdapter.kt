package com.example.mishorasextras.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mishorasextras.activities.Horarios
import com.example.mishorasextras.databinding.ActivityMainBinding
import com.example.mishorasextras.databinding.ItemHorarioRecyclerViewBinding
import com.example.mishorasextras.models.Horario
import com.google.firebase.firestore.core.View

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    val lista = mutableListOf<Horario>()

    fun RecyclerViewAdapter(lista:List<Horario>){
        this.lista.addAll(lista)
    }


    class ViewHolder(val binding: ItemHorarioRecyclerViewBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun enlazar (horario: Horario){
            binding.itemFechaId.text = horario.fecha
            binding.itemDiaId.text = horario.dia
            binding.itemJornadaId.text = horario.jornada
            binding.itemHoraEntradaId.text = horario.horaEntrada
            binding.itemHoraSalidaId.text = horario.horaSalida
            binding.itemDescansoId.text = horario.descanso
        }
        companion object{
            fun crear(parent: ViewGroup): ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHorarioRecyclerViewBinding.inflate(inflater,parent,false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.crear(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.enlazar(lista[position])
    }

    override fun getItemCount(): Int {
       return lista.size
    }


}