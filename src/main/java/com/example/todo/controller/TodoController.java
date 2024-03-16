package com.example.todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("api/todo")
@AllArgsConstructor
public class TodoController {

	private TodoService todoService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
		
		TodoDto Savedtodo= todoService.addTodo(todoDto);
		
		return new ResponseEntity<>(Savedtodo, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("{id}")
	public ResponseEntity<TodoDto> getTodo(@PathVariable long id){
		
		TodoDto todoDto = todoService.getTodo(id);
		
		return ResponseEntity.ok(todoDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping()
	public ResponseEntity<List<TodoDto>> getAllTodo(){
		List<TodoDto> todoDto = todoService.getAllTodos();
		
		return ResponseEntity.ok(todoDto) ;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("{id}")
	public ResponseEntity<TodoDto> updateTodo(@PathVariable long id, @RequestBody TodoDto todoDto){
		
		TodoDto	todoDtoUpdated= todoService.updateTodo(todoDto, id);
		
		return ResponseEntity.ok(todoDtoUpdated);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable long id){
		  todoService.deleteTodo(id);
		 return ResponseEntity.ok("Todo Deleted Successfully!!!");
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PatchMapping("{id}/complete")
	public ResponseEntity<TodoDto> completeTodo(@PathVariable long id){
		TodoDto todoDto= todoService.completeTodo(id);
		
		return ResponseEntity.ok(todoDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PatchMapping("{id}/in-complete")
	public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable long id){
		TodoDto todoDto= todoService.incompleteTodo(id);
		
		return ResponseEntity.ok(todoDto);
	}
}
