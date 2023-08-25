package cz.tarantik.android_course.scanner.ui

import cz.tarantik.android_course.scanner.ScannerStatus

data class ScannerStateUiModel(
    val status: ScannerStatus = ScannerStatus.Suspended,
    val scannedData: List<String> = emptyList()
)