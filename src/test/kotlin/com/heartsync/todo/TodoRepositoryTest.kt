package com.heartsync.todo

import com.heartsync.todo.entity.Todo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class TodoRepositoryTest(
    @Autowired
    val todoRepository: TodoRepository
) {

    private val todo = Todo("test")

    @AfterEach
    fun tearDown() {
        todoRepository.deleteAllInBatch()
    }

    @Test
    fun saveTodo() {
        // when
        val saved = todoRepository.save(todo)

        // then
        assertThat(saved.todoId).isEqualTo(todo.todoId)
        assertThat(saved.todoContent).isEqualTo(todo.todoContent)
        assertThat(saved.isCompleted).isFalse()
    }

    @Test
    fun findTodo() {
        // when
        val saved = todoRepository.save(todo)

        // then
        assertThat(saved.todoId).isEqualTo(saved.todoId)
        assertThat(saved.todoContent).isEqualTo(saved.todoContent)
    }

    @Test
    fun findAllTodos() {
        // given
        val todo2 = Todo("test2")
        val todo3 = Todo("test3")

        // when
        val saved = todoRepository.saveAll(listOf(todo, todo2, todo3))

        // then
        assertThat(saved).hasSize(3)
    }

    @Test
    fun updateTodo() {
        // when
        val saved = todoRepository.save(todo)
        saved.todoContent = "updated"

        val updated = todoRepository.save(saved)

        // then
        assertThat(updated.todoId).isEqualTo(saved.todoId)
        assertThat(updated.todoContent).isEqualTo("updated")
    }

    @Test
    fun deleteTodo() {
        // when
        val saved = todoRepository.save(todo)
        todoRepository.delete(saved)

        val notExists = todoRepository.findById(saved.todoId)

        // then
        assertThat(notExists).isEmpty
    }
}