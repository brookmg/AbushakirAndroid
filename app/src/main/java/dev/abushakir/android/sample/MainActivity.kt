package dev.abushakir.android.sample

import android.icu.util.EthiopicCalendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import dev.abushakir.android.ETDateTime
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ecTextView = findViewById<TextView>(R.id.ec_date)
        val gcTextView = findViewById<TextView>(R.id.gc_date)

        val timeFlow = flow {
            while (true) {
                delay(1000)
                emit(Date() to ETDateTime.now())
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            timeFlow.collect {
                withContext(Dispatchers.Main) {
                    ecTextView.text = "EC: ${it.second}"
                    gcTextView.text = "GC: ${it.first.toGMTString()}"
                }
            }
        }
    }
}