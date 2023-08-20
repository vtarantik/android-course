package cz.tarantik.android_course.scanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cz.tarantik.android_course.R
import cz.tarantik.android_course.databinding.ActivityScannerBinding

class ScannerActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, ScannerActivity::class.java)
    }

    private lateinit var binding: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DWUtilities.CreateDWProfile(this)
        binding.btnScan.setOnClickListener {
            //  Button pressed, start scan
            val dwIntent = Intent()
            dwIntent.action = "com.symbol.datawedge.api.ACTION"
            dwIntent.putExtra("com.symbol.datawedge.api.SOFT_SCAN_TRIGGER", "START_SCANNING")
            sendBroadcast(dwIntent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("TAG", "Intent received")
        displayScanResult(intent)
    }

    override fun onResume() {
        super.onResume()
        Log.d("ASD", "onResume")
    }

    private fun displayScanResult(scanIntent: Intent) {
        val decodedSource =
            scanIntent.getStringExtra(resources.getString(R.string.datawedge_intent_key_source))
        val decodedData =
            scanIntent.getStringExtra(resources.getString(R.string.datawedge_intent_key_data))
        val decodedLabelType =
            scanIntent.getStringExtra(resources.getString(R.string.datawedge_intent_key_label_type))
        val scan = "$decodedData [$decodedLabelType]"
        binding.tvOutput.text = scan

        //  Button released, end scan
        val dwIntent = Intent()
        dwIntent.action = "com.symbol.datawedge.api.ACTION"
        dwIntent.putExtra("com.symbol.datawedge.api.SOFT_SCAN_TRIGGER", "STOP_SCANNING")
        sendBroadcast(dwIntent)
    }
}