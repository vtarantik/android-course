package cz.tarantik.android_course.scanner.domain

import cz.tarantik.android_course.scanner.ScannerStatus

data class ScannerState(
    val status: ScannerStatus = ScannerStatus.Unknown,
    val values: List<String> = emptyList(),
)