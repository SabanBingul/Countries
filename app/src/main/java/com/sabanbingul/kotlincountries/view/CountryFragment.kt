package com.sabanbingul.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sabanbingul.kotlincountries.R
import com.sabanbingul.kotlincountries.util.downloadFromUrl
import com.sabanbingul.kotlincountries.util.placeholderProgressBar
import com.sabanbingul.kotlincountries.viewmodel.CountryViewModel
import com.sabanbingul.kotlincountries.viewmodel.FeedViewModel


class CountryFragment : Fragment() {

    private lateinit var viewModel: CountryViewModel

    private lateinit var countryName : TextView
    private lateinit var countryRegion : TextView
    private lateinit var countryCapital : TextView
    private lateinit var countryCurrency : TextView
    private lateinit var countryLanguage : TextView
    private lateinit var countryImage : ImageView


    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        countryName = view.findViewById(R.id.countryName)
        countryRegion = view.findViewById(R.id.countryRegion)
        countryCapital = view.findViewById(R.id.countryCapital)
        countryCurrency = view.findViewById(R.id.countryCurrency)
        countryLanguage = view.findViewById(R.id.countryLanguage)
        countryImage = view.findViewById(R.id.countryImage)



        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)



        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                countryName.text = country.countryName
                countryCapital.text = country.countryCapital
                countryRegion.text = country.countryRegion
                countryCurrency.text = country.countryCurrency
                countryLanguage.text = country.countryLanguage
                context?.let {
                    countryImage.downloadFromUrl(country.imageUrl!!, placeholderProgressBar(it))

                }
            }
        })
    }

}