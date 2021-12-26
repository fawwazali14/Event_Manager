package com.example.minprojfrag

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import android.content.pm.PackageManager as PackageManager1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetEvents.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetEvents : Fragment() {
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
        return inflater.inflate(R.layout.fragment_set_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val b: Button = view.findViewById(R.id.button)
        val t1 = view.findViewById<TextInputLayout>(R.id.textview1).editText?.text
        val t2 = view.findViewById<TextInputLayout>(R.id.textview2).editText?.text
        val t3 = view.findViewById<TextInputLayout>(R.id.textview3).editText?.text

        val db = Firebase.firestore
        val uid = Firebase.auth.currentUser?.uid
        b.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT)
            intent.setData(CalendarContract.Events.CONTENT_URI)
            intent.putExtra(CalendarContract.Events.TITLE,t1.toString())
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, t2.toString())
            intent.putExtra(CalendarContract.Events.DESCRIPTION, t3.toString())
            intent.putExtra(CalendarContract.Events.ALL_DAY,true)
            if ("$t1" != "") {
                val n = hashMapOf(
                    "t1" to "$t1",
                    "t2" to "$t2",
                    "t3" to "$t3"
                )


                val d = db.collection("$uid").document("Events")

                lifecycleScope.launch {

                    d.set(n)
                        .addOnSuccessListener { documentReference ->
                            Log.d("TAG name", "DocumentSnapshot added successfully")

                            if(activity?.let { it1 -> intent.resolveActivity(it1.packageManager) } != null){

                                startActivity(intent)
                            }



                        }
                }

            }




        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetEvents.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetEvents().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}