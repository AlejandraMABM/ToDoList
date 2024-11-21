package com.example.todolist.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.TaskAdapter
import com.example.todolist.data.Task
import com.example.todolist.data.providers.TaskDAO
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: TaskAdapter

    lateinit var taskDAO: TaskDAO

    var taskList: MutableList<Task> = mutableListOf()


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


        adapter = TaskAdapter(taskList, {
            //Editar Tarea
            val task = taskList[it]
            println("mostrar tarea")
            showTask(task)
        }, {
                //Marcar tarea
                val task = taskList[it]
                checkTask(task)
            }, {
                // Borrar tarea
                val task = taskList[it]
            println("eliminar tarea")
                deleteTask(task)
            })


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Crear  tarea
        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onResume() {
        super.onResume()

        // Cargamos la lista por si se hubiera añadido una tarea nueva
        taskList = taskDAO.findAll().toMutableList()
        adapter.updateItems(taskList)
    }

    // Funcion para cuando marcamos una tarea (finalizada/pendiente)
    fun checkTask(task: Task)
    {
        task.done = !task.done
        taskDAO.update(task)
        adapter.updateItems(taskList)
    }

    // Funciona para mostrar un dialogo para borrar la tarea
    fun deleteTask(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Borrar Tarea")
            .setMessage("Estás seguro de que quieres borrar la tarea")
            .setPositiveButton(android.R.string.ok) {
                dialog, which ->
                // Borramos la tarea en caso de pulsar el botón ok
                taskDAO.delete(task)
                taskList.remove(task)
                adapter.updateItems(taskList)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(R.drawable.ic_delete)
            .show()

    }

    //Mostramos la tarea para editarla funciona correctamente

    fun showTask(task: Task) {
        val intent = Intent(this,TaskActivity::class.java)
        intent.putExtra(TaskActivity.EXTRA_TASK_ID,task.id)
        startActivity(intent)

    }
}