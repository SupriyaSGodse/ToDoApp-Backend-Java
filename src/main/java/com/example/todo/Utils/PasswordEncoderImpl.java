package com.example.todo.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl {

	public static void main(String[] args) {
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("supriya"));
		System.out.println(passwordEncoder.encode("admin"));
	}
}
