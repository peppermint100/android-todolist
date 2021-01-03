package com.example.roompractice

import androidx.room.*

@Dao
interface TodoDao {
    // query
    @Query("SELECT * FROM todo")
    fun getAllTodos(): List<TodoEntity>

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: TodoEntity)

//     delete
    @Delete
    fun delete(todo: TodoEntity)
}