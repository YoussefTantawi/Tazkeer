package com.tantawi.tazkeer.database;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u000f\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J$\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0010J \u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0017J(\u0010\u0018\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001d\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010\u001f\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001e\u0010!\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006$\u00c0\u0006\u0003"}, d2 = {"Lcom/tantawi/tazkeer/database/TaskDao;", "", "insertTask", "", "task", "Lcom/tantawi/tazkeer/database/TaskEntity;", "(Lcom/tantawi/tazkeer/database/TaskEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTask", "", "deleteTask", "getTodayTasks", "", "startOfDay", "endOfDay", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveTasks", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllTasks", "getCompletedTasks", "getSystemTaskForDate", "taskDate", "", "systemTaskType", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTaskByDateCategoryAndTitle", "category", "title", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTaskById", "taskId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markTaskCompleted", "completedAtMillis", "markTaskNotCompleted", "updatedAtMillis", "getTasksForNotificationRescheduling", "app"})
@androidx.room.Dao()
public abstract interface TaskDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertTask(@org.jetbrains.annotations.NotNull()
    com.tantawi.tazkeer.database.TaskEntity task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTask(@org.jetbrains.annotations.NotNull()
    com.tantawi.tazkeer.database.TaskEntity task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTask(@org.jetbrains.annotations.NotNull()
    com.tantawi.tazkeer.database.TaskEntity task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM tasks\n        WHERE isCompleted = 0\n        AND (recurrenceType != \'Once\' OR timeMillis BETWEEN :startOfDay AND :endOfDay)\n        ORDER BY timeMillis ASC\n        ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTodayTasks(long startOfDay, long endOfDay, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.tantawi.tazkeer.database.TaskEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY timeMillis ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getActiveTasks(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.tantawi.tazkeer.database.TaskEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tasks ORDER BY timeMillis ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllTasks(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.tantawi.tazkeer.database.TaskEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY completedAtMillis DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCompletedTasks(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.tantawi.tazkeer.database.TaskEntity>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM tasks\n        WHERE taskDate = :taskDate\n        AND systemTaskType = :systemTaskType\n        LIMIT 1\n        ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSystemTaskForDate(@org.jetbrains.annotations.NotNull()
    java.lang.String taskDate, @org.jetbrains.annotations.NotNull()
    java.lang.String systemTaskType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tantawi.tazkeer.database.TaskEntity> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM tasks\n        WHERE taskDate = :taskDate\n        AND category = :category\n        AND title = :title\n        LIMIT 1\n        ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTaskByDateCategoryAndTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String taskDate, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tantawi.tazkeer.database.TaskEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTaskById(long taskId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tantawi.tazkeer.database.TaskEntity> $completion);
    
    @androidx.room.Query(value = "UPDATE tasks SET isCompleted = 1, completedAtMillis = :completedAtMillis, updatedAtMillis = :completedAtMillis WHERE id = :taskId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markTaskCompleted(long taskId, long completedAtMillis, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE tasks SET isCompleted = 0, completedAtMillis = NULL, updatedAtMillis = :updatedAtMillis WHERE id = :taskId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markTaskNotCompleted(long taskId, long updatedAtMillis, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE isCompleted = 0 AND notificationEnabled = 1 ORDER BY timeMillis ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTasksForNotificationRescheduling(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.tantawi.tazkeer.database.TaskEntity>> $completion);
}