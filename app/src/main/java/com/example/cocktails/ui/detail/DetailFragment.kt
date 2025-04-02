package com.example.cocktails.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cocktails.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val vm: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.id.let {
            vm.getCocktail(it)
            vm.cocktailLiveData.observe(viewLifecycleOwner){
                with(binding){
                    Picasso
                        .get()
                        .load(it.imageUrl.ifEmpty { "boo" })
                        .into(ivDetailsImage)

                    tvDetailsTitle.text = it.title
                    tvDetailsIngredientsContent.text = it.ingredients
                    tvDetailsInstructionsContent.text = it.instructions
                }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}