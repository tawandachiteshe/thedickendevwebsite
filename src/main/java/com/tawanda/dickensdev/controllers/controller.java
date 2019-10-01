package com.tawanda.dickensdev.controllers;

import com.tawanda.dickensdev.model.StoriesInfo;
import com.tawanda.dickensdev.model.userInfo;
import com.tawanda.dickensdev.service.StoriesService;
import com.tawanda.dickensdev.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Controller
public class controller {
    private com.tawanda.dickensdev.service.userService userService;
    private StoriesService storiesService;
    private Random random = null;
    private String name;
    @Autowired
    public controller(userService userService, StoriesService storiesService){
        this.userService = userService;
        this.storiesService = storiesService;

    }

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
   @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("login",new userInfo());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute userInfo info,Model model){
        if(userService.confirmationVerifier(info)){
            return "index";
        }else {
            model.addAttribute("confirmationMessage", "please confirm your account");
            return "login";
        }
    }





}
