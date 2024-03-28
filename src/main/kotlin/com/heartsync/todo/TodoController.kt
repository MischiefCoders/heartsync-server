package com.heartsync.todo

import com.heartsync.todo.entity.Todo
import org.springframework.http.ResponseEntity
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
    fun getAllTodos(): ResponseEntity<List<Todo>> {
        val todos = todoService.getTodos()

        return ResponseEntity.ok(todos)
    }

    @GetMapping("/{id}")
    fun getTodo(@PathVariable id: Long): ResponseEntity<Todo> {
        return ResponseEntity.ok(todoService.getTodo(id))
    }

    @PostMapping("")
    fun addTodo(@RequestBody todo: Todo): ResponseEntity<Any> {
        todoService.addTodo(todo)

        return ResponseEntity.ok().build()
    }

    @PutMapping("/{id}")
    fun changeCompletionStatus(@RequestBody isCompleted: Boolean, @PathVariable id: Long): ResponseEntity<Any> {
        todoService.changeCompletionStatus(id, isCompleted)

        return ResponseEntity.ok().build()
    }

    @PutMapping("")
    fun updateTodo(@RequestBody todo: Todo): ResponseEntity<Any> {
        todoService.updateTodo(todo)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Any> {
        todoService.deleteTodo(id)

        return ResponseEntity.ok().build()
    }
}