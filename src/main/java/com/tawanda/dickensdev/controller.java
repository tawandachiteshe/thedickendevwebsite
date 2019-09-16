package com.tawanda.dickensdev;

import com.tawanda.dickensdev.model.userInfo;
import com.tawanda.dickensdev.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class controller {
    private com.tawanda.dickensdev.service.userService userService;
    @Autowired
    public controller(userService userService){
        this.userService = userService;

    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("welcome","The dickens Dev");
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
