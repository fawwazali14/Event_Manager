package com.example.minprojfrag

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

data class User(
    val t1:String="",
    val t2:String="",
    val t3:String=""
)

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

/**
 * A simple [Fragment] subclass.
 * Use the [ViewEvents.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewEvents : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        val db = Firebase.firestore
        val uid = Firebase.auth.currentUser?.uid
        val query = db.collection("$uid")
        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query , User::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<User, UserViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                val view =    LayoutInflater.from(parent.context)
                    .inflate(R.layout.card ,parent,false)
                return UserViewHolder(view)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                val a : TextView = holder.itemView.findViewById(R.id.textView3)
                val b : TextView = holder.itemView.findViewById(R.id.textView4)
                val c : TextView = holder.itemView.findViewById(R.id.textView5)
                val btn: Button = holder.itemView.findViewById(R.id.weatherbtn)
                a.text= model.t1
                b.text= model.t2
                c.text= model.t3

                btn.setOnClickListener {
                    Navigation.findNavController(view).navigate(R.id.action_viewEvents_to_climate)
                }
            }

        }
        val x : RecyclerView = view.findViewById(R.id.rview1)
        x.adapter=adapter
        x.layoutManager = LinearLayoutManager(context)



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewEvents.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewEvents().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}