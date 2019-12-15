package com.shengyu.oj.controller;

import com.shengyu.oj.model.Grade;
import com.shengyu.oj.model.Question;
import com.shengyu.oj.repository.GradeRepository;
import com.shengyu.oj.repository.JudgeRepository;
import com.shengyu.oj.repository.QuestionRepository;
import com.shengyu.oj.service.ExecuteStringSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.standard.expression.EqualsExpression;

@Controller
public class RunCodeController {
    private Logger logger = LoggerFactory.getLogger(RunCodeController.class);
    private JudgeRepository judge;
    private QuestionRepository question;
    private GradeRepository gradeRepository;
    @Autowired
    private ExecuteStringSourceService executeStringSourceService;

    private static final String defaultSource = "import java.util.*;\n\n" +
            "public class Run {\n"
            + "    public static void main(String[] args) {\n"
            + "        \n"
            + "    }\n"
            + "}";

    public RunCodeController(JudgeRepository judge, GradeRepository gradeRepository, QuestionRepository question) {
        this.judge = judge;
        this.gradeRepository = gradeRepository;
        this.question = question;
    }

    @RequestMapping(value = "/ide/{id}", method = RequestMethod.GET)
    public ModelAndView entry(@PathVariable long id) {
        String des = question.findById(id).getDescription();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lastSource", defaultSource);
        modelAndView.addObject("description",des);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        modelAndView.addObject("username", username);
        modelAndView.setViewName("ide");
        return modelAndView;
    }

    @RequestMapping(value = "/ide/{id}", method = RequestMethod.POST)
    public ModelAndView runCode(@RequestParam("source") String source,
                          @PathVariable long id) {
        String example = judge.findById(id).getExample();
        String res = judge.findById(id).getRes();
        String des = question.findById(id).getDescription();
        String[] runResult = executeStringSourceService.execute(source, example);
        ModelAndView modelAndView = new ModelAndView();
        if (res.equals(runResult[0])) {
            modelAndView.addObject("lastSource", source);
            modelAndView.addObject("description",des);
            modelAndView.addObject("runResult", "通过 用时：" + runResult[1] + "ns");
            modelAndView.addObject("time", runResult[1]);

            Grade grade = new Grade();
            grade.setQid(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            modelAndView.addObject("username", username);
            grade.setUser(authentication.getName());
            grade.setTime(runResult[1]);
            gradeRepository.save(grade);
            modelAndView.setViewName("ide");
            return modelAndView;
        }
        modelAndView.addObject("lastSource", source);
        modelAndView.addObject("description",des);
        modelAndView.addObject("runResult", "错误："+runResult[0]);
        modelAndView.setViewName("ide");
        return modelAndView;
    }
}
