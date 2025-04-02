package com.example.cocktails.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentEditBinding
import com.example.cocktails.utils.CocktailMerged
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private val vm : EditViewModel by viewModels()
    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.cocktail.let {
            with(binding){
                etEditImageUrl.setText(it.imageUrl)
                etEditIngredients.setText(it.ingredients)
                etEditInstructions.setText(it.instructions)
                etEditName.setText(it.title)

                Picasso
                    .get()
                    .load(it.imageUrl.ifEmpty { "boo" })
                    .into(ivCreateImage)

                btnDetailsDelete.setOnClickListener {_->
                    vm.deleteCocktail(CocktailMerged(
                        null,
                        it.uuid,
                        etEditName.text.toString(),
                        etEditImageUrl.text.toString(),
                        etEditIngredients.text.toString(),
                        etEditInstructions.text.toString()
                    ))
                }

                btnDetailsEdit.setOnClickListener { _->
                    vm.updateCocktail(CocktailMerged(
                        null,
                        it.uuid,
                        etEditName.text.toString(),
                        etEditImageUrl.text.toString(),
                        etEditIngredients.text.toString(),
                        etEditInstructions.text.toString()
                    ))
                }

                etEditImageUrl.setOnFocusChangeListener { _, hasFocus ->
                    if(!hasFocus){
                        val url = binding.etEditImageUrl.text.toString().trim()
                        Picasso
                            .get()
                            .load(url.ifEmpty { "boo" })
                            .into(ivCreateImage)
                    }
                }
            }
        }

        vm.isDeletedOrUpdated.observe(viewLifecycleOwner){
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


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}