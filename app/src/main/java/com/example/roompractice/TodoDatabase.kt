package com.example.roompractice

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [TodoEntity::class])
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase?{
            if(INSTANCE == null){
                synchronized(TodoDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }
    }
}