package com.example.mishorasextras


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mishorasextras.Adapters.RecyclerViewAdapter
import com.example.mishorasextras.databinding.FragmentDiasBinding
import com.example.mishorasextras.models.Horario
import com.example.mishorasextras.viewModel.horariosViewModel


class Dias : Fragment() {

    private lateinit var binding: FragmentDiasBinding
    private val model: horariosViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding =  FragmentDiasBinding.inflate(inflater, container, false)
        val view = binding.root

        model.getHorarios().observe(viewLifecycleOwner,{
            createRecyclerView(it)
        })
        return view
    }


    private fun createRecyclerView(horarios: List<Horario>){
        val mAdapter = RecyclerViewAdapter()
        mAdapter.RecyclerViewAdapter(horarios)
        val rv = binding.horariosRecyclerView
        rv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }


    }




}