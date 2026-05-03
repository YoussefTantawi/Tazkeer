@file:Suppress("DEPRECATION")

package com.tantawi.tazkeer.helpers

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

// Helper applies the saved app language and RTL/LTR direction.
object LanguageHelper {
    fun wrapContext(context: Context): Context {
        val language = PreferencesHelper.getLanguage(context)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }
}
