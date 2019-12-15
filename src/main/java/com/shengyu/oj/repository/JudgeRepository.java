package com.shengyu.oj.repository;

import com.shengyu.oj.model.Judge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JudgeRepository extends JpaRepository<Judge, Long> {
    Judge findById(long id);
}
