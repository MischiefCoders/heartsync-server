package com.heartsync.todo

import com.heartsync.todo.entity.Todo
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todos")
class TodoController(val todoService: TodoService) {

    @GetMapping("")
    fun getAllTodos(): List<Todo> {
        return todoService.getTodos()
    }

    @GetMapping("/{id}")
    fun getTodo(@PathVariable id: Long): Todo {
        val todo = todoService.getTodo(id)

        return todo
    }

    @PostMapping("")
    fun addTodo(@RequestBody todo: Todo) {
        todoService.addTodo(todo)
    }

    @PutMapping("/{id}")
    fun changeCompletionStatus(@RequestBody isCompleted: Boolean, @PathVariable id: Long) {
        todoService.changeCompletionStatus(id, isCompleted)
    }

    @PutMapping("")
    fun updateTodo(@RequestBody todo: Todo) {
        todoService.updateTodo(todo)
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long) {
        todoService.deleteTodo(id)
    }
}