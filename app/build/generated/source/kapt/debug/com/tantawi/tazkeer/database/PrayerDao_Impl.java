package com.tantawi.tazkeer.database;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class PrayerDao_Impl implements PrayerDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<PrayerTimeEntity> __insertAdapterOfPrayerTimeEntity;

  public PrayerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfPrayerTimeEntity = new EntityInsertAdapter<PrayerTimeEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `prayer_times` (`id`,`date`,`latitude`,`longitude`,`fajr`,`dhuhr`,`asr`,`maghrib`,`isha`,`sunrise`,`fetchedAtMillis`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final PrayerTimeEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getDate() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getDate());
        }
        statement.bindDouble(3, entity.getLatitude());
        statement.bindDouble(4, entity.getLongitude());
        if (entity.getFajr() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getFajr());
        }
        if (entity.getDhuhr() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getDhuhr());
        }
        if (entity.getAsr() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getAsr());
        }
        if (entity.getMaghrib() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getMaghrib());
        }
        if (entity.getIsha() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getIsha());
        }
        if (entity.getSunrise() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getSunrise());
        }
        statement.bindLong(11, entity.getFetchedAtMillis());
      }
    };
  }

  @Override
  public Object insertOrUpdatePrayerTimes(final PrayerTimeEntity prayerTime,
      final Continuation<? super Unit> $completion) {
    if (prayerTime == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfPrayerTimeEntity.insert(_connection, prayerTime);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getPrayerTimesByDate(final String date, final double latitude,
      final double longitude, final Continuation<? super PrayerTimeEntity> $completion) {
    final String _sql = "SELECT * FROM prayer_times WHERE date = ? AND latitude = ? AND longitude = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (date == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, date);
        }
        _argIndex = 2;
        _stmt.bindDouble(_argIndex, latitude);
        _argIndex = 3;
        _stmt.bindDouble(_argIndex, longitude);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "date");
        final int _columnIndexOfLatitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "latitude");
        final int _columnIndexOfLongitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "longitude");
        final int _columnIndexOfFajr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fajr");
        final int _columnIndexOfDhuhr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dhuhr");
        final int _columnIndexOfAsr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "asr");
        final int _columnIndexOfMaghrib = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maghrib");
        final int _columnIndexOfIsha = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isha");
        final int _columnIndexOfSunrise = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sunrise");
        final int _columnIndexOfFetchedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fetchedAtMillis");
        final PrayerTimeEntity _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpDate;
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmpDate = null;
          } else {
            _tmpDate = _stmt.getText(_columnIndexOfDate);
          }
          final double _tmpLatitude;
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude);
          final double _tmpLongitude;
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude);
          final String _tmpFajr;
          if (_stmt.isNull(_columnIndexOfFajr)) {
            _tmpFajr = null;
          } else {
            _tmpFajr = _stmt.getText(_columnIndexOfFajr);
          }
          final String _tmpDhuhr;
          if (_stmt.isNull(_columnIndexOfDhuhr)) {
            _tmpDhuhr = null;
          } else {
            _tmpDhuhr = _stmt.getText(_columnIndexOfDhuhr);
          }
          final String _tmpAsr;
          if (_stmt.isNull(_columnIndexOfAsr)) {
            _tmpAsr = null;
          } else {
            _tmpAsr = _stmt.getText(_columnIndexOfAsr);
          }
          final String _tmpMaghrib;
          if (_stmt.isNull(_columnIndexOfMaghrib)) {
            _tmpMaghrib = null;
          } else {
            _tmpMaghrib = _stmt.getText(_columnIndexOfMaghrib);
          }
          final String _tmpIsha;
          if (_stmt.isNull(_columnIndexOfIsha)) {
            _tmpIsha = null;
          } else {
            _tmpIsha = _stmt.getText(_columnIndexOfIsha);
          }
          final String _tmpSunrise;
          if (_stmt.isNull(_columnIndexOfSunrise)) {
            _tmpSunrise = null;
          } else {
            _tmpSunrise = _stmt.getText(_columnIndexOfSunrise);
          }
          final long _tmpFetchedAtMillis;
          _tmpFetchedAtMillis = _stmt.getLong(_columnIndexOfFetchedAtMillis);
          _result = new PrayerTimeEntity(_tmpId,_tmpDate,_tmpLatitude,_tmpLongitude,_tmpFajr,_tmpDhuhr,_tmpAsr,_tmpMaghrib,_tmpIsha,_tmpSunrise,_tmpFetchedAtMillis);
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
  public Object getLatestPrayerTimes(final Continuation<? super PrayerTimeEntity> $completion) {
    final String _sql = "SELECT * FROM prayer_times ORDER BY fetchedAtMillis DESC LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "date");
        final int _columnIndexOfLatitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "latitude");
        final int _columnIndexOfLongitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "longitude");
        final int _columnIndexOfFajr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fajr");
        final int _columnIndexOfDhuhr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dhuhr");
        final int _columnIndexOfAsr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "asr");
        final int _columnIndexOfMaghrib = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maghrib");
        final int _columnIndexOfIsha = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isha");
        final int _columnIndexOfSunrise = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sunrise");
        final int _columnIndexOfFetchedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fetchedAtMillis");
        final PrayerTimeEntity _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpDate;
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmpDate = null;
          } else {
            _tmpDate = _stmt.getText(_columnIndexOfDate);
          }
          final double _tmpLatitude;
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude);
          final double _tmpLongitude;
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude);
          final String _tmpFajr;
          if (_stmt.isNull(_columnIndexOfFajr)) {
            _tmpFajr = null;
          } else {
            _tmpFajr = _stmt.getText(_columnIndexOfFajr);
          }
          final String _tmpDhuhr;
          if (_stmt.isNull(_columnIndexOfDhuhr)) {
            _tmpDhuhr = null;
          } else {
            _tmpDhuhr = _stmt.getText(_columnIndexOfDhuhr);
          }
          final String _tmpAsr;
          if (_stmt.isNull(_columnIndexOfAsr)) {
            _tmpAsr = null;
          } else {
            _tmpAsr = _stmt.getText(_columnIndexOfAsr);
          }
          final String _tmpMaghrib;
          if (_stmt.isNull(_columnIndexOfMaghrib)) {
            _tmpMaghrib = null;
          } else {
            _tmpMaghrib = _stmt.getText(_columnIndexOfMaghrib);
          }
          final String _tmpIsha;
          if (_stmt.isNull(_columnIndexOfIsha)) {
            _tmpIsha = null;
          } else {
            _tmpIsha = _stmt.getText(_columnIndexOfIsha);
          }
          final String _tmpSunrise;
          if (_stmt.isNull(_columnIndexOfSunrise)) {
            _tmpSunrise = null;
          } else {
            _tmpSunrise = _stmt.getText(_columnIndexOfSunrise);
          }
          final long _tmpFetchedAtMillis;
          _tmpFetchedAtMillis = _stmt.getLong(_columnIndexOfFetchedAtMillis);
          _result = new PrayerTimeEntity(_tmpId,_tmpDate,_tmpLatitude,_tmpLongitude,_tmpFajr,_tmpDhuhr,_tmpAsr,_tmpMaghrib,_tmpIsha,_tmpSunrise,_tmpFetchedAtMillis);
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
  public Object deleteOldPrayerTimes(final long beforeMillis,
      final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM prayer_times WHERE fetchedAtMillis < ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, beforeMillis);
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
