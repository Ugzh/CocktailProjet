package com.example.cocktails.ui.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails.R
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.databinding.FragmentCreateBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID


@AndroidEntryPoint
class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val vm : CreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.isAddedLiveData.observe(viewLifecycleOwner){
            if(it)
                findNavController().popBackStack()
        }

        vm.userMessageLiveData.observe(viewLifecycleOwner){
            if(it != 0)
                Toast
                    .makeText(
                        view.context,
                        view.context.getString(it),
                        Toast.LENGTH_SHORT
                    )
                    .show()
        }

        with(binding){
            btnCreateAdd.setOnClickListener {
                vm.addCocktail(
                    Cocktail(
                        UUID.randomUUID(),
                        etCreateName.text.toString(),
                        etCreateImageurl.text.toString(),
                        etCreateInstructions.text.toString(),
                        etCreateIngredients.text.toString()
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}