package com.tantawi.tazkeer.database;

import androidx.annotation.NonNull;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<TaskEntity> __insertAdapterOfTaskEntity;

  private final EntityDeleteOrUpdateAdapter<TaskEntity> __deleteAdapterOfTaskEntity;

  private final EntityDeleteOrUpdateAdapter<TaskEntity> __updateAdapterOfTaskEntity;

  public TaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfTaskEntity = new EntityInsertAdapter<TaskEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tasks` (`id`,`title`,`description`,`timeMillis`,`category`,`customCategory`,`priority`,`recurrenceType`,`selectedDaysCsv`,`isCompleted`,`completedAtMillis`,`createdAtMillis`,`updatedAtMillis`,`notificationEnabled`,`taskDate`,`isSystemTask`,`systemTaskType`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final TaskEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        statement.bindLong(4, entity.getTimeMillis());
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getCategory());
        }
        if (entity.getCustomCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getCustomCategory());
        }
        if (entity.getPriority() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getPriority());
        }
        if (entity.getRecurrenceType() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getRecurrenceType());
        }
        if (entity.getSelectedDaysCsv() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getSelectedDaysCsv());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.getCompletedAtMillis() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getCompletedAtMillis());
        }
        statement.bindLong(12, entity.getCreatedAtMillis());
        statement.bindLong(13, entity.getUpdatedAtMillis());
        final int _tmp_1 = entity.getNotificationEnabled() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        if (entity.getTaskDate() == null) {
          statement.bindNull(15);
        } else {
          statement.bindText(15, entity.getTaskDate());
        }
        final int _tmp_2 = entity.isSystemTask() ? 1 : 0;
        statement.bindLong(16, _tmp_2);
        if (entity.getSystemTaskType() == null) {
          statement.bindNull(17);
        } else {
          statement.bindText(17, entity.getSystemTaskType());
        }
      }
    };
    this.__deleteAdapterOfTaskEntity = new EntityDeleteOrUpdateAdapter<TaskEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tasks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final TaskEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTaskEntity = new EntityDeleteOrUpdateAdapter<TaskEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tasks` SET `id` = ?,`title` = ?,`description` = ?,`timeMillis` = ?,`category` = ?,`customCategory` = ?,`priority` = ?,`recurrenceType` = ?,`selectedDaysCsv` = ?,`isCompleted` = ?,`completedAtMillis` = ?,`createdAtMillis` = ?,`updatedAtMillis` = ?,`notificationEnabled` = ?,`taskDate` = ?,`isSystemTask` = ?,`systemTaskType` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final TaskEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        statement.bindLong(4, entity.getTimeMillis());
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getCategory());
        }
        if (entity.getCustomCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getCustomCategory());
        }
        if (entity.getPriority() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getPriority());
        }
        if (entity.getRecurrenceType() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getRecurrenceType());
        }
        if (entity.getSelectedDaysCsv() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getSelectedDaysCsv());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.getCompletedAtMillis() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getCompletedAtMillis());
        }
        statement.bindLong(12, entity.getCreatedAtMillis());
        statement.bindLong(13, entity.getUpdatedAtMillis());
        final int _tmp_1 = entity.getNotificationEnabled() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        if (entity.getTaskDate() == null) {
          statement.bindNull(15);
        } else {
          statement.bindText(15, entity.getTaskDate());
        }
        final int _tmp_2 = entity.isSystemTask() ? 1 : 0;
        statement.bindLong(16, _tmp_2);
        if (entity.getSystemTaskType() == null) {
          statement.bindNull(17);
        } else {
          statement.bindText(17, entity.getSystemTaskType());
        }
        statement.bindLong(18, entity.getId());
      }
    };
  }

  @Override
  public Object insertTask(final TaskEntity task, final Continuation<? super Long> $completion) {
    if (task == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfTaskEntity.insertAndReturnId(_connection, task);
    }, $completion);
  }

  @Override
  public Object deleteTask(final TaskEntity task, final Continuation<? super Unit> $completion) {
    if (task == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfTaskEntity.handle(_connection, task);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object updateTask(final TaskEntity task, final Continuation<? super Unit> $completion) {
    if (task == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfTaskEntity.handle(_connection, task);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getTodayTasks(final long startOfDay, final long endOfDay,
      final Continuation<? super List<TaskEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM tasks\n"
            + "        WHERE isCompleted = 0\n"
            + "        AND (recurrenceType != 'Once' OR timeMillis BETWEEN ? AND ?)\n"
            + "        ORDER BY timeMillis ASC\n"
            + "        ";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, startOfDay);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, endOfDay);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTimeMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeMillis");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfCustomCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "customCategory");
        final int _columnIndexOfPriority = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "priority");
        final int _columnIndexOfRecurrenceType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recurrenceType");
        final int _columnIndexOfSelectedDaysCsv = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selectedDaysCsv");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAtMillis");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfUpdatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAtMillis");
        final int _columnIndexOfNotificationEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationEnabled");
        final int _columnIndexOfTaskDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "taskDate");
        final int _columnIndexOfIsSystemTask = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemTask");
        final int _columnIndexOfSystemTaskType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "systemTaskType");
        final List<TaskEntity> _result = new ArrayList<TaskEntity>();
        while (_stmt.step()) {
          final TaskEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final long _tmpTimeMillis;
          _tmpTimeMillis = _stmt.getLong(_columnIndexOfTimeMillis);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpCustomCategory;
          if (_stmt.isNull(_columnIndexOfCustomCategory)) {
            _tmpCustomCategory = null;
          } else {
            _tmpCustomCategory = _stmt.getText(_columnIndexOfCustomCategory);
          }
          final String _tmpPriority;
          if (_stmt.isNull(_columnIndexOfPriority)) {
            _tmpPriority = null;
          } else {
            _tmpPriority = _stmt.getText(_columnIndexOfPriority);
          }
          final String _tmpRecurrenceType;
          if (_stmt.isNull(_columnIndexOfRecurrenceType)) {
            _tmpRecurrenceType = null;
          } else {
            _tmpRecurrenceType = _stmt.getText(_columnIndexOfRecurrenceType);
          }
          final String _tmpSelectedDaysCsv;
          if (_stmt.isNull(_columnIndexOfSelectedDaysCsv)) {
            _tmpSelectedDaysCsv = null;
          } else {
            _tmpSelectedDaysCsv = _stmt.getText(_columnIndexOfSelectedDaysCsv);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final long _tmpUpdatedAtMillis;
          _tmpUpdatedAtMillis = _stmt.getLong(_columnIndexOfUpdatedAtMillis);
          final boolean _tmpNotificationEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfNotificationEnabled));
          _tmpNotificationEnabled = _tmp_1 != 0;
          final String _tmpTaskDate;
          if (_stmt.isNull(_columnIndexOfTaskDate)) {
            _tmpTaskDate = null;
          } else {
            _tmpTaskDate = _stmt.getText(_columnIndexOfTaskDate);
          }
          final boolean _tmpIsSystemTask;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemTask));
          _tmpIsSystemTask = _tmp_2 != 0;
          final String _tmpSystemTaskType;
          if (_stmt.isNull(_columnIndexOfSystemTaskType)) {
            _tmpSystemTaskType = null;
          } else {
            _tmpSystemTaskType = _stmt.getText(_columnIndexOfSystemTaskType);
          }
          _item = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTimeMillis,_tmpCategory,_tmpCustomCategory,_tmpPriority,_tmpRecurrenceType,_tmpSelectedDaysCsv,_tmpIsCompleted,_tmpCompletedAtMillis,_tmpCreatedAtMillis,_tmpUpdatedAtMillis,_tmpNotificationEnabled,_tmpTaskDate,_tmpIsSystemTask,_tmpSystemTaskType);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getActiveTasks(final Continuation<? super List<TaskEntity>> $completion) {
    final String _sql = "SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY timeMillis ASC";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTimeMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeMillis");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfCustomCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "customCategory");
        final int _columnIndexOfPriority = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "priority");
        final int _columnIndexOfRecurrenceType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recurrenceType");
        final int _columnIndexOfSelectedDaysCsv = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selectedDaysCsv");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAtMillis");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfUpdatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAtMillis");
        final int _columnIndexOfNotificationEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationEnabled");
        final int _columnIndexOfTaskDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "taskDate");
        final int _columnIndexOfIsSystemTask = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemTask");
        final int _columnIndexOfSystemTaskType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "systemTaskType");
        final List<TaskEntity> _result = new ArrayList<TaskEntity>();
        while (_stmt.step()) {
          final TaskEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final long _tmpTimeMillis;
          _tmpTimeMillis = _stmt.getLong(_columnIndexOfTimeMillis);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpCustomCategory;
          if (_stmt.isNull(_columnIndexOfCustomCategory)) {
            _tmpCustomCategory = null;
          } else {
            _tmpCustomCategory = _stmt.getText(_columnIndexOfCustomCategory);
          }
          final String _tmpPriority;
          if (_stmt.isNull(_columnIndexOfPriority)) {
            _tmpPriority = null;
          } else {
            _tmpPriority = _stmt.getText(_columnIndexOfPriority);
          }
          final String _tmpRecurrenceType;
          if (_stmt.isNull(_columnIndexOfRecurrenceType)) {
            _tmpRecurrenceType = null;
          } else {
            _tmpRecurrenceType = _stmt.getText(_columnIndexOfRecurrenceType);
          }
          final String _tmpSelectedDaysCsv;
          if (_stmt.isNull(_columnIndexOfSelectedDaysCsv)) {
            _tmpSelectedDaysCsv = null;
          } else {
            _tmpSelectedDaysCsv = _stmt.getText(_columnIndexOfSelectedDaysCsv);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final long _tmpUpdatedAtMillis;
          _tmpUpdatedAtMillis = _stmt.getLong(_columnIndexOfUpdatedAtMillis);
          final boolean _tmpNotificationEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfNotificationEnabled));
          _tmpNotificationEnabled = _tmp_1 != 0;
          final String _tmpTaskDate;
          if (_stmt.isNull(_columnIndexOfTaskDate)) {
            _tmpTaskDate = null;
          } else {
            _tmpTaskDate = _stmt.getText(_columnIndexOfTaskDate);
          }
          final boolean _tmpIsSystemTask;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemTask));
          _tmpIsSystemTask = _tmp_2 != 0;
          final String _tmpSystemTaskType;
          if (_stmt.isNull(_columnIndexOfSystemTaskType)) {
            _tmpSystemTaskType = null;
          } else {
            _tmpSystemTaskType = _stmt.getText(_columnIndexOfSystemTaskType);
          }
          _item = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTimeMillis,_tmpCategory,_tmpCustomCategory,_tmpPriority,_tmpRecurrenceType,_tmpSelectedDaysCsv,_tmpIsCompleted,_tmpCompletedAtMillis,_tmpCreatedAtMillis,_tmpUpdatedAtMillis,_tmpNotificationEnabled,_tmpTaskDate,_tmpIsSystemTask,_tmpSystemTaskType);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getAllTasks(final Continuation<? super List<TaskEntity>> $completion) {
    final String _sql = "SELECT * FROM tasks ORDER BY timeMillis ASC";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTimeMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeMillis");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfCustomCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "customCategory");
        final int _columnIndexOfPriority = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "priority");
        final int _columnIndexOfRecurrenceType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recurrenceType");
        final int _columnIndexOfSelectedDaysCsv = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selectedDaysCsv");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAtMillis");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfUpdatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAtMillis");
        final int _columnIndexOfNotificationEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationEnabled");
        final int _columnIndexOfTaskDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "taskDate");
        final int _columnIndexOfIsSystemTask = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemTask");
        final int _columnIndexOfSystemTaskType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "systemTaskType");
        final List<TaskEntity> _result = new ArrayList<TaskEntity>();
        while (_stmt.step()) {
          final TaskEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final long _tmpTimeMillis;
          _tmpTimeMillis = _stmt.getLong(_columnIndexOfTimeMillis);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpCustomCategory;
          if (_stmt.isNull(_columnIndexOfCustomCategory)) {
            _tmpCustomCategory = null;
          } else {
            _tmpCustomCategory = _stmt.getText(_columnIndexOfCustomCategory);
          }
          final String _tmpPriority;
          if (_stmt.isNull(_columnIndexOfPriority)) {
            _tmpPriority = null;
          } else {
            _tmpPriority = _stmt.getText(_columnIndexOfPriority);
          }
          final String _tmpRecurrenceType;
          if (_stmt.isNull(_columnIndexOfRecurrenceType)) {
            _tmpRecurrenceType = null;
          } else {
            _tmpRecurrenceType = _stmt.getText(_columnIndexOfRecurrenceType);
          }
          final String _tmpSelectedDaysCsv;
          if (_stmt.isNull(_columnIndexOfSelectedDaysCsv)) {
            _tmpSelectedDaysCsv = null;
          } else {
            _tmpSelectedDaysCsv = _stmt.getText(_columnIndexOfSelectedDaysCsv);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final long _tmpUpdatedAtMillis;
          _tmpUpdatedAtMillis = _stmt.getLong(_columnIndexOfUpdatedAtMillis);
          final boolean _tmpNotificationEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfNotificationEnabled));
          _tmpNotificationEnabled = _tmp_1 != 0;
          final String _tmpTaskDate;
          if (_stmt.isNull(_columnIndexOfTaskDate)) {
            _tmpTaskDate = null;
          } else {
            _tmpTaskDate = _stmt.getText(_columnIndexOfTaskDate);
          }
          final boolean _tmpIsSystemTask;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemTask));
          _tmpIsSystemTask = _tmp_2 != 0;
          final String _tmpSystemTaskType;
          if (_stmt.isNull(_columnIndexOfSystemTaskType)) {
            _tmpSystemTaskType = null;
          } else {
            _tmpSystemTaskType = _stmt.getText(_columnIndexOfSystemTaskType);
          }
          _item = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTimeMillis,_tmpCategory,_tmpCustomCategory,_tmpPriority,_tmpRecurrenceType,_tmpSelectedDaysCsv,_tmpIsCompleted,_tmpCompletedAtMillis,_tmpCreatedAtMillis,_tmpUpdatedAtMillis,_tmpNotificationEnabled,_tmpTaskDate,_tmpIsSystemTask,_tmpSystemTaskType);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getCompletedTasks(final Continuation<? super List<TaskEntity>> $completion) {
    final String _sql = "SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY completedAtMillis DESC";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTimeMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeMillis");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfCustomCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "customCategory");
        final int _columnIndexOfPriority = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "priority");
        final int _columnIndexOfRecurrenceType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recurrenceType");
        final int _columnIndexOfSelectedDaysCsv = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selectedDaysCsv");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAtMillis");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfUpdatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAtMillis");
        final int _columnIndexOfNotificationEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationEnabled");
        final int _columnIndexOfTaskDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "taskDate");
        final int _columnIndexOfIsSystemTask = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemTask");
        final int _columnIndexOfSystemTaskType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "systemTaskType");
        final List<TaskEntity> _result = new ArrayList<TaskEntity>();
        while (_stmt.step()) {
          final TaskEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final long _tmpTimeMillis;
          _tmpTimeMillis = _stmt.getLong(_columnIndexOfTimeMillis);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpCustomCategory;
          if (_stmt.isNull(_columnIndexOfCustomCategory)) {
            _tmpCustomCategory = null;
          } else {
            _tmpCustomCategory = _stmt.getText(_columnIndexOfCustomCategory);
          }
          final String _tmpPriority;
          if (_stmt.isNull(_columnIndexOfPriority)) {
            _tmpPriority = null;
          } else {
            _tmpPriority = _stmt.getText(_columnIndexOfPriority);
          }
          final String _tmpRecurrenceType;
          if (_stmt.isNull(_columnIndexOfRecurrenceType)) {
            _tmpRecurrenceType = null;
          } else {
            _tmpRecurrenceType = _stmt.getText(_columnIndexOfRecurrenceType);
          }
          final String _tmpSelectedDaysCsv;
          if (_stmt.isNull(_columnIndexOfSelectedDaysCsv)) {
            _tmpSelectedDaysCsv = null;
          } else {
            _tmpSelectedDaysCsv = _stmt.getText(_columnIndexOfSelectedDaysCsv);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final long _tmpUpdatedAtMillis;
          _tmpUpdatedAtMillis = _stmt.getLong(_columnIndexOfUpdatedAtMillis);
          final boolean _tmpNotificationEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfNotificationEnabled));
          _tmpNotificationEnabled = _tmp_1 != 0;
          final String _tmpTaskDate;
          if (_stmt.isNull(_columnIndexOfTaskDate)) {
            _tmpTaskDate = null;
          } else {
            _tmpTaskDate = _stmt.getText(_columnIndexOfTaskDate);
          }
          final boolean _tmpIsSystemTask;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemTask));
          _tmpIsSystemTask = _tmp_2 != 0;
          final String _tmpSystemTaskType;
          if (_stmt.isNull(_columnIndexOfSystemTaskType)) {
            _tmpSystemTaskType = null;
          } else {
            _tmpSystemTaskType = _stmt.getText(_columnIndexOfSystemTaskType);
          }
          _item = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTimeMillis,_tmpCategory,_tmpCustomCategory,_tmpPriority,_tmpRecurrenceType,_tmpSelectedDaysCsv,_tmpIsCompleted,_tmpCompletedAtMillis,_tmpCreatedAtMillis,_tmpUpdatedAtMillis,_tmpNotificationEnabled,_tmpTaskDate,_tmpIsSystemTask,_tmpSystemTaskType);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getSystemTaskForDate(final String taskDate, final String systemTaskType,
      final Continuation<? super TaskEntity> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM tasks\n"
            + "        WHERE taskDate = ?\n"
            + "        AND systemTaskType = ?\n"
            + "        LIMIT 1\n"
            + "        ";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (taskDate == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, taskDate);
        }
        _argIndex = 2;
        if (systemTaskType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, systemTaskType);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTimeMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeMillis");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfCustomCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "customCategory");
        final int _columnIndexOfPriority = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "priority");
        final int _columnIndexOfRecurrenceType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recurrenceType");
        final int _columnIndexOfSelectedDaysCsv = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selectedDaysCsv");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAtMillis");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfUpdatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAtMillis");
        final int _columnIndexOfNotificationEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationEnabled");
        final int _columnIndexOfTaskDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "taskDate");
        final int _columnIndexOfIsSystemTask = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemTask");
        final int _columnIndexOfSystemTaskType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "systemTaskType");
        final TaskEntity _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final long _tmpTimeMillis;
          _tmpTimeMillis = _stmt.getLong(_columnIndexOfTimeMillis);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpCustomCategory;
          if (_stmt.isNull(_columnIndexOfCustomCategory)) {
            _tmpCustomCategory = null;
          } else {
            _tmpCustomCategory = _stmt.getText(_columnIndexOfCustomCategory);
          }
          final String _tmpPriority;
          if (_stmt.isNull(_columnIndexOfPriority)) {
            _tmpPriority = null;
          } else {
            _tmpPriority = _stmt.getText(_columnIndexOfPriority);
          }
          final String _tmpRecurrenceType;
          if (_stmt.isNull(_columnIndexOfRecurrenceType)) {
            _tmpRecurrenceType = null;
          } else {
            _tmpRecurrenceType = _stmt.getText(_columnIndexOfRecurrenceType);
          }
          final String _tmpSelectedDaysCsv;
          if (_stmt.isNull(_columnIndexOfSelectedDaysCsv)) {
            _tmpSelectedDaysCsv = null;
          } else {
            _tmpSelectedDaysCsv = _stmt.getText(_columnIndexOfSelectedDaysCsv);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final long _tmpUpdatedAtMillis;
          _tmpUpdatedAtMillis = _stmt.getLong(_columnIndexOfUpdatedAtMillis);
          final boolean _tmpNotificationEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfNotificationEnabled));
          _tmpNotificationEnabled = _tmp_1 != 0;
          final String _tmpTaskDate;
          if (_stmt.isNull(_columnIndexOfTaskDate)) {
            _tmpTaskDate = null;
          } else {
            _tmpTaskDate = _stmt.getText(_columnIndexOfTaskDate);
          }
          final boolean _tmpIsSystemTask;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemTask));
          _tmpIsSystemTask = _tmp_2 != 0;
          final String _tmpSystemTaskType;
          if (_stmt.isNull(_columnIndexOfSystemTaskType)) {
            _tmpSystemTaskType = null;
          } else {
            _tmpSystemTaskType = _stmt.getText(_columnIndexOfSystemTaskType);
          }
          _result = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTimeMillis,_tmpCategory,_tmpCustomCategory,_tmpPriority,_tmpRecurrenceType,_tmpSelectedDaysCsv,_tmpIsCompleted,_tmpCompletedAtMillis,_tmpCreatedAtMillis,_tmpUpdatedAtMillis,_tmpNotificationEnabled,_tmpTaskDate,_tmpIsSystemTask,_tmpSystemTaskType);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getTaskByDateCategoryAndTitle(final String taskDate, final String category,
      final String title, final Continuation<? super TaskEntity> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM tasks\n"
            + "        WHERE taskDate = ?\n"
            + "        AND category = ?\n"
            + "        AND title = ?\n"
            + "        LIMIT 1\n"
            + "        ";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (taskDate == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, taskDate);
        }
        _argIndex = 2;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
        }
        _argIndex = 3;
        if (title == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, title);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTimeMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeMillis");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfCustomCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "customCategory");
        final int _columnIndexOfPriority = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "priority");
        final int _columnIndexOfRecurrenceType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recurrenceType");
        final int _columnIndexOfSelectedDaysCsv = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selectedDaysCsv");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAtMillis");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfUpdatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAtMillis");
        final int _columnIndexOfNotificationEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationEnabled");
        final int _columnIndexOfTaskDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "taskDate");
        final int _columnIndexOfIsSystemTask = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemTask");
        final int _columnIndexOfSystemTaskType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "systemTaskType");
        final TaskEntity _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final long _tmpTimeMillis;
          _tmpTimeMillis = _stmt.getLong(_columnIndexOfTimeMillis);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpCustomCategory;
          if (_stmt.isNull(_columnIndexOfCustomCategory)) {
            _tmpCustomCategory = null;
          } else {
            _tmpCustomCategory = _stmt.getText(_columnIndexOfCustomCategory);
          }
          final String _tmpPriority;
          if (_stmt.isNull(_columnIndexOfPriority)) {
            _tmpPriority = null;
          } else {
            _tmpPriority = _stmt.getText(_columnIndexOfPriority);
          }
          final String _tmpRecurrenceType;
          if (_stmt.isNull(_columnIndexOfRecurrenceType)) {
            _tmpRecurrenceType = null;
          } else {
            _tmpRecurrenceType = _stmt.getText(_columnIndexOfRecurrenceType);
          }
          final String _tmpSelectedDaysCsv;
          if (_stmt.isNull(_columnIndexOfSelectedDaysCsv)) {
            _tmpSelectedDaysCsv = null;
          } else {
            _tmpSelectedDaysCsv = _stmt.getText(_columnIndexOfSelectedDaysCsv);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final long _tmpUpdatedAtMillis;
          _tmpUpdatedAtMillis = _stmt.getLong(_columnIndexOfUpdatedAtMillis);
          final boolean _tmpNotificationEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfNotificationEnabled));
          _tmpNotificationEnabled = _tmp_1 != 0;
          final String _tmpTaskDate;
          if (_stmt.isNull(_columnIndexOfTaskDate)) {
            _tmpTaskDate = null;
          } else {
            _tmpTaskDate = _stmt.getText(_columnIndexOfTaskDate);
          }
          final boolean _tmpIsSystemTask;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemTask));
          _tmpIsSystemTask = _tmp_2 != 0;
          final String _tmpSystemTaskType;
          if (_stmt.isNull(_columnIndexOfSystemTaskType)) {
            _tmpSystemTaskType = null;
          } else {
            _tmpSystemTaskType = _stmt.getText(_columnIndexOfSystemTaskType);
          }
          _result = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTimeMillis,_tmpCategory,_tmpCustomCategory,_tmpPriority,_tmpRecurrenceType,_tmpSelectedDaysCsv,_tmpIsCompleted,_tmpCompletedAtMillis,_tmpCreatedAtMillis,_tmpUpdatedAtMillis,_tmpNotificationEnabled,_tmpTaskDate,_tmpIsSystemTask,_tmpSystemTaskType);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getTaskById(final long taskId, final Continuation<? super TaskEntity> $completion) {
    final String _sql = "SELECT * FROM tasks WHERE id = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, taskId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTimeMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeMillis");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfCustomCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "customCategory");
        final int _columnIndexOfPriority = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "priority");
        final int _columnIndexOfRecurrenceType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recurrenceType");
        final int _columnIndexOfSelectedDaysCsv = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selectedDaysCsv");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAtMillis");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfUpdatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAtMillis");
        final int _columnIndexOfNotificationEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationEnabled");
        final int _columnIndexOfTaskDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "taskDate");
        final int _columnIndexOfIsSystemTask = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemTask");
        final int _columnIndexOfSystemTaskType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "systemTaskType");
        final TaskEntity _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final long _tmpTimeMillis;
          _tmpTimeMillis = _stmt.getLong(_columnIndexOfTimeMillis);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpCustomCategory;
          if (_stmt.isNull(_columnIndexOfCustomCategory)) {
            _tmpCustomCategory = null;
          } else {
            _tmpCustomCategory = _stmt.getText(_columnIndexOfCustomCategory);
          }
          final String _tmpPriority;
          if (_stmt.isNull(_columnIndexOfPriority)) {
            _tmpPriority = null;
          } else {
            _tmpPriority = _stmt.getText(_columnIndexOfPriority);
          }
          final String _tmpRecurrenceType;
          if (_stmt.isNull(_columnIndexOfRecurrenceType)) {
            _tmpRecurrenceType = null;
          } else {
            _tmpRecurrenceType = _stmt.getText(_columnIndexOfRecurrenceType);
          }
          final String _tmpSelectedDaysCsv;
          if (_stmt.isNull(_columnIndexOfSelectedDaysCsv)) {
            _tmpSelectedDaysCsv = null;
          } else {
            _tmpSelectedDaysCsv = _stmt.getText(_columnIndexOfSelectedDaysCsv);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final long _tmpUpdatedAtMillis;
          _tmpUpdatedAtMillis = _stmt.getLong(_columnIndexOfUpdatedAtMillis);
          final boolean _tmpNotificationEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfNotificationEnabled));
          _tmpNotificationEnabled = _tmp_1 != 0;
          final String _tmpTaskDate;
          if (_stmt.isNull(_columnIndexOfTaskDate)) {
            _tmpTaskDate = null;
          } else {
            _tmpTaskDate = _stmt.getText(_columnIndexOfTaskDate);
          }
          final boolean _tmpIsSystemTask;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemTask));
          _tmpIsSystemTask = _tmp_2 != 0;
          final String _tmpSystemTaskType;
          if (_stmt.isNull(_columnIndexOfSystemTaskType)) {
            _tmpSystemTaskType = null;
          } else {
            _tmpSystemTaskType = _stmt.getText(_columnIndexOfSystemTaskType);
          }
          _result = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTimeMillis,_tmpCategory,_tmpCustomCategory,_tmpPriority,_tmpRecurrenceType,_tmpSelectedDaysCsv,_tmpIsCompleted,_tmpCompletedAtMillis,_tmpCreatedAtMillis,_tmpUpdatedAtMillis,_tmpNotificationEnabled,_tmpTaskDate,_tmpIsSystemTask,_tmpSystemTaskType);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getTasksForNotificationRescheduling(
      final Continuation<? super List<TaskEntity>> $completion) {
    final String _sql = "SELECT * FROM tasks WHERE isCompleted = 0 AND notificationEnabled = 1 ORDER BY timeMillis ASC";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTimeMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeMillis");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfCustomCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "customCategory");
        final int _columnIndexOfPriority = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "priority");
        final int _columnIndexOfRecurrenceType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recurrenceType");
        final int _columnIndexOfSelectedDaysCsv = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selectedDaysCsv");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAtMillis");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfUpdatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "updatedAtMillis");
        final int _columnIndexOfNotificationEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationEnabled");
        final int _columnIndexOfTaskDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "taskDate");
        final int _columnIndexOfIsSystemTask = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemTask");
        final int _columnIndexOfSystemTaskType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "systemTaskType");
        final List<TaskEntity> _result = new ArrayList<TaskEntity>();
        while (_stmt.step()) {
          final TaskEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final long _tmpTimeMillis;
          _tmpTimeMillis = _stmt.getLong(_columnIndexOfTimeMillis);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpCustomCategory;
          if (_stmt.isNull(_columnIndexOfCustomCategory)) {
            _tmpCustomCategory = null;
          } else {
            _tmpCustomCategory = _stmt.getText(_columnIndexOfCustomCategory);
          }
          final String _tmpPriority;
          if (_stmt.isNull(_columnIndexOfPriority)) {
            _tmpPriority = null;
          } else {
            _tmpPriority = _stmt.getText(_columnIndexOfPriority);
          }
          final String _tmpRecurrenceType;
          if (_stmt.isNull(_columnIndexOfRecurrenceType)) {
            _tmpRecurrenceType = null;
          } else {
            _tmpRecurrenceType = _stmt.getText(_columnIndexOfRecurrenceType);
          }
          final String _tmpSelectedDaysCsv;
          if (_stmt.isNull(_columnIndexOfSelectedDaysCsv)) {
            _tmpSelectedDaysCsv = null;
          } else {
            _tmpSelectedDaysCsv = _stmt.getText(_columnIndexOfSelectedDaysCsv);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final long _tmpUpdatedAtMillis;
          _tmpUpdatedAtMillis = _stmt.getLong(_columnIndexOfUpdatedAtMillis);
          final boolean _tmpNotificationEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfNotificationEnabled));
          _tmpNotificationEnabled = _tmp_1 != 0;
          final String _tmpTaskDate;
          if (_stmt.isNull(_columnIndexOfTaskDate)) {
            _tmpTaskDate = null;
          } else {
            _tmpTaskDate = _stmt.getText(_columnIndexOfTaskDate);
          }
          final boolean _tmpIsSystemTask;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemTask));
          _tmpIsSystemTask = _tmp_2 != 0;
          final String _tmpSystemTaskType;
          if (_stmt.isNull(_columnIndexOfSystemTaskType)) {
            _tmpSystemTaskType = null;
          } else {
            _tmpSystemTaskType = _stmt.getText(_columnIndexOfSystemTaskType);
          }
          _item = new TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTimeMillis,_tmpCategory,_tmpCustomCategory,_tmpPriority,_tmpRecurrenceType,_tmpSelectedDaysCsv,_tmpIsCompleted,_tmpCompletedAtMillis,_tmpCreatedAtMillis,_tmpUpdatedAtMillis,_tmpNotificationEnabled,_tmpTaskDate,_tmpIsSystemTask,_tmpSystemTaskType);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object markTaskCompleted(final long taskId, final long completedAtMillis,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE tasks SET isCompleted = 1, completedAtMillis = ?, updatedAtMillis = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, completedAtMillis);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, completedAtMillis);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, taskId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object markTaskNotCompleted(final long taskId, final long updatedAtMillis,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE tasks SET isCompleted = 0, completedAtMillis = NULL, updatedAtMillis = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, updatedAtMillis);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, taskId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
