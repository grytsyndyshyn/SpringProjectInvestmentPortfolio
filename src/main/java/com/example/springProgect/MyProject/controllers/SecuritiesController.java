package com.example.springProgect.MyProject.controllers;

import com.example.springProgect.MyProject.models.Investor;
import com.example.springProgect.MyProject.models.Securities;
import com.example.springProgect.MyProject.services.InvestorsService;
import com.example.springProgect.MyProject.services.SecuritiesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/securities")
public class SecuritiesController {
    private final SecuritiesService securitiesService;
    private final InvestorsService investorsService;

    @Autowired
    public SecuritiesController(SecuritiesService securitiesService, InvestorsService investorsService) {
        this.securitiesService = securitiesService;
        this.investorsService = investorsService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "securities_per_page", required = false) Integer securitiesPerPage,
                        @RequestParam(value = "sort_by_kind", required = false) boolean sortByKind) {

        if (page == null || securitiesPerPage == null)
            model.addAttribute("securities", securitiesService.findAll(sortByKind));
        else
            model.addAttribute("securities", securitiesService.findWithPagination(page, securitiesPerPage, sortByKind));

        return "securities/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("investor") Investor investor) {
        model.addAttribute("securities", securitiesService.findOne(id));

        Investor securitiesOwner = securitiesService.getSecuritiesOwner(id);

        if (securitiesOwner != null)
            model.addAttribute("owner", securitiesOwner);
        else
            model.addAttribute("investors", investorsService.findAll());

        return "securities/show";
    }

    @GetMapping("/new")
    public String newSecurities(@ModelAttribute("share") Securities Securities) {
        return "securities/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("share") @Valid Securities Securities,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "securities/new";

        securitiesService.save(Securities);
        return "redirect:/securities";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("share", securitiesService.findOne(id));
        return "securities/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("share") @Valid Securities securities, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "securities/edit";

        securitiesService.update(id, securities);
        return "redirect:/securities";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        securitiesService.delete(id);
        return "redirect:/securities";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        securitiesService.release(id);
        return "redirect:/securities/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("investor") Investor selectedInvestor) {

        securitiesService.assign(id, selectedInvestor);
        return "redirect:/securities/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "securities/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("securities", securitiesService.searchByIssuer(query));
        return "securities/search";
    }
}



