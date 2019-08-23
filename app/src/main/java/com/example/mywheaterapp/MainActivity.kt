package com.example.mywheaterapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywheaterapp.adapter.ForecastAdapter
import com.example.mywheaterapp.injection.component.DaggerOpenWeatherAPIComponent
import com.example.mywheaterapp.injection.module.OpenWeatherAPIModule
import com.example.mywheaterapp.repository.model.ForecastItemViewModel
import com.example.mywheaterapp.repository.model.AllResponse
import com.example.mywheaterapp.util.ErrorTypes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),MainView {
    val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDI()
        setContentView(R.layout.activity_main)
        initializeForecastList()

        getYerevanForecast()
    }

    override fun showSpinner() {
        forecastList.visibility = View.GONE
       // emptyStateText.visibility = View.GONE
        loadingSpinner.visibility = View.VISIBLE
    }

    override fun hideSpinner() {

        forecastList.visibility = View.VISIBLE
        loadingSpinner.visibility = View.GONE
    }

    override fun setYerevanForecast(response: AllResponse) {
        emptyStateText.text = " City: ${response.name}, Temprature: ${response.main.temp} , Pressure: ${response.cod} " +
                ", Humidity: ${response.main.humidity}"

    }

    override fun showToast(toast: String) {
        toast(toast)
    }

    override fun updateForecast(forecasts: List<ForecastItemViewModel>) {

    }

    override fun showErrorToast(errorType: ErrorTypes) {
        when (errorType) {
            ErrorTypes.CALL_ERROR -> toast("connection error")
            ErrorTypes.MISSING_API_KEY -> toast("API KEY is missing")
            ErrorTypes.NO_RESULT_FOUND -> toast("City not found")
        }
        loadingSpinner.visibility = View.GONE
        emptyStateText.visibility = View.VISIBLE
    }



    private fun injectDI() {
        DaggerOpenWeatherAPIComponent
            .builder()
            .openWeatherAPIModule(OpenWeatherAPIModule())
            .build()
            .inject(presenter)

    }

    private fun initializeForecastList() {
        forecastList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ForecastAdapter()
        }

    }
    //Other methods
    private fun getForecast(query: String) = presenter.getForecastForSevenDays(query)
    private fun getYerevanForecast() = presenter.getYerevanForecast()

    inline fun <reified T> Any.safeCast() = this as? T

    fun Activity.toast(toastMessage: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, toastMessage, duration).show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val menuItem = menu?.findItem(R.id.search_button)
        val searchMenuItem = menuItem?.actionView

        if (searchMenuItem is SearchView) {
            searchMenuItem.queryHint = "search"
            searchMenuItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    //This method will be created in next steps
                    getForecast(query)
                    menuItem.collapseActionView()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        return true
    }
}
