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
public class OperatorColisController {

    private final static String PAGES_FOLDER = "pages/";
    private final ColisService colisService;

    @Autowired
    public OperatorColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    @RequestMapping(value = {"/operator"}, method = RequestMethod.GET)
    public ModelAndView colis() {
        System.out.println("yolo");
        List<Colis> listColis = colisService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lesColis", listColis);
        modelAndView.addObject("linkEdit", "/operator/colis/edit");

        modelAndView.setViewName(PAGES_FOLDER + "operator_colis_page");
        return modelAndView;
    }

    @RequestMapping(value = {"/operator/colis/edit"}, method = RequestMethod.POST)
    public String editColis(Colis colis, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "redirect:/operator/";
        }
        Colis target_coli = colisService.findByIdColis(colis.getIdColis());
        target_coli.setLatitude(colis.getLatitude());
        target_coli.setLongitude(colis.getLongitude());
        target_coli.setEmplacement(colis.getEmplacement());
        target_coli.setEtat(colis.getEtat());
        colisService.editColis(target_coli);
        redirectAttributes.addAttribute("successMessage", "Le colis a été editer.");
        return "redirect:/operator/";
    }
}