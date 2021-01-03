package com.example.roompractice

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roompractice.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity(), TodoRecyclerViewInterface {
    private val TAG: String = "로그"
    private lateinit var binding: ActivityMainBinding;

    lateinit var db: TodoDatabase
    lateinit var todoRecyclerViewAdapter: TodoRecyclerViewAdapter;
    var todoList: List<TodoEntity> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MainActivity - onCreate: ");
        super.onCreate(savedInstanceState)

        db = TodoDatabase.getInstance(this)!!

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this@MainActivity

        hideKeyboardOnTouchOutside()
        loadTodos()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun hideKeyboardOnTouchOutside(){
        binding.mainLayout.setOnTouchListener{ _, _ ->
            hideSoftInput()
            true
        }

        binding.myRecyclerView.setOnTouchListener{ _, _ ->
            hideSoftInput()
            true
        }
    }

    fun loadTodos(){
        val getTask = object: AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                todoList = db.todoDao().getAllTodos()
                Log.d(TAG, "MainActivity - doInBackground: $todoList ");
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(todoList)
            }
        }

        getTask.execute()
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

        val insertTask =
        object: AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                val todo = TodoEntity(null, inputText)
                db.todoDao().insertTodo(todo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                loadTodos()
            }
        }
        insertTask.execute()

        binding.todoInput.text.clear()
        hideSoftInput()
    }

    override fun onDeleteButtonClicked(todo: TodoEntity) {
        val deleteTask = object: AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.todoDao().delete(todo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                loadTodos()
            }
        }

        deleteTask.execute()
    }

    fun setRecyclerView(todoList: List<TodoEntity>){
        todoRecyclerViewAdapter = TodoRecyclerViewAdapter(this, todoList, this)
        binding.myRecyclerView.apply {
            layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = todoRecyclerViewAdapter
        }
    }

    private fun hideSoftInput() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.todoInput.windowToken, 0)
    }
}