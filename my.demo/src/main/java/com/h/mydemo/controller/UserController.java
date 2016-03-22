package com.h.mydemo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.h.mydemo.model.User;

@Controller
@RequestMapping(value = "/user") 
public class UserController {

	@RequestMapping(value="test")
	public String test(Model model){
		List<User> userList = new ArrayList<User>(){ };
		return "/jsp/test";
	}
	
	@RequestMapping(value="testAjax")
	@ResponseBody
	public Map<Long,User> testAjax(){
		Map<Long,User> userMap = new HashMap<Long,User>(){
			private static final long serialVersionUID = -8070051304940464909L;
			{
				put(1L, new User("a",15));
				put(2L, new User("b",16));
				put(3L, new User("c",17));
				put(4L, new User("d",18));
				put(5L, new User("e",19));
			}
		};
		return userMap;
	}
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 10000000; i++) {
			if(i%5 == 31)
				System.out.println(i);
			
		}
	}
	
}
