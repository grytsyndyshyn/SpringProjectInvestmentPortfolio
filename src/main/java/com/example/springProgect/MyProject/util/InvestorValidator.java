package com.example.springProgect.MyProject.util;

import com.example.springProgect.MyProject.models.Investor;
import com.example.springProgect.MyProject.services.InvestorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class InvestorValidator implements Validator{
    private final InvestorsService investorsService;

    @Autowired
    public InvestorValidator(InvestorsService investorsService) {
        this.investorsService = investorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Investor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Investor investor = (Investor) o;

        if (investorsService.getInvestorByLastName(investor.getLastName()).isPresent())
            errors.rejectValue("lastName", "", "Такий користувач уже існує");
    }
}
