package com.mysite.sbb.question;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void list() throws Exception {
        mockMvc.perform(get("/question/list"))
        .andExpect(status().isOk())
                .andExpect(view().name("question_list"))
                .andExpect(model().attribute("questions", Matchers.hasSize(2)));

    }

    @Test
    void detail() throws Exception {
        Question q1 = new Question();
        q1.setSubject("subject1");
        q1.setContent("content1");
        q1.setCreateDate(LocalDateTime.now());
        q1.addAnswer("answer1-1");

        mockMvc.perform(get("/question/detail/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("question_detail"))
                .andExpect(model().attribute("question",
                        Matchers.allOf(
                                Matchers.hasProperty("subject", Matchers.is("subject1")),
                                Matchers.hasProperty("answers", Matchers.hasSize(1))
                        )
                ));
    }

    @Test
    void questionCreate() throws Exception {
        mockMvc.perform(post("/question/create")
                .param("subject","newSubject")
                .param("content","newContent")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/question/list"));
        Optional<Question> questionOptional = this.questionRepository.findBySubject("newSubject");
        assertTrue(questionOptional.isPresent());
        Question q = questionOptional.get();
        assertEquals("newContent", q.getContent());

    }

    @Test
    void testQuestionCreate() {
    }
}