package com.example.springProgect.MyProject.services;

import com.example.springProgect.MyProject.models.Investor;
import com.example.springProgect.MyProject.models.Securities;
import com.example.springProgect.MyProject.repositories.InvestorsRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class InvestorsService {
    private final InvestorsRepository investorsRepository;

    @Autowired
    public InvestorsService(InvestorsRepository investorsRepository) {
        this.investorsRepository = investorsRepository;
    }

    public List<Investor> findAll() {
        return investorsRepository.findAll();
    }

    public Investor findOne(int id) {
        Optional<Investor> foundInvestor = investorsRepository.findById(id);
        return foundInvestor.orElse(null);
    }

    @Transactional
    public void save(Investor investor) {
        investorsRepository.save(investor);
    }

    @Transactional
    public void update(int id, Investor updatedInvestor) {
        updatedInvestor.setId(id);
        investorsRepository.save(updatedInvestor);
    }

    @Transactional
    public void delete(int id) {
        investorsRepository.deleteById(id);
    }

    public Optional<Investor> getInvestorByLastName(String lastName) {
        return investorsRepository.findByLastName(lastName);
    }

    public List<Securities> getSecuritiesByInvestorId(int id) {
        Optional<Investor> investor = investorsRepository.findById(id);

        if (investor.isPresent()) {
            Hibernate.initialize(investor.get().getSecurities());
            investor.get().getSecurities().forEach(securities -> {
                long diffInMillies = Math.abs(securities.getTakenAt().getTime() - new Date().getTime());

                if (diffInMillies > 864000000)
                    securities.setExpired(true);
            });

            return investor.get().getSecurities();
        }
        else {
            return Collections.emptyList();
        }
    }
}
