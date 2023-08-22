package cz.tarantik.android_course.scanner

sealed interface ScannerStatus {
    data object Suspended: ScannerStatus
    data object Resumed: ScannerStatus
    data object Enabled: ScannerStatus
    data object Disabled: ScannerStatus
    data object Unknown: ScannerStatus
}