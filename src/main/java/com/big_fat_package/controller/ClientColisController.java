package com.big_fat_package.controller;

import com.big_fat_package.model.Colis;
import com.big_fat_package.service.ColisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClientColisController {

    private final static String PAGES_FOLDER = "pages/";
    private final ColisService colisService;

    @Autowired
    public ClientColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    @RequestMapping(value = {"/client"}, method = RequestMethod.GET)
    public ModelAndView colis() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PAGES_FOLDER + "client_colis_page");
        return modelAndView;
    }

    @RequestMapping(value = {"/client/search"}, method = RequestMethod.POST)
    public ModelAndView editColis(Colis colis, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Colis target_coli = colisService.findByIdColis(colis.getIdColis());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PAGES_FOLDER + "client_colis_page");
        if (target_coli == null){
            modelAndView.addObject("errorMessage", "Ereur il n'y a pas de colis avec cette id !");
        }else {
            modelAndView.addObject("coli", target_coli);
        }
        return modelAndView;
    }
}