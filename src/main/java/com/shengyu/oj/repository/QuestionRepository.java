package com.shengyu.oj.repository;

import com.shengyu.oj.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findById(long id);
}
