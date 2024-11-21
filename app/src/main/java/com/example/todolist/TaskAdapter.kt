package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todolist.data.Task
import com.example.todolist.databinding.ItemTaskBinding


class TaskAdapter(
    var items: List<Task>,
    val onItemClick: (Int) -> Unit,
    val onItemCheck: (Int) -> Unit,
    val onItemDelete: (Int) -> Unit
) :
    RecyclerView.Adapter<TaskViewHolder>() {

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = items[position]
        holder.render(task)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }

        holder.binding.doneCheckBox.setOnCheckedChangeListener { checkBox, isChecked ->
            if (checkBox.isPressed) {
                onItemCheck(position)
            }
            holder.binding.deleteButton.setOnClickListener {
                onItemDelete(position)
            }

        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val binding =
                ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TaskViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        fun uodateItems(items: List<Task>) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    class TaskViewHolder(val binding: ItemTaskBinding) : ViewHolder(binding.root) {

        fun render(task: Task) {
            binding.nameTextView.text = task.name
            binding.doneCheckBox.isChecked = task.done
        }
    }