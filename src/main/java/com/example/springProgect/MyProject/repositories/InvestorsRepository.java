package com.example.springProgect.MyProject.repositories;

import com.example.springProgect.MyProject.models.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorsRepository extends JpaRepository<Investor, Integer> {
    Optional<Investor> findByLastName(String lastName);


}
