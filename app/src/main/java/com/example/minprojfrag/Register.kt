package com.example.minprojfrag

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Register.newInstance] factory method to
 * create an instance of this fragment.
 */
class Register : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth

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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Firebase.firestore
        val auth=Firebase.auth

        val navcon = findNavController()
        val emai:TextInputEditText=view.findViewById(R.id.remail)
        val passwor:TextInputEditText=view.findViewById(R.id.rpass)
        val signup: Button = view.findViewById(R.id.button5)
        signup.setOnClickListener {
            val email = emai.text.toString().trim()
            val password = passwor.text.toString().trim()
            val hash = hashMapOf(
                "email" to "$email"
            )
            if ((email.isNotEmpty() and password.isNotEmpty()) and Patterns.EMAIL_ADDRESS.matcher(email).matches())  {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        val user: FirebaseUser? = Firebase.auth.currentUser
                        if (task.isSuccessful) {
                            val uid = user?.uid
                            db.collection("$uid").document("Events").set(hash).addOnCompleteListener {
                                user?.sendEmailVerification()?.addOnCompleteListener {
                                    Firebase.auth.signOut()
                                    navcon.popBackStack()
                                    Toast.makeText(
                                        context, "verify ur email and login ",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                Log.d("TAG", "user created With Email:success")
                            }
                        } else{
                            Toast.makeText(
                                context, "${task.exception}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }


                    }
            }
            else {
                snackbar("please check your email and password")
            }

        }
    }


    fun snackbar(string:CharSequence) {
        Snackbar.make(requireContext(),requireView(),string,Snackbar.LENGTH_SHORT).show()
    }
}