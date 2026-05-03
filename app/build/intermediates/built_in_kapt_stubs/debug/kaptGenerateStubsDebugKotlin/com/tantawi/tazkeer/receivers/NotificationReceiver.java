package com.tantawi.tazkeer.receivers;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\"\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000bH\u0002J*\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000bH\u0002J\u0018\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u00a8\u0006\u0014"}, d2 = {"Lcom/tantawi/tazkeer/receivers/NotificationReceiver;", "Landroid/content/BroadcastReceiver;", "<init>", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "notificationTitle", "", "notificationType", "alarmKind", "notificationBody", "markTaskCompleted", "rescheduleRecurringTask", "taskId", "", "Companion", "app"})
public final class NotificationReceiver extends android.content.BroadcastReceiver {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_MARK_TASK_COMPLETED = "com.tantawi.tazkeer.ACTION_MARK_TASK_COMPLETED";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_NOTIFICATION_ID = "notificationId";
    @org.jetbrains.annotations.NotNull()
    public static final com.tantawi.tazkeer.receivers.NotificationReceiver.Companion Companion = null;
    
    public NotificationReceiver() {
        super();
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    private final java.lang.String notificationTitle(android.content.Context context, java.lang.String notificationType, java.lang.String alarmKind) {
        return null;
    }
    
    private final java.lang.String notificationBody(android.content.Context context, android.content.Intent intent, java.lang.String notificationType, java.lang.String alarmKind) {
        return null;
    }
    
    private final void markTaskCompleted(android.content.Context context, android.content.Intent intent) {
    }
    
    private final void rescheduleRecurringTask(android.content.Context context, long taskId) {
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/tantawi/tazkeer/receivers/NotificationReceiver$Companion;", "", "<init>", "()V", "ACTION_MARK_TASK_COMPLETED", "", "EXTRA_NOTIFICATION_ID", "app"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}