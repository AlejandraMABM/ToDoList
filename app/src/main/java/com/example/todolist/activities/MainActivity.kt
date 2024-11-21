package com.example.todolist.activities

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.TaskAdapter
import com.example.todolist.data.Task
import com.example.todolist.data.providers.TaskDAO
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.ActivityTaskBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: TaskAdapter

    lateinit var taskDAO: TaskDAO

    var taskList: List<Task> = mutableListOf()

    private fun checkTask(task: Task): Any {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        taskDAO = TaskDAO(this)

        /*taskDAO.insert(Task(-1, "Comprar leche"))
     taskDAO.insert(Task(-1, "Pagar el alquiler"))
     taskDAO.insert(Task(-1, "Pasear al perro"))*/

        adapter = TaskAdapter(taskList, {
            //Editar Tarea
            val task = taskList[it]
            showTask(task) },
            {
                //Marcar tarea
            val task = taskList[it]
            checkTask(task)
        }, {
            // Borrar tarea
            val task = taskList[it]
                deleteTask(task)
            })


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showTask(task: Task) {

    }

    override fun OnResume() {
        super.onResume()

        taskList = taskDAO.findAll()

        adapter.uodateItems(taskList)
    }
}