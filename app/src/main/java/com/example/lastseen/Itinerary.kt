package com.example.lastseen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import java.util.*

class Itinerary : AppCompatActivity() {

    private var itineraryItemsArrayListView : ArrayList<ItineraryItemView> = ArrayList()

    private lateinit var itineraryList : ListView
    private lateinit var arrayAdapter : ArrayAdapter<ItineraryItemView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itinerary)

        itineraryList = findViewById(R.id.itinerary_list)
        arrayAdapter = ArrayAdapter(this, R.layout.itinerary_item, itineraryItemsArrayListView)
        itineraryList.adapter = arrayAdapter

        initializeButtons()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.update_information) {
            val intent = Intent(this, UpdateInformation::class.java)

            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initializeButtons() {
        val submitButton : Button = findViewById(R.id.itinerary_submit_button)
        val trail : Spinner = findViewById(R.id.trail_dropdown)
        val date : CalendarView = findViewById(R.id.itinerary_calendar)
        val hour : Spinner = findViewById(R.id.hour_dropdown)
        val minute : Spinner = findViewById(R.id.minute_dropdown)
        val ampm : Spinner = findViewById(R.id.am_pm_dropdown)


        submitButton.setOnClickListener {
            val itineraryItem = ItineraryItemView(this)
            itineraryItem.text = (trail.selectedItem.toString() + " "
                    + date.date.toString() + " "
                    + hour.selectedItem.toString() + " "
                    + minute.selectedItem.toString() + " "
                    + ampm.selectedItem.toString())

            System.out.println(itineraryItem.text.toString())

            itineraryItemsArrayListView.add(itineraryItem)

            arrayAdapter.notifyDataSetChanged()
        }
    }
}
