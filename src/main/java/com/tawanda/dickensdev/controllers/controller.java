package com.tawanda.dickensdev.controllers;

import com.tawanda.dickensdev.model.StoriesInfo;
import com.tawanda.dickensdev.model.userInfo;
import com.tawanda.dickensdev.service.StoriesService;
import com.tawanda.dickensdev.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


@Controller
public class controller {
    private com.tawanda.dickensdev.service.userService userService;
    private StoriesService storiesService;
    private Random random = null;
    @Autowired
    public controller(userService userService, StoriesService storiesService){
        this.userService = userService;
        this.storiesService = storiesService;

    }

    @GetMapping("/")
    public String index(Model model){
            random = new Random();
           int rand = random.nextInt(storiesService.getAllStories().size());
            model.addAttribute("StoriesHeading",storiesService.getAllStories().get(rand).getHeading());
            model.addAttribute("StoriesBody",storiesService.getAllStories().get(rand).getBody());
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }




}
