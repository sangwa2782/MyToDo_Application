package com.ajayam.mytodo.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ajayam.mytodo.data.AppDatabase
import com.ajayam.mytodo.data.dao.TodoDao
import com.ajayam.mytodo.data.entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private var todoDao: TodoDao?
    private var appDatabase: AppDatabase?
    private var list: LiveData<List<Todo>>

    init {
        appDatabase = AppDatabase.getDatabase(application)
        todoDao = appDatabase?.todoDao()
        list = todoDao?.getTodos()!!
    }

    fun getNotes(): LiveData<List<Todo>> = list

    fun store(todoName: String) {
        insertToDatabase(todoName)
    }

    private fun insertToDatabase(todoName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            todoDao?.insert(
                Todo(
                    null,
                    todoName
                )
            )
        }
    }

    fun update(todo: Todo) {
        CoroutineScope(Dispatchers.IO).launch {
            todoDao?.update(todo)
        }
    }

    fun delete(todo: Todo) {
        CoroutineScope(Dispatchers.IO).launch {
            todoDao?.delete(todo)
        }
    }


}