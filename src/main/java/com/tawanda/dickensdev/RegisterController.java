package com.tawanda.dickensdev;

import com.tawanda.dickensdev.model.userInfo;
import com.tawanda.dickensdev.service.EmailService;
import com.tawanda.dickensdev.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
public class RegisterController {

    private Pbkdf2PasswordEncoder pbkdf2PasswordEncoder;
    private com.tawanda.dickensdev.service.userService UserService;
    private EmailService emailService;

    @Autowired
    public RegisterController(Pbkdf2PasswordEncoder pbkdf2PasswordEncoder, userService UserService, EmailService emailService) {

        this.pbkdf2PasswordEncoder = pbkdf2PasswordEncoder;
        this.UserService = UserService;
        this.emailService = emailService;
    }

    // Return registration form template
    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("userInfo",new userInfo());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegister(@ModelAttribute userInfo userInfo){
        UserService.registerUser(userInfo);
        return "login";
    }


}