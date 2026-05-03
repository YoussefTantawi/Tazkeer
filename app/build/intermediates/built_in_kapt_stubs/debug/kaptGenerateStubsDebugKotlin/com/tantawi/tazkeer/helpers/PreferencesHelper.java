package com.tantawi.tazkeer.helpers;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u001f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0018\u001a\n \u001a*\u0004\u0018\u00010\u00190\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001f\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u001eJ\u000e\u0010\"\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010#\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010$\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u0005J\u000e\u0010&\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\'\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u001eJ\u000e\u0010(\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010)\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u001eJ\u000e\u0010*\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010+\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u001eJ\u000e\u0010,\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010-\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u001eJ\u000e\u0010.\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010/\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cJ\u001e\u00100\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u00101\u001a\u00020\u00142\u0006\u00102\u001a\u00020\u0014J\u0016\u00103\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u00104\u001a\u00020\u0005J\u001e\u00105\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u00104\u001a\u00020\u00052\u0006\u00106\u001a\u00020\u001eJ\u0016\u00107\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u00108\u001a\u00020\u0005J\u001e\u00109\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010:\u001a\u00020\u00052\u0006\u0010;\u001a\u00020\u0005J&\u0010<\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010:\u001a\u00020\u00052\u0006\u0010;\u001a\u00020\u00052\u0006\u0010=\u001a\u00020\u001eJ\u000e\u0010>\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2 = {"Lcom/tantawi/tazkeer/helpers/PreferencesHelper;", "", "<init>", "()V", "PREFS_NAME", "", "KEY_DARK_MODE", "KEY_LANGUAGE", "KEY_NOTIFICATIONS", "KEY_LATITUDE", "KEY_LONGITUDE", "KEY_USE_24_HOUR_TIME", "KEY_LAST_RESET_DATE", "KEY_SUMMARY_SECTION_ENABLED", "KEY_UPCOMING_SECTION_ENABLED", "KEY_SECTION_VISIBLE_PREFIX", "KEY_PRAYER_COMPLETED_PREFIX", "KEY_AZKAR_COMPLETED_PREFIX", "KEY_SYSTEM_TASK_DELETED_PREFIX", "DEFAULT_LATITUDE", "", "DEFAULT_LONGITUDE", "LANGUAGE_ENGLISH", "LANGUAGE_ARABIC", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "context", "Landroid/content/Context;", "isDarkMode", "", "setDarkMode", "", "enabled", "applyTheme", "getLanguage", "setLanguage", "language", "areNotificationsEnabled", "setNotificationsEnabled", "use24HourPrayerTime", "setUse24HourPrayerTime", "isSummarySectionEnabled", "setSummarySectionEnabled", "isUpcomingSectionEnabled", "setUpcomingSectionEnabled", "getLatitude", "getLongitude", "setLocation", "latitude", "longitude", "isSectionVisible", "sectionKey", "setSectionVisible", "visible", "resetDailyCompletionsIfNeeded", "currentDate", "isSystemTaskDeleted", "date", "systemTaskType", "setSystemTaskDeleted", "deleted", "reset", "app"})
public final class PreferencesHelper {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "tazkeer_preferences";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DARK_MODE = "dark_mode";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LANGUAGE = "language";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_NOTIFICATIONS = "notifications";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LATITUDE = "latitude";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LONGITUDE = "longitude";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_USE_24_HOUR_TIME = "use_24_hour_time";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LAST_RESET_DATE = "last_reset_date";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SUMMARY_SECTION_ENABLED = "summary_section_enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_UPCOMING_SECTION_ENABLED = "upcoming_section_enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SECTION_VISIBLE_PREFIX = "section_visible_";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PRAYER_COMPLETED_PREFIX = "prayer_completed_";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_AZKAR_COMPLETED_PREFIX = "azkar_completed_";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SYSTEM_TASK_DELETED_PREFIX = "system_task_deleted_";
    public static final double DEFAULT_LATITUDE = 30.0444;
    public static final double DEFAULT_LONGITUDE = 31.2357;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LANGUAGE_ENGLISH = "en";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LANGUAGE_ARABIC = "ar";
    @org.jetbrains.annotations.NotNull()
    public static final com.tantawi.tazkeer.helpers.PreferencesHelper INSTANCE = null;
    
    private PreferencesHelper() {
        super();
    }
    
    private final android.content.SharedPreferences prefs(android.content.Context context) {
        return null;
    }
    
    public final boolean isDarkMode(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setDarkMode(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean enabled) {
    }
    
    public final void applyTheme(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLanguage(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final void setLanguage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
    
    public final boolean areNotificationsEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setNotificationsEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean enabled) {
    }
    
    public final boolean use24HourPrayerTime(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setUse24HourPrayerTime(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean enabled) {
    }
    
    public final boolean isSummarySectionEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setSummarySectionEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean enabled) {
    }
    
    public final boolean isUpcomingSectionEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setUpcomingSectionEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean enabled) {
    }
    
    public final double getLatitude(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0.0;
    }
    
    public final double getLongitude(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0.0;
    }
    
    public final void setLocation(@org.jetbrains.annotations.NotNull()
    android.content.Context context, double latitude, double longitude) {
    }
    
    public final boolean isSectionVisible(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String sectionKey) {
        return false;
    }
    
    public final void setSectionVisible(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String sectionKey, boolean visible) {
    }
    
    public final void resetDailyCompletionsIfNeeded(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String currentDate) {
    }
    
    public final boolean isSystemTaskDeleted(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String systemTaskType) {
        return false;
    }
    
    public final void setSystemTaskDeleted(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String systemTaskType, boolean deleted) {
    }
    
    public final void reset(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
}