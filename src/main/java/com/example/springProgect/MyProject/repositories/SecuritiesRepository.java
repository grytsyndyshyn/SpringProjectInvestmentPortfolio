package com.example.springProgect.MyProject.repositories;

import com.example.springProgect.MyProject.models.Securities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecuritiesRepository extends JpaRepository<Securities, Integer> {
    List<Securities> findByKindStartingWith(String kind);
}