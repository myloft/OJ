package com.shengyu.oj.controller;

import com.shengyu.oj.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public ModelAndView question() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("questions", questionRepository.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        modelAndView.addObject("username", username);
        modelAndView.setViewName("question");
        return modelAndView;
    }
}
