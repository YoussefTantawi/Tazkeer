package com.tantawi.tazkeer;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J\u0012\u0010\u0010\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\rH\u0002J\b\u0010\u0014\u001a\u00020\rH\u0002J\b\u0010\u0015\u001a\u00020\rH\u0002J\u0018\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0002J\b\u0010\u001a\u001a\u00020\rH\u0002J\b\u0010\u001b\u001a\u00020\rH\u0002J\b\u0010\u001c\u001a\u00020\rH\u0002J\b\u0010\u001d\u001a\u00020\rH\u0002J-\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 2\u000e\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020#0\"2\u0006\u0010$\u001a\u00020%H\u0016\u00a2\u0006\u0002\u0010&R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/tantawi/tazkeer/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "database", "Lcom/tantawi/tazkeer/database/AppDatabase;", "notificationsSwitch", "Lcom/google/android/material/switchmaterial/SwitchMaterial;", "languageSwitch", "timeFormatSwitch", "summarySectionSwitch", "upcomingSectionSwitch", "attachBaseContext", "", "newBase", "Landroid/content/Context;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupInitialValues", "setupSettingsActions", "updateLocationAutomatically", "refreshPrayerTimes", "latitude", "", "longitude", "contactUs", "shareApp", "resetPreferences", "restartAppToHome", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "app"})
public final class SettingsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.tantawi.tazkeer.database.AppDatabase database;
    private com.google.android.material.switchmaterial.SwitchMaterial notificationsSwitch;
    private com.google.android.material.switchmaterial.SwitchMaterial languageSwitch;
    private com.google.android.material.switchmaterial.SwitchMaterial timeFormatSwitch;
    private com.google.android.material.switchmaterial.SwitchMaterial summarySectionSwitch;
    private com.google.android.material.switchmaterial.SwitchMaterial upcomingSectionSwitch;
    
    public SettingsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void attachBaseContext(@org.jetbrains.annotations.NotNull()
    android.content.Context newBase) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupInitialValues() {
    }
    
    private final void setupSettingsActions() {
    }
    
    private final void updateLocationAutomatically() {
    }
    
    private final void refreshPrayerTimes(double latitude, double longitude) {
    }
    
    private final void contactUs() {
    }
    
    private final void shareApp() {
    }
    
    private final void resetPreferences() {
    }
    
    private final void restartAppToHome() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
}