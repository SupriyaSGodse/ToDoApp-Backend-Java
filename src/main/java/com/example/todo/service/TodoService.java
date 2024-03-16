package com.example.todo.service;

import java.util.List;

import com.example.todo.dto.TodoDto;

public interface TodoService {

	TodoDto addTodo(TodoDto todoDto);
	
	TodoDto getTodo(long id);
	
	List<TodoDto> getAllTodos();
	
	TodoDto updateTodo(TodoDto todoDto, long id);
	
	void deleteTodo(long id);
	
	TodoDto completeTodo(long id);
	
	TodoDto incompleteTodo(long id);
}
