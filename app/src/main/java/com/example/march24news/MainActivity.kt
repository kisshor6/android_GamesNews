package com.example.march24news

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), GamesItemClicked {
    private lateinit var madapter : GameListRecycler
    private lateinit var progress : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbarCustom = findViewById<Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbarCustom)

        progress = findViewById(R.id.progress)
        progress.visibility = View.VISIBLE

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        getData()
        madapter = GameListRecycler(this)
        recyclerView.adapter = madapter
    }

    fun getData(){

        val queue = Volley.newRequestQueue(this)
        val url = "https://free-to-play-games-database.p.rapidapi.com/api/games"
        val headers = hashMapOf(
            "X-RapidAPI-Key" to "66a26f2d17msh5aeaa606a9441bep1453d3jsne80a418ec6f0",
            "X-RapidAPI-Host" to "free-to-play-games-database.p.rapidapi.com"
        )
        val jsonArrayRequest = object : JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                val gamesArray = ArrayList<Games>()
                for (i in 0 until response.length()){
                    val gamesJsonObject = response.getJSONObject(i)
                    val games = Games(
                        gamesJsonObject.getString("short_description"),
                        gamesJsonObject.getString("publisher"),
                        gamesJsonObject.getString("game_url"),
                        gamesJsonObject.getString("thumbnail")
                    )
                    gamesArray.add(games)


//                    val description = gamesJsonObject.getString("short_description")
//                    val developer = gamesJsonObject.getString("developer")
                }
                madapter.updateGames(gamesArray)
                progress.visibility = View.GONE
            },
            Response.ErrorListener { error ->
                Log.d("naveen", "$error")
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }
        queue.add(jsonArrayRequest)

    }


    override fun onItemClicked(item: Games) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

}