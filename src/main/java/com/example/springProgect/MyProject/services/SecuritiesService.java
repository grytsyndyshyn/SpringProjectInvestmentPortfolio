package com.example.springProgect.MyProject.services;

import com.example.springProgect.MyProject.models.Investor;
import com.example.springProgect.MyProject.models.Securities;
import com.example.springProgect.MyProject.repositories.SecuritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SecuritiesService {
    private final SecuritiesRepository securitiesRepository;

    @Autowired
    public SecuritiesService(SecuritiesRepository securitiesRepository) {
        this.securitiesRepository = securitiesRepository;
    }

    public List<Securities> findAll(boolean sortByIssuer) {
        if (sortByIssuer)
            return securitiesRepository.findAll(Sort.by("issuer"));
        else
            return securitiesRepository.findAll();
    }

    public List<Securities> findWithPagination(Integer page, Integer securitiesPerPage, boolean sortByIssuer) {
        if (sortByIssuer)
            return securitiesRepository.findAll(PageRequest.of(page, securitiesPerPage, Sort.by("issuer"))).getContent();
        else
            return securitiesRepository.findAll(PageRequest.of(page, securitiesPerPage)).getContent();
    }

    public Securities findOne(int id) {
        Optional<Securities> foundSecurities = securitiesRepository.findById(id);
        return foundSecurities.orElse(null);
    }

    public List<Securities> searchByIssuer(String query) {
        return securitiesRepository.findByKindStartingWith(query);
    }

    @Transactional
    public void save(Securities securities) {
        securitiesRepository.save(securities);
    }

    @Transactional
    public void update(int id, Securities updatedSecurities) {
        Securities securitiesToBeUpdated = securitiesRepository.findById(id).get();


        updatedSecurities.setId(id);
        updatedSecurities.setOwner(securitiesToBeUpdated.getOwner());

        securitiesRepository.save(updatedSecurities);
    }

    @Transactional
    public void delete(int id) {
        securitiesRepository.deleteById(id);
    }


    public Investor getSecuritiesOwner(int id) {

        return securitiesRepository.findById(id).map(Securities::getOwner).orElse(null);
    }


    @Transactional
    public void release(int id) {
        securitiesRepository.findById(id).ifPresent(
                securities -> {
                    securities.setOwner(null);
                    securities.setTakenAt(null);
                });
    }


    @Transactional
    public void assign(int id, Investor selectedInvestor) {
        securitiesRepository.findById(id).ifPresent(
                securities -> {
                    securities.setOwner(selectedInvestor);
                    securities.setTakenAt(new Date());
                }
        );
    }
}
