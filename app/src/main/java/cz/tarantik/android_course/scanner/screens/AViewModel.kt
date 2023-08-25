package cz.tarantik.android_course.scanner.screens

import android.util.Log
import cz.tarantik.android_course.base.BaseViewModel
import cz.tarantik.android_course.scanner.ScannerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AViewModel(private val scannerManager: ScannerManager): BaseViewModel() {
    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

    init {
        scannerManager.status.onEach {
            Log.d("AVM", "Received")
        }.launchIn(this)
    }

    fun enableScanner() {
        scannerManager.resumeScanner()
    }
}