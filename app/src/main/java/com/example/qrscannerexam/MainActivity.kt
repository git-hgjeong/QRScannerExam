package com.example.qrscannerexam

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.qrscannerexam.data.AppDatabase
import com.example.qrscannerexam.data.QRData
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtView : TextView = findViewById(R.id.txtResult)
        viewModel.getAll().observe(this, Observer {
            txtView.text = it.toString()
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