package com.tantawi.tazkeer;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 *2\u00020\u0001:\u0002)*B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0014J\u0012\u0010\u0018\u001a\u00020\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001aH\u0014J\b\u0010\u001d\u001a\u00020\u0015H\u0002J\b\u0010\u001e\u001a\u00020\u0015H\u0002J\b\u0010\u001f\u001a\u00020\u0015H\u0002J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\u0015H\u0002J\u0018\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\'H\u0002J\b\u0010(\u001a\u00020\u0015H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/tantawi/tazkeer/OnboardingActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "imageView", "Landroid/widget/ImageView;", "titleText", "Landroid/widget/TextView;", "messageText", "pageIndicatorText", "previousButton", "Lcom/google/android/material/button/MaterialButton;", "nextButton", "englishButton", "arabicButton", "currentPage", "", "pages", "", "Lcom/tantawi/tazkeer/OnboardingActivity$OnboardingPage;", "attachBaseContext", "", "newBase", "Landroid/content/Context;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSaveInstanceState", "outState", "bindViews", "setupClickListeners", "showCurrentPage", "selectLanguage", "language", "", "updateLanguageButtons", "styleLanguageButton", "button", "selected", "", "openHome", "OnboardingPage", "Companion", "app"})
public final class OnboardingActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.ImageView imageView;
    private android.widget.TextView titleText;
    private android.widget.TextView messageText;
    private android.widget.TextView pageIndicatorText;
    private com.google.android.material.button.MaterialButton previousButton;
    private com.google.android.material.button.MaterialButton nextButton;
    private com.google.android.material.button.MaterialButton englishButton;
    private com.google.android.material.button.MaterialButton arabicButton;
    private int currentPage = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.tantawi.tazkeer.OnboardingActivity.OnboardingPage> pages = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String STATE_CURRENT_PAGE = "currentPage";
    private static final int SELECTED_LANGUAGE_STROKE = 3;
    private static final int UNSELECTED_LANGUAGE_STROKE = 1;
    @org.jetbrains.annotations.NotNull()
    public static final com.tantawi.tazkeer.OnboardingActivity.Companion Companion = null;
    
    public OnboardingActivity() {
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
    
    @java.lang.Override()
    protected void onSaveInstanceState(@org.jetbrains.annotations.NotNull()
    android.os.Bundle outState) {
    }
    
    private final void bindViews() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void showCurrentPage() {
    }
    
    private final void selectLanguage(java.lang.String language) {
    }
    
    private final void updateLanguageButtons() {
    }
    
    private final void styleLanguageButton(com.google.android.material.button.MaterialButton button, boolean selected) {
    }
    
    private final void openHome() {
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/tantawi/tazkeer/OnboardingActivity$Companion;", "", "<init>", "()V", "STATE_CURRENT_PAGE", "", "SELECTED_LANGUAGE_STROKE", "", "UNSELECTED_LANGUAGE_STROKE", "app"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t\u00a8\u0006\u0016"}, d2 = {"Lcom/tantawi/tazkeer/OnboardingActivity$OnboardingPage;", "", "titleResId", "", "messageResId", "imageResId", "<init>", "(III)V", "getTitleResId", "()I", "getMessageResId", "getImageResId", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "app"})
    static final class OnboardingPage {
        private final int titleResId = 0;
        private final int messageResId = 0;
        private final int imageResId = 0;
        
        public OnboardingPage(int titleResId, int messageResId, int imageResId) {
            super();
        }
        
        public final int getTitleResId() {
            return 0;
        }
        
        public final int getMessageResId() {
            return 0;
        }
        
        public final int getImageResId() {
            return 0;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final int component3() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.tantawi.tazkeer.OnboardingActivity.OnboardingPage copy(int titleResId, int messageResId, int imageResId) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}