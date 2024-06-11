package com.application.searchrecipe.utils

import android.app.Application
import com.application.searchrecipe.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RecipeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RecipeApplication)
            modules(appModule)
        }
    }
}