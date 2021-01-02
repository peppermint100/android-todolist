package com.example.roompractice

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roompractice.databinding.TodoItemBinding
import kotlin.collections.ArrayList

class TodoRecyclerViewAdapter(val context: Context,
                              todoList: ArrayList<TodoModel>,
                              todoRecyclerViewInterface: TodoRecyclerViewInterface)
    : RecyclerView.Adapter<TodoRecyclerViewHolder>() {

    private var todoList: ArrayList<TodoModel> = todoList;
    private val TAG: String = "로그"
    private var todoRecyclerViewInterface: TodoRecyclerViewInterface? = null

    init {
        this.todoRecyclerViewInterface = todoRecyclerViewInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoRecyclerViewHolder {
        Log.d(TAG, "TodoRecyclerViewAdapter - onCreateViewHolder: ");
        return TodoRecyclerViewHolder(
            todoRecyclerViewInterface!!,
            TodoItemBinding.inflate(LayoutInflater.from(context),
            parent,
            false))
    }

    override fun getItemCount() = todoList.size

    override fun onBindViewHolder(holder: TodoRecyclerViewHolder, position: Int) {
        Log.d(TAG, "TodoRecyclerViewAdapter - onBindViewHolder: ");

        holder.bind(todoList[position])
    }

    fun loadTodos(){
        for(i:Int in 0..10){
            val todo = TodoModel("$i 번째 할 일")
            todoList.add(todo)
        }
    }

    fun addTodo(todo: TodoModel){
        todoList.add(todo)
        notifyItemInserted(todoList.size - 1)
    }

    fun deleteTodo(position: Int){
        todoList.removeAt(position)
        notifyItemRemoved(position)
    }
}