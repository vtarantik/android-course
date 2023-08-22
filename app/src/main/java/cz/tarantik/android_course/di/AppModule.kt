package cz.tarantik.android_course.di

import cz.tarantik.android_course.scanner.ScannerManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private val appModule = module {
    single { ScannerManager(androidApplication()) }
}

val applicationModules = listOf(
    appModule,
    dataModule,
    uiModule
)