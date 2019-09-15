package com.tawanda.dickensdev;

import com.tawanda.dickensdev.model.userInfo;
import com.tawanda.dickensdev.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/userinfo")
public class RestController {
    private final userService userService;
    @Autowired
    public RestController(com.tawanda.dickensdev.service.userService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<userInfo> getallinfo(){
        return userService.getAllpeople();
    }

    @PostMapping
    public void addUser(@RequestBody userInfo userInfo){
        userService.insertUser(userInfo);
    }
}
