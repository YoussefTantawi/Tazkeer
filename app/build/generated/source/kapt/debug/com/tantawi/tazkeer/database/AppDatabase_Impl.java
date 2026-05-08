package com.tantawi.tazkeer.database;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile TaskDao _taskDao;

  private volatile PrayerDao _prayerDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(4, "4ddfcf16194ff3b51a088a42cd36de35", "0c387253d8ecc4f9e73b5ebf8549f6ed") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `tasks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `timeMillis` INTEGER NOT NULL, `category` TEXT NOT NULL, `customCategory` TEXT, `priority` TEXT NOT NULL, `recurrenceType` TEXT NOT NULL, `selectedDaysCsv` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, `completedAtMillis` INTEGER, `createdAtMillis` INTEGER NOT NULL, `updatedAtMillis` INTEGER NOT NULL, `notificationEnabled` INTEGER NOT NULL, `taskDate` TEXT NOT NULL, `isSystemTask` INTEGER NOT NULL, `systemTaskType` TEXT)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `prayer_times` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `fajr` TEXT NOT NULL, `dhuhr` TEXT NOT NULL, `asr` TEXT NOT NULL, `maghrib` TEXT NOT NULL, `isha` TEXT NOT NULL, `sunrise` TEXT, `midnight` TEXT, `fetchedAtMillis` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE UNIQUE INDEX IF NOT EXISTS `index_prayer_times_date_latitude_longitude` ON `prayer_times` (`date`, `latitude`, `longitude`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4ddfcf16194ff3b51a088a42cd36de35')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `tasks`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `prayer_times`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsTasks = new HashMap<String, TableInfo.Column>(17);
        _columnsTasks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("timeMillis", new TableInfo.Column("timeMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("customCategory", new TableInfo.Column("customCategory", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("priority", new TableInfo.Column("priority", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("recurrenceType", new TableInfo.Column("recurrenceType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("selectedDaysCsv", new TableInfo.Column("selectedDaysCsv", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("completedAtMillis", new TableInfo.Column("completedAtMillis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("createdAtMillis", new TableInfo.Column("createdAtMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("updatedAtMillis", new TableInfo.Column("updatedAtMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("notificationEnabled", new TableInfo.Column("notificationEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("taskDate", new TableInfo.Column("taskDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("isSystemTask", new TableInfo.Column("isSystemTask", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("systemTaskType", new TableInfo.Column("systemTaskType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysTasks = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesTasks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTasks = new TableInfo("tasks", _columnsTasks, _foreignKeysTasks, _indicesTasks);
        final TableInfo _existingTasks = TableInfo.read(connection, "tasks");
        if (!_infoTasks.equals(_existingTasks)) {
          return new RoomOpenDelegate.ValidationResult(false, "tasks(com.tantawi.tazkeer.database.TaskEntity).\n"
                  + " Expected:\n" + _infoTasks + "\n"
                  + " Found:\n" + _existingTasks);
        }
        final Map<String, TableInfo.Column> _columnsPrayerTimes = new HashMap<String, TableInfo.Column>(12);
        _columnsPrayerTimes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("fajr", new TableInfo.Column("fajr", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("dhuhr", new TableInfo.Column("dhuhr", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("asr", new TableInfo.Column("asr", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("maghrib", new TableInfo.Column("maghrib", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("isha", new TableInfo.Column("isha", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("sunrise", new TableInfo.Column("sunrise", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("midnight", new TableInfo.Column("midnight", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrayerTimes.put("fetchedAtMillis", new TableInfo.Column("fetchedAtMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysPrayerTimes = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesPrayerTimes = new HashSet<TableInfo.Index>(1);
        _indicesPrayerTimes.add(new TableInfo.Index("index_prayer_times_date_latitude_longitude", true, Arrays.asList("date", "latitude", "longitude"), Arrays.asList("ASC", "ASC", "ASC")));
        final TableInfo _infoPrayerTimes = new TableInfo("prayer_times", _columnsPrayerTimes, _foreignKeysPrayerTimes, _indicesPrayerTimes);
        final TableInfo _existingPrayerTimes = TableInfo.read(connection, "prayer_times");
        if (!_infoPrayerTimes.equals(_existingPrayerTimes)) {
          return new RoomOpenDelegate.ValidationResult(false, "prayer_times(com.tantawi.tazkeer.database.PrayerTimeEntity).\n"
                  + " Expected:\n" + _infoPrayerTimes + "\n"
                  + " Found:\n" + _existingPrayerTimes);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tasks", "prayer_times");
  }

  @Override
  public void clearAllTables() {
    super.performClear(false, "tasks", "prayer_times");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TaskDao.class, TaskDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PrayerDao.class, PrayerDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TaskDao taskDao() {
    if (_taskDao != null) {
      return _taskDao;
    } else {
      synchronized(this) {
        if(_taskDao == null) {
          _taskDao = new TaskDao_Impl(this);
        }
        return _taskDao;
      }
    }
  }

  @Override
  public PrayerDao prayerDao() {
    if (_prayerDao != null) {
      return _prayerDao;
    } else {
      synchronized(this) {
        if(_prayerDao == null) {
          _prayerDao = new PrayerDao_Impl(this);
        }
        return _prayerDao;
      }
    }
  }
}
