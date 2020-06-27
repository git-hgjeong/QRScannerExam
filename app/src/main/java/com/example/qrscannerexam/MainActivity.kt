package com.example.qrscannerexam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.qrscannerexam.data.AppDatabase
import com.example.qrscannerexam.data.QRData
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_qr.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val reAdapter = SearchAdapter()
        rView.adapter = reAdapter
        rView.layoutManager = LinearLayoutManager(this)

        val txtView : TextView = findViewById(R.id.txtResult)
        viewModel.getAll().observe(this, Observer {
            txtView.text = it.toString()
            reAdapter.setItemData(it)
        })

        button.setOnClickListener {
            IntentIntegrator(this).initiateScan();
        }
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val txtView : TextView = findViewById(R.id.txtResult)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                //txtView.text = "Cancelled"
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.insert(QRData("Cancelled"))
                }
            } else {
                //Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                //txtView.text = result.contents
                val txt : String = result.contents
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.insert(QRData(txt))
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var items: MutableList<SearchData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchViewHolder {
        return SearchViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        items[position].let {
            with(holder){
                ItemData.text = it.fullname
            }
        }
    }

    fun setItemData(qrData:List<QRData>){
        items.clear()
        for((index, value) in qrData.withIndex()){
            val sdata = SearchData(value.data, "")
            items.add(sdata)
        }
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_qr,parent,false)
    ){
        val ItemData = itemView.textView
    }
}

data class SearchData(val fullname:String, val quiz:String)