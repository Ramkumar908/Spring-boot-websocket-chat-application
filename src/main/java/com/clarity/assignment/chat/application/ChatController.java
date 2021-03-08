package com.clarity.assignment.chat.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clarity.assignment.chat.application.Model.User;
import com.clarity.assignment.chat.application.repo.UserRepository;
@Controller
public class ChatController {
	
	@Autowired
	UserRepository repo;
	
	// testing url:http://localhost:8080/
	@RequestMapping(value="/login",method= {RequestMethod.GET,RequestMethod.POST})
	public String  userRegister(User user, Model  model)
	{
		System.out.println("username is"+user.getUsername()+" and  password is  "+user.getUserpassword());
		User newUser=new User();
		model.addAttribute("username",user.getUsername());
	    if(user.getUsername().equals(null)|| user.getUserpassword().equals(null))
	    {
	    	return "index";
	    }
	    try
	    {
	    newUser=repo.findByUsername(user.getUsername());
	    if(newUser==null)
	    {
	    	repo.save(user);
	    	model.addAttribute("errorMessage","New User Data Saved in Database please login again");
	    	 return "index";
	    }
	    }catch(NullPointerException e)
	    {
	    	System.out.println("hello Enter ");
	    	System.out.println("User Successfully saved");
	    	//return "User Not Exist by Username"+user.getUsername();
	    }
	   // System.out.println("newUser is"+newUser);
		//System.out.println("newusername is"+newUser.getUsername()+" and  newpassword is  "+newUser.getUserpassword());

	    model.addAttribute("successMessage", "Successfully login please enter any username and start chating");
	    return "mypage";
	}
	
	@MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessagePojo sendMessage(@Payload ChatMessagePojo chatMessagePojo) {
        return chatMessagePojo;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessagePojo addUser(@Payload ChatMessagePojo chatMessagePojo, SimpMessageHeaderAccessor headerAccessor) {
        

    headerAccessor.getSessionAttributes().put("username", chatMessagePojo.getSender());
        return chatMessagePojo;
    }

}
