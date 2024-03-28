package com.heartsync.todo

import com.heartsync.todo.entity.Todo
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.security.InvalidParameterException

@Service
@RequiredArgsConstructor
class TodoService(val todoRepository: TodoRepository) {

    fun addTodo(todo: Todo): Todo {
        val saved = todoRepository.save(todo)
        return saved
    }

    fun getTodo(id: Long): Todo {
        val todo = todoRepository.findById(id)
            .orElseThrow { throw InvalidParameterException("존재하지 않는 todo") }

        return todo
    }

    fun getTodos(): List<Todo> {
        return todoRepository.findAll()
    }

    fun updateTodo(todo: Todo): Todo {
        todoRepository.findById(todo.todoId)
            .orElseThrow { throw InvalidParameterException("존재하지 않는 todo") }

        return todoRepository.save(todo)
    }

    fun changeCompletionStatus(id: Long, isCompleted: Boolean) {
        val todo = todoRepository.findById(id)
            .orElseThrow { throw InvalidParameterException("존재하지 않는 todo") }

        todo.isCompleted = isCompleted
        todoRepository.save(todo)
    }

    fun deleteTodo(id: Long) {
        val todo = todoRepository.findById(id)
            .orElseThrow { throw InvalidParameterException("존재하지 않는 todo") }

        todoRepository.delete(todo)
    }
}