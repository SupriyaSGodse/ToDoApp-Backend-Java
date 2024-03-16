package com.example.todo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.exceptions.ResourceNotFoundException;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{

	private TodoRepository todoRepository;
	
	private ModelMapper modelMapper;
	
	@Override
	public TodoDto addTodo(TodoDto todoDto) {
		
//		Todo todo= new Todo();
//		todo.setTitle(todoDto.getTitle());
//		todo.setDescription(todoDto.getDescription());
//		todo.setCompleted(todoDto.isCompleted());
		
		Todo todo=modelMapper.map(todoDto, Todo.class);
		Todo savedTodo= todoRepository.save(todo);
		
//		TodoDto savedTodoDto =new TodoDto();
//		savedTodoDto.setId(savedTodo.getId());
//		savedTodoDto.setDescription(savedTodo.getDescription());
//		savedTodoDto.setTitle(savedTodo.getTitle());
//		savedTodoDto.setCompleted(savedTodo.isCompleted());
		
		TodoDto savedTodoDto=modelMapper.map(savedTodo, TodoDto.class);
		return savedTodoDto;
	}

	@Override
	public TodoDto getTodo(long id) {
		
		Todo todo= todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id :" +id));
		
		
		return modelMapper.map(todo, TodoDto.class);
	}

	@Override
	public List<TodoDto> getAllTodos() {
		
		List<Todo> todos= todoRepository.findAll();
		
		return todos.stream().map((todo)-> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
	}

	@Override
	public TodoDto updateTodo(TodoDto todoDto, long id) {
		
		Todo todo=todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id :"+id));
		
		todo.setTitle(todoDto.getTitle());
		todo.setDescription(todoDto.getDescription());
		todo.setCompleted(todoDto.isCompleted());
		
		Todo savedTodo= todoRepository.save(todo);
		return modelMapper.map(savedTodo, TodoDto.class);
	}

	@Override
	public void deleteTodo(long id) {
		// TODO Auto-generated method stub

		Todo todo=todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id :"+id));
		
		todoRepository.deleteById(id);
	}

	@Override
	public TodoDto completeTodo(long id) {
		
		Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+id));
		todo.setCompleted(Boolean.TRUE);
		Todo todoupdated = todoRepository.save(todo);
		
		return modelMapper.map(todoupdated, TodoDto.class);
	}

	public TodoDto incompleteTodo(long id) {

		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));
		todo.setCompleted(Boolean.FALSE);
		Todo todoupdated = todoRepository.save(todo);

		return modelMapper.map(todoupdated, TodoDto.class);
	}

}
