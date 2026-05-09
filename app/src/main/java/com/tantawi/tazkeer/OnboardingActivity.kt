package com.tantawi.tazkeer

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.button.MaterialButton
import com.tantawi.tazkeer.helpers.LanguageHelper
import com.tantawi.tazkeer.helpers.PreferencesHelper

// First-launch screen that explains the app and lets the user choose a language.
class OnboardingActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var titleText: TextView
    private lateinit var messageText: TextView
    private lateinit var pageIndicatorText: TextView
    private lateinit var previousButton: MaterialButton
    private lateinit var nextButton: MaterialButton
    private lateinit var englishButton: MaterialButton
    private lateinit var arabicButton: MaterialButton

    private var currentPage = 0

    private val pages = listOf(
        OnboardingPage(
            R.string.onboarding_title_welcome,
            R.string.onboarding_message_welcome,
            R.drawable.img_tazkeer_on_boarding
        ),
        OnboardingPage(
            R.string.onboarding_title_tasks,
            R.string.onboarding_message_tasks,
            R.drawable.img_organize_daily_tasks
        ),
        OnboardingPage(
            R.string.onboarding_title_prayers,
            R.string.onboarding_message_prayers,
            R.drawable.img_prayer_and_azkar_reminders
        )
    )

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.wrapContext(newBase))
    }

    // Opens Home immediately when onboarding has already been completed.
    override fun onCreate(savedInstanceState: Bundle?) {
        PreferencesHelper.applyTheme(this)
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (PreferencesHelper.isOnboardingCompleted(this)) {
            openHome()
            return
        }

        setContentView(R.layout.activity_onboarding)
        currentPage = savedInstanceState?.getInt(STATE_CURRENT_PAGE, 0)?.coerceIn(0, pages.lastIndex) ?: 0
        bindViews()
        setupClickListeners()
        showCurrentPage()
        updateLanguageButtons()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_CURRENT_PAGE, currentPage)
    }

    // Connects the Activity fields to the onboarding layout views.
    private fun bindViews() {
        imageView = findViewById(R.id.onboardingImageView)
        titleText = findViewById(R.id.onboardingTitleText)
        messageText = findViewById(R.id.onboardingMessageText)
        pageIndicatorText = findViewById(R.id.onboardingPageIndicatorText)
        previousButton = findViewById(R.id.onboardingPreviousButton)
        nextButton = findViewById(R.id.onboardingNextButton)
        englishButton = findViewById(R.id.onboardingEnglishButton)
        arabicButton = findViewById(R.id.onboardingArabicButton)
    }

    // Handles language selection and page navigation buttons.
    private fun setupClickListeners() {
        englishButton.setOnClickListener {
            selectLanguage(PreferencesHelper.LANGUAGE_ENGLISH)
        }
        arabicButton.setOnClickListener {
            selectLanguage(PreferencesHelper.LANGUAGE_ARABIC)
        }
        previousButton.setOnClickListener {
            if (currentPage > 0) {
                currentPage--
                showCurrentPage()
            }
        }
        nextButton.setOnClickListener {
            if (currentPage == pages.lastIndex) {
                PreferencesHelper.setOnboardingCompleted(this, true)
                openHome()
            } else {
                currentPage++
                showCurrentPage()
            }
        }
    }

    // Binds the current onboarding page data to the visible layout.
    private fun showCurrentPage() {
        val page = pages[currentPage]
        imageView.setImageResource(page.imageResId)
        titleText.setText(page.titleResId)
        messageText.setText(page.messageResId)
        pageIndicatorText.text = getString(R.string.onboarding_page_count, currentPage + 1, pages.size)
        previousButton.visibility = if (currentPage == 0) View.INVISIBLE else View.VISIBLE
        nextButton.setText(
            if (currentPage == pages.lastIndex) {
                R.string.onboarding_get_started
            } else {
                R.string.onboarding_next
            }
        )
    }

    // Saves the selected app language and recreates the screen with localized text.
    private fun selectLanguage(language: String) {
        if (PreferencesHelper.getLanguage(this) == language) {
            updateLanguageButtons()
            return
        }
        PreferencesHelper.setLanguage(this, language)
        recreate()
    }

    // Highlights the currently selected language button.
    private fun updateLanguageButtons() {
        val selectedLanguage = PreferencesHelper.getLanguage(this)
        styleLanguageButton(englishButton, selectedLanguage == PreferencesHelper.LANGUAGE_ENGLISH)
        styleLanguageButton(arabicButton, selectedLanguage == PreferencesHelper.LANGUAGE_ARABIC)
    }

    // Applies simple selected/unselected styling using the existing app colors.
    private fun styleLanguageButton(button: MaterialButton, selected: Boolean) {
        val backgroundColor = if (selected) R.color.green else R.color.surface
        val textColor = if (selected) R.color.text_on_teal else R.color.text_primary
        button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, backgroundColor))
        button.setTextColor(ContextCompat.getColor(this, textColor))
        button.strokeWidth = if (selected) SELECTED_LANGUAGE_STROKE else UNSELECTED_LANGUAGE_STROKE
    }

    // Clears the onboarding Activity and starts the main dashboard.
    private fun openHome() {
        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    private data class OnboardingPage(
        val titleResId: Int,
        val messageResId: Int,
        val imageResId: Int
    )

    companion object {
        private const val STATE_CURRENT_PAGE = "currentPage"
        private const val SELECTED_LANGUAGE_STROKE = 3
        private const val UNSELECTED_LANGUAGE_STROKE = 1
    }
}
