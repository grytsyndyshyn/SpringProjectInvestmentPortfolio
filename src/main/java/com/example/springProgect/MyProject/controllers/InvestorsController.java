package com.example.springProgect.MyProject.controllers;

import com.example.springProgect.MyProject.util.InvestorValidator;
import jakarta.validation.Valid;
import com.example.springProgect.MyProject.models.Investor;
import com.example.springProgect.MyProject.services.InvestorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class InvestorsController {

    private final InvestorsService investorsService;
    private final InvestorValidator investorValidator;

    @Autowired
    public InvestorsController(InvestorsService investorsService, InvestorValidator investorValidator) {
        this.investorsService = investorsService;
        this.investorValidator = investorValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("investors", investorsService.findAll());
        return "investors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("investor", investorsService.findOne(id));
        model.addAttribute("securities", investorsService.getSecuritiesByInvestorId(id));

        return "investors/show";
    }

    @GetMapping("/new")
    public String newInvestor(@ModelAttribute("investor") Investor investor) {
        return "investors/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("investor") @Valid Investor investor,
                         BindingResult bindingResult) {
        investorValidator.validate(investor, bindingResult);

        if (bindingResult.hasErrors())
            return "investors/new";

        investorsService.save(investor);
        return "redirect:/investors";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("investor", investorsService.findOne(id));
        return "investors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("investor") @Valid Investor investor, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "investors/edit";

        investorsService.update(id, investor);
        return "redirect:/investors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        investorsService.delete(id);
        return "redirect:/investors";
    }
}
