package cz.tarantik.android_course.scanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import cz.tarantik.android_course.scanner.domain.ScannerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "ScannerManager"
private const val NOTIFICATION_ACTION = "com.symbol.datawedge.api.NOTIFICATION_ACTION"
private const val NOTIFICATION_TYPE_SCANNER_STATUS = "SCANNER_STATUS"

private const val SUSPEND = "suspend"
private const val RESUME = "resume"

class ScannerManager(
    private val appContext: Context,
) {
    private val suspendReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //Get result of the suspend/resume API call
            val action = intent.action
            if (action != null && action == "com.symbol.datawedge.api.RESULT_ACTION") {
                val extras = intent.extras
                if (extras != null) {
                    //user specified ID
                    val cmdID = extras.getString("COMMAND_IDENTIFIER")
                    if (RESUME == cmdID || SUSPEND == cmdID) {
                        //success or failure
                        val result = extras.getString("RESULT")
                        //Original command
                        val command = extras.getString("COMMAND")
                        if ("FAILURE" == result) {
                            val info = extras.getBundle("RESULT_INFO")
                            var errorCode: String? = ""
                            if (info != null) {
                                errorCode = info.getString("RESULT_CODE")
                            }
                            Log.d(
                                TAG,
                                "SuspendReceiver.onReceive.Error: Command:$command:$cmdID:$result,Code:$errorCode"
                            )
                        } else {
                            Log.d(
                                TAG,
                                "SuspendReceiver.onReceive.Success Command:$command:$cmdID:$result"
                            )
                        }
                    }
                }
            }
        }
    }

    private val scannerStatusReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action;
            Log.d(TAG, "#DataWedge-APP# Action: $action");

            if (action.equals(NOTIFICATION_ACTION)) {

                if (intent.hasExtra(NOTIFICATION_ACTION)) {
                    val bundle = intent.getBundleExtra("com.symbol.datawedge.api.NOTIFICATION")
                    val notificationType = bundle?.getString("NOTIFICATION_TYPE")
                    if(notificationType == NOTIFICATION_TYPE_SCANNER_STATUS) {
                        Log.d(TAG, "SCANNER_STATUS: status: " + bundle.getString("STATUS") + ", profileName: " + bundle.getString("PROFILE_NAME"));
                    }
                }
            }
        }
    }

    private val _state = MutableStateFlow(ScannerState())
    val status = _state.asStateFlow()

    init {
        registerReceivers()
    }

    private fun suspendScanner() {
        val i = Intent().apply {
            action = "com.symbol.datawedge.api.ACTION"
            putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "SUSPEND_PLUGIN")
            putExtra("SEND_RESULT", "true")
            putExtra("COMMAND_IDENTIFIER", SUSPEND) //Unique identifier
        }
        appContext.sendBroadcast(i)
    }

    private fun resumeScanner() {
        val i = Intent().apply {
            action = "com.symbol.datawedge.api.ACTION"
            putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "RESUME_PLUGIN")
            putExtra("SEND_RESULT", "true")
            putExtra("COMMAND_IDENTIFIER", RESUME) //Unique identifier
        }
        appContext.sendBroadcast(i)
    }
//
//    private fun enableScanner() {
//        val i = Intent().apply {
//            action = "com.symbol.datawedge.api.ACTION"
//            putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "ENABLE_PLUGIN")
//            putExtra("SEND_RESULT", "true")
//            putExtra("COMMAND_IDENTIFIER", "MY_ENABLE_SCANNER") //Unique identifier
//        }
//        appContext.sendBroadcast(i)
//    }
//
//    private fun disableScanner() {
//        val i = Intent().apply {
//            action = "com.symbol.datawedge.api.ACTION"
//            putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "DISABLE_PLUGIN")
//            putExtra("SEND_RESULT", "true")
//            putExtra("COMMAND_IDENTIFIER", "MY_DISABLE_SCANNER") //Unique identifier
//        }
//        appContext.sendBroadcast(i)
//    }

    private fun registerScannerStatusNotifications() {
        val bundle = Bundle().apply {
            putString("com.symbol.datawedge.api.APPLICATION_NAME", "com.example.intenttest")
            putString("com.symbol.datawedge.api.NOTIFICATION_TYPE", "SCANNER_STATUS")
        }
        val intent = Intent().apply {
            action = "com.symbol.datawedge.api.ACTION"
            putExtra("com.symbol.datawedge.api.REGISTER_FOR_NOTIFICATION", bundle)
        }

        appContext.sendBroadcast(intent)
    }

    private fun registerReceivers() {
        val filter = IntentFilter().apply {
            addAction("com.symbol.datawedge.api.RESULT_ACTION")
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        appContext.registerReceiver(suspendReceiver, filter)

        registerScannerStatusNotifications()
        appContext.registerReceiver(scannerStatusReceiver, IntentFilter())
    }
}