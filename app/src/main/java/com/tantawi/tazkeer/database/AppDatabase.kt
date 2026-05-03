package com.tantawi.tazkeer.database

import android.content.Context
import androidx.room.Database
import androidx.room.migration.Migration
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

// AppDatabase gives the app one shared Room database instance.
@Database(
    entities = [TaskEntity::class, PrayerTimeEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun prayerDao(): PrayerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tazkeer_database"
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE tasks ADD COLUMN taskDate TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE tasks ADD COLUMN isSystemTask INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE tasks ADD COLUMN systemTaskType TEXT")
                db.execSQL("UPDATE tasks SET category = 'Others' WHERE category = 'Other'")
                db.execSQL("UPDATE tasks SET category = 'Prayer' WHERE category = 'Prayers'")
                db.execSQL("UPDATE tasks SET category = 'Fitness' WHERE category = 'Gym'")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("UPDATE tasks SET category = 'Others' WHERE category = 'Other'")
                db.execSQL("UPDATE tasks SET category = 'Prayer' WHERE category = 'Prayers'")
                db.execSQL("UPDATE tasks SET category = 'Fitness' WHERE category = 'Gym'")
            }
        }
    }
}
