package com.example.mycloset.DatabaseWorkingset

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Room Database class responsible for managing the database and providing access to DAOs.
@Database(entities = [ProductEntity::class], version = 3, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    // Abstract method to retrieve the ProductDao, which provides access to database operations.
    abstract fun productDao(): ProductDao

    // Companion object, which contains methods for creating and accessing the database instance.
    companion object {
        @Volatile
        private var instance: ProductDatabase? = null

        // Singleton pattern to ensure there's only one instance of the database.
        fun getInstance(context: Context): ProductDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Creates and returns a new instance of the database.
        private fun buildDatabase(context: Context): ProductDatabase {
            return Room.databaseBuilder(context, ProductDatabase::class.java, "product-database")
                .fallbackToDestructiveMigration() // Allows destructive migration (deleting old data) in case of version upgrade.
                .build()
        }
    }
}
