package com.tantawi.tazkeer.helpers;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u0010\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/tantawi/tazkeer/helpers/PermissionHelper;", "", "<init>", "()V", "REQUEST_LOCATION", "", "REQUEST_NOTIFICATIONS", "hasLocationPermission", "", "context", "Landroid/content/Context;", "requestLocationPermission", "", "activity", "Landroid/app/Activity;", "hasNotificationPermission", "requestNotificationsIfNeeded", "app"})
public final class PermissionHelper {
    public static final int REQUEST_LOCATION = 100;
    public static final int REQUEST_NOTIFICATIONS = 101;
    @org.jetbrains.annotations.NotNull()
    public static final com.tantawi.tazkeer.helpers.PermissionHelper INSTANCE = null;
    
    private PermissionHelper() {
        super();
    }
    
    public final boolean hasLocationPermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void requestLocationPermission(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    public final boolean hasNotificationPermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean requestNotificationsIfNeeded(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
        return false;
    }
}