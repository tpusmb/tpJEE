package com.big_fat_package.controller;

import com.big_fat_package.model.Colis;
import com.big_fat_package.service.ColisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ColisController {

    private final static String PAGES_FOLDER = "pages/dashboard/";
    private final ColisService colisService;

    @Autowired
    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    private Authentication getAuthenticatedUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = {"/colis/**"}, method = RequestMethod.GET)
    public ModelAndView colis() {
        List<Colis> listColis = colisService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lesColis", listColis);
        modelAndView.setViewName(PAGES_FOLDER + "colis_page");
        return modelAndView;
    }

    @RequestMapping(value = {"/colis/create"}, method = RequestMethod.POST)
    public String createColis(@Valid Colis colis, RedirectAttributes redirectAttributes) {

        colisService.saveColis(colis);
        redirectAttributes.addAttribute("successMessage", "Le colis a été ajouté.");
        return "redirect:/colis/";
    }
}