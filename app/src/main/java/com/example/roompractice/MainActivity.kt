package com.example.roompractice

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roompractice.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), TodoRecyclerViewInterface {
    private val TAG: String = "로그"
    private lateinit var binding: ActivityMainBinding;
    lateinit var todoRecyclerViewAdapter: TodoRecyclerViewAdapter;

    val todoList: ArrayList<TodoModel> = ArrayList<TodoModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MainActivity - onCreate: ");
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this@MainActivity

        todoRecyclerViewAdapter = TodoRecyclerViewAdapter(this, todoList, this)
        binding.myRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = todoRecyclerViewAdapter
        }


        binding.mainLayout.setOnTouchListener{ _, _ ->
            hideSoftInput()
            true
        }

        binding.myRecyclerView.setOnTouchListener{ _, _ ->
            hideSoftInput()
            true
        }

        todoRecyclerViewAdapter.loadTodos()
    }

    fun addTodo(){
        val inputText: String = binding.todoInput.text.toString()
        if(inputText.isEmpty()){
            var emptyInputAlert = AlertDialog.Builder(this)
            emptyInputAlert.setMessage("빈 칸을 채워주세요.")
            emptyInputAlert.setPositiveButton("확인",null)
            emptyInputAlert.show()
            return
        }
        Log.d(TAG, "MainActivity - addTodo button clicked! input is $inputText");

        todoRecyclerViewAdapter.addTodo(TodoModel(inputText))
        binding.todoInput.text.clear()

        hideSoftInput()
    }

    private fun hideSoftInput() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.todoInput.windowToken, 0)
    }

    override fun onDeleteButtonClicked(id: Int) {
        Log.d(TAG, "MainActivity - onDeleteButtonClicked: ");
        todoRecyclerViewAdapter.deleteTodo(id)
    }
}