package com.heartsync.todo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Todo(
    @Column(name = "todo_content")
    var todoContent: String,

    @Column(name = "is_completed")
    var isCompleted: Boolean = false
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    val todoId: Long = 0L

}