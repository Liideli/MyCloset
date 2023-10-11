package com.example.mycloset.DatabaseWorkingset

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var instance: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ProductDatabase {
            return Room.databaseBuilder(context, ProductDatabase::class.java, "product-database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

class DatabaseManager(context: Context) {
    private val database = ProductDatabase.getInstance(context)

    suspend fun insertProduct(product: ProductEntity) {
        database.productDao().insertProduct(product)
    }
}