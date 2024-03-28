package com.heartsync.todo

import com.heartsync.todo.entity.Todo
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.security.InvalidParameterException
import java.util.*

@SpringBootTest
@ExtendWith(MockKExtension::class)
class TodoServiceTest(
    @MockK
    var mockRepository: TodoRepository,

    @InjectMockKs
    var todoService: TodoService
) {

    private val todo = Todo("test")

    @Test
    fun addTodo() {
        // given
        every { mockRepository.save(any()) } returns todo

        // when
        val saved = todoService.addTodo(todo)

        // then
        assertThat(saved.todoContent).isEqualTo(todo.todoContent)
        assertThat(saved.isCompleted).isFalse()
    }

    @Test
    fun getTodo() {
        // given
        every { mockRepository.findById(any()) } returns Optional.of(todo)

        // when
        val result = todoService.getTodo(1L)

        // then
        assertThat(result.todoContent).isEqualTo(todo.todoContent)
    }

    @Test
    fun getTodoThrowsException() {
        // given
        every { mockRepository.findById(any()) } returns Optional.empty()

        assertThatThrownBy { todoService.getTodo(todo.todoId) }
            .isInstanceOf(InvalidParameterException::class.java)
            .hasMessage("존재하지 않는 todo")
    }

    @Test
    fun getTodos() {
        // given
        val todo2 = Todo("test2")
        every { mockRepository.findAll() } returns listOf(todo, todo2)

        // when
        val todos = todoService.getTodos()

        // then
        assertThat(todos).hasSize(2)
        assertThat(todos).extracting("todoContent").contains("test", "test2")
    }

    @Test
    fun updateTodo() {
        // given
        every { mockRepository.findById(any()) } returns Optional.of(todo)
        every { mockRepository.save(any()) } returns todo

        // when
        todo.todoContent = "updated"
        val updated = todoService.updateTodo(todo)

        // then
        assertThat(updated.todoId).isEqualTo(todo.todoId)
        assertThat(updated.todoContent).isEqualTo(todo.todoContent)
    }

    @Test
    fun updateTodoThrowsException() {
        // given
        every { mockRepository.findById(any()) } returns Optional.empty()

        // when / then
        assertThatThrownBy { todoService.updateTodo(todo) }
            .isInstanceOf(InvalidParameterException::class.java)
            .hasMessage("존재하지 않는 todo")
    }

    @Test
    fun deleteTodoThrowsException() {
        // given
        every { mockRepository.findById(any()) } returns Optional.empty()

        // when / then
        assertThatThrownBy { todoService.deleteTodo(todo.todoId) }
            .isInstanceOf(InvalidParameterException::class.java)
            .hasMessage("존재하지 않는 todo")
    }


}