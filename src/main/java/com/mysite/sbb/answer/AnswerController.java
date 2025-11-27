package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable  Integer id,
                               @RequestParam(value = "content") String content) {
        Question q = this.questionService.getQuestion(id);
        this.answerService.create(q,content);
        return "redirect:/question/detail/%s".formatted(id);
    }



}

