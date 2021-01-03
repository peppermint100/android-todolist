package com.example.roompractice

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.roompractice.databinding.TodoItemBinding

class TodoRecyclerViewHolder(
    todoRecyclerViewInterface: TodoRecyclerViewInterface,
    binding: TodoItemBinding)
    : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {
    private val TAG: String = "로그"
    private val todoText = binding.todoText
    private val deleteButton = binding.deleteButton
    private var todoRecyclerViewInterface: TodoRecyclerViewInterface? = null
    lateinit var todo: TodoEntity

    init {
        deleteButton.setOnClickListener(this)
        this.todoRecyclerViewInterface = todoRecyclerViewInterface
    }

    fun bind(todoEntity: TodoEntity){
        todoText.text = todoEntity.todo
        todo = todoEntity
    }

    override fun onClick(v: View?) {
        Log.d(TAG, "TodoRecyclerViewHolder - onClick: $adapterPosition");
        this.todoRecyclerViewInterface?.onDeleteButtonClicked(todo)
    }
}