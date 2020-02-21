package com.example.lastseen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.generateViewId
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.text.SimpleDateFormat
import java.util.*

class Itinerary : AppCompatActivity() {

    private var itineraryItemsArrayListView : ArrayList<ItineraryItemView> = ArrayList()

    private lateinit var itineraryList : ListView
    private lateinit var arrayAdapter : ArrayAdapter<ItineraryItemView>
    private lateinit var userIdentifier : String

    private lateinit var submitButton : Button
    private lateinit var trail : Spinner
    private lateinit var calendarView : CalendarView
    private lateinit var hour : Spinner
    private lateinit var minute : Spinner
    private lateinit var ampm : Spinner
    private lateinit var calendar : Calendar

    private lateinit var queue : RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itinerary)

        initializeViewsAdaptersVariables()
        initializeButtonAndCalendar()

        updateItineraryList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.update_information) {
            val intent = Intent(this, UpdateInformation::class.java)
            intent.putExtra("userIdentifier", userIdentifier)

            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initializeViewsAdaptersVariables() {
        userIdentifier = intent.extras?.get("userIdentifier") as String
        itineraryList = findViewById(R.id.itinerary_list)
        arrayAdapter = ArrayAdapter(this, R.layout.itinerary_item, itineraryItemsArrayListView)
        itineraryList.adapter = arrayAdapter
    }

    private fun initializeButtonAndCalendar() {
        submitButton = findViewById(R.id.itinerary_submit_button)
        trail = findViewById(R.id.trail_dropdown)
        calendarView = findViewById(R.id.itinerary_calendar)
        hour = findViewById(R.id.hour_dropdown)
        minute = findViewById(R.id.minute_dropdown)
        ampm = findViewById(R.id.am_pm_dropdown)
        calendar = GregorianCalendar.getInstance()

        calendarView.setOnDateChangeListener {_, year, month, day -> calendar.set(year, month, day)}

        submitButton.setOnClickListener {
            sendItineraryItemToServer()
        }
    }

    private fun sendItineraryItemToServer() {
        val jsonItineraryItem = generateJsonItineraryItemPayload()
        val urlString = getString(R.string.server_url) + getString(R.string.server_itinerary)

        val addItineraryItemRequest = object : JsonObjectRequest(Method.POST, urlString, jsonItineraryItem,
            Response.Listener {
                updateItineraryList()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "UNABLE TO ADD ITEM", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject> {
                try {
                    val json = String(response!!.data, charset("UTF-8"))

                    if (json.isEmpty()) {
                        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response))
                    } else {
                        return super.parseNetworkResponse(response)
                    }
                } catch (e : UnsupportedEncodingException) {
                    return Response.error(ParseError(e))
                }
            }
        }

        queue.add(addItineraryItemRequest)
    }

    private fun generateJsonItineraryItemPayload() : JSONObject {
        val itineraryItemMap = HashMap<String, Any?>()

        itineraryItemMap.put("userIdentifier", userIdentifier)
        itineraryItemMap.put("trail", trail.selectedItem.toString())
        itineraryItemMap.put("date", SimpleDateFormat("MMM dd YYYY").format(calendar.time))
        itineraryItemMap.put("hour", hour.selectedItem.toString())
        itineraryItemMap.put("minute", minute.selectedItem.toString())
        itineraryItemMap.put("ampm", ampm.selectedItem.toString())

        return JSONObject(itineraryItemMap)
    }

    private fun updateItineraryList() {
        queue = Volley.newRequestQueue(this)
        val urlString = getString(R.string.server_url) + getString(R.string.server_itinerary) + userIdentifier

        val getItineraryItemsRequest = JsonArrayRequest(
            Request.Method.GET, urlString, null,
            Response.Listener { response ->

                itineraryItemsArrayListView.clear()

                for (i in 0 until response.length()) {
                    val itineraryItemJson = response.getJSONObject(i)
                    val itineraryItem = ItineraryItemView(this)
                    itineraryItem.id = generateViewId()
                    itineraryItem.text = ((itineraryItemJson.getString("trail"))
                            + ", " + (itineraryItemJson.getString("date")) + ", "
                            + (itineraryItemJson.getString("hour")) + ":"
                            + (itineraryItemJson.getString("minute")) + " "
                            + (itineraryItemJson.getString("ampm"))
                    )

                    itineraryItemsArrayListView.add(itineraryItem)
                }

                arrayAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "UNABLE TO OBTAIN ITINERARY FROM SERVER", Toast.LENGTH_SHORT).show()
            })

        queue.add(getItineraryItemsRequest)
    }
}
