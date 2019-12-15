package com.shengyu.oj.repository;

import com.shengyu.oj.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByQidOrderByTimeAsc(@Param("Qid") Long qid);
}
