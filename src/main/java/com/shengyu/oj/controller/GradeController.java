package com.shengyu.oj.controller;

import com.shengyu.oj.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GradeController {
    @Autowired
    private GradeRepository gradeRepository;

    @RequestMapping(value = "/grade/{qid}", method = RequestMethod.GET)
    public ModelAndView grade(@PathVariable long qid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("grade", gradeRepository.findByQidOrderByTimeAsc(qid));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        modelAndView.addObject("username", username);
        modelAndView.setViewName("grade");
        return modelAndView;
    }
}
