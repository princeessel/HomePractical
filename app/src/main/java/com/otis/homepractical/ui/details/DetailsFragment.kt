package com.otis.homepractical.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.otis.homepractical.MainActivity
import com.otis.homepractical.R
import com.otis.homepractical.models.Pro

class DetailsFragment : Fragment() {

    companion object {
        val TAG = "DetailsFragment"
        fun newInstance() = DetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        arguments?.let {
            val proData = it.getParcelable<Pro>(Pro.TAG)

            proData?.let {
                requireView().findViewById<TextView>(R.id.nameTV).text = proData.companyName
                requireView().findViewById<TextView>(R.id.specialtyTV).text = proData.specialty
                requireView().findViewById<TextView>(R.id.ratingTV).text = "Ratings: ${proData.compositeRating} | ${proData.ratingCount} ratings (s)"
                requireView().findViewById<TextView>(R.id.locationTV).text = proData.primaryLocation
                requireView().findViewById<TextView>(R.id.servicesTV).text = proData.servicesOffered ?: "Services Not Available"
                requireView().findViewById<TextView>(R.id.overviewTV).text = proData.companyOverview
                requireView().findViewById<Button>(R.id.callButton).setOnClickListener {
                    Log.d("Pro:Phone", proData.phoneNumber)
                    Toast.makeText(requireContext(), proData.phoneNumber, Toast.LENGTH_SHORT).show()
                }
                requireView().findViewById<Button>(R.id.emailButton).setOnClickListener {
                    Log.d("Pro:Email", proData.email)
                    Toast.makeText(requireContext(), proData.email, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}