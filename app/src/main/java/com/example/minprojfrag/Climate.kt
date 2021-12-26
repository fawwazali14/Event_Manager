package com.example.minprojfrag

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [Climate.newInstance] factory method to
 * create an instance of this fragment.
 */
class Climate : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val CITY: String = "hyderabad,in"
    val API: String = "3db2810c89f9fcabb232ad73deb4046e"
    val a = view?.findViewById<ProgressBar>(R.id.loader)
    val b = view?.findViewById<RelativeLayout>(R.id.mainContainer)
    val c = view?.findViewById<TextView>(R.id.errorText)

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
        return inflater.inflate(R.layout.fragment_climate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherTask().execute()
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */

            /* Showing the ProgressBar, Making the main design GONE */

            if (a != null) {
                a.visibility = View.VISIBLE
            }
            if (b != null) {
                b.visibility= View.GONE
            }
            if (c != null) {
                c.visibility= View.GONE
            }

        }
        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        try{
            val jsonObj = JSONObject(result)
            val main = jsonObj.getJSONObject("main")
            val sys = jsonObj.getJSONObject("sys")
            val wind = jsonObj.getJSONObject("wind")
            val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
            val updatedAt: Long = jsonObj.getLong("dt")
            val updatedAtText =
                "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", java.util.Locale.ENGLISH).format(
                    Date(updatedAt * 1000)
                )
            val temp = main.getString("temp") + "°C"
            val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
            val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
            val pressure = main.getString("pressure")
            val humidity = main.getString("humidity")
            val sunrise: Long = sys.getLong("sunrise")
            val sunset: Long = sys.getLong("sunset")
            val windSpeed = wind.getString("speed")
            val weatherDescription = weather.getString("description")
            val address = jsonObj.getString("name") + ", " + sys.getString("country")
            /* Populating extracted data into our views */
            val d = view?.findViewById<TextView>(R.id.address)
            val e = view?.findViewById<TextView>(R.id.updated_at)
            val f = view?.findViewById<TextView>(R.id.status)
            val g = view?.findViewById<TextView>(R.id.temp)
            val h = view?.findViewById<TextView>(R.id.temp_min)
            val i = view?.findViewById<TextView>(R.id.temp_max)
            val j = view?.findViewById<TextView>(R.id.sunrise)
            val k = view?.findViewById<TextView>(R.id.sunset)
            val l = view?.findViewById<TextView>(R.id.wind)
            val m = view?.findViewById<TextView>(R.id.pressure)
            val n = view?.findViewById<TextView>(R.id.humidity)
            if (d != null) {
                d.text = address
            }
            if (e != null) {
                e.text = updatedAtText
            }
            if (f != null) {
                f.text = weatherDescription
            }
            if (g != null) {
                g.text = temp
            }
            if (h != null) {
                h.text = tempMin
            }
            if (i != null) {
                i.text = tempMax
            }
            if (j != null) {
                j.text =
                    SimpleDateFormat("hh:mm a", java.util.Locale.ENGLISH).format(Date(sunrise * 1000))
            }
            if (k != null) {
                k.text =
                    SimpleDateFormat("hh:mm a", java.util.Locale.ENGLISH).format(Date(sunset * 1000))
            }
            if (l != null) {
                l.text = windSpeed
            }
            if (m != null) {
                m.text = pressure
            }
            if (n != null) {
                n.text = humidity
            }
            if (a != null) {
                a.visibility = View.GONE
            }
            if (b != null) {
                b.visibility = View.VISIBLE
            }
        }catch (e:Exception) {
            if (a != null) {
                a.visibility = View.GONE
            }
            if (c != null) {
                c.visibility = View.VISIBLE
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
         * @return A new instance of fragment Climate.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Climate().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}