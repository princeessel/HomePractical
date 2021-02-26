package com.otis.homepractical.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.otis.homepractical.MainActivity
import com.otis.homepractical.R
import com.otis.homepractical.models.Pro
import com.otis.homepractical.ui.details.DetailsFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var listAdapter: ProListAdapter

    private lateinit var proData: Array<Pro>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.proDataLiveData.observe(this) {
            Log.d("TAG", it.toString())
            proData = it
            listAdapter.proArray = it
        }

        listAdapter = ProListAdapter {
            val arguments = Bundle ()
            arguments.putParcelable(Pro.TAG, proData[it])

            val detailsFragment = DetailsFragment.newInstance().apply {
                setArguments(arguments)
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, detailsFragment)
                .addToBackStack(DetailsFragment.TAG)
                .commit()
        }

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        viewModel.loadProData(requireContext())
    }
}

   class ProListAdapter(val clickListener: (Int) -> Unit) : RecyclerView.Adapter<ProListAdapter.ViewHolder>() {

    var proArray = emptyArray<Pro>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_pro_list_recyclerview, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(proArray[position])

        viewHolder.itemView.setOnClickListener {
            clickListener (position)
        }
    }

    override fun getItemCount() = proArray.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTV: TextView = view.findViewById(R.id.nameTV)
        var ratingsTV: TextView = view.findViewById(R.id.ratingsTV)

        fun bind (pro: Pro) {
            nameTV.text = pro.companyName

            when {
                pro.compositeRating.toFloat() >= 4f -> {
                    ratingsTV.setTextColor(ContextCompat.getColor(itemView.context, R.color.ratings_high))
                }
                pro.compositeRating.toFloat() >= 3f -> {
                    ratingsTV.setTextColor(ContextCompat.getColor(itemView.context, R.color.ratings_mid))
                }
                else -> {
                    ratingsTV.setTextColor(ContextCompat.getColor(itemView.context, R.color.ratings_low))
                }
            }

            ratingsTV.text = when {
                pro.ratingCount.toInt() > 0 -> {
                    "Ratings: ${pro.compositeRating} | ${pro.ratingCount} ratings (s)"
                }
                else -> {
                    ratingsTV.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                    "References Available"
                }
            }
        }
    }
}