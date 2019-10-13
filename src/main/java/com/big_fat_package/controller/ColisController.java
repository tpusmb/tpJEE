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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ColisController {

    private final static String PAGES_FOLDER = "pages/";
    private final ColisService colisService;

    @Autowired
    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public ModelAndView colis() {
        List<Colis> listColis = colisService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lesColis", listColis);
        modelAndView.addObject("formColi", new Colis());
        modelAndView.setViewName(PAGES_FOLDER + "colis_page");
        return modelAndView;
    }

    @RequestMapping(value = {"/admin/colis/add"}, method = RequestMethod.POST)
    public String createColis(@Valid Colis colis, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()){
            return "redirect:/admin/";
        }

        colisService.saveColis(colis);
        redirectAttributes.addAttribute("successMessage", "Le colis a été ajouté.");
        return "redirect:/admin/";
    }

    @RequestMapping(value = {"/admin/colis/edit"}, method = RequestMethod.POST)
    public String editColis(Colis colis, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()){
            return "redirect:/admin/";
        }
        Colis target_coli = colisService.findByIdColis(colis.getIdColis());
        target_coli.setPoidsColis(colis.getPoidsColis());
        target_coli.setValeurColis(colis.getValeurColis());
        target_coli.setOrigineColis(colis.getOrigineColis());
        target_coli.setDestinationColis(colis.getDestinationColis());
        target_coli.setLatitude(colis.getLatitude());
        target_coli.setLongitude(colis.getLongitude());
        target_coli.setEmplacement(colis.getEmplacement());
        target_coli.setEtat(colis.getEtat());
        colisService.editColis(target_coli);
        redirectAttributes.addAttribute("successMessage", "Le colis a été editer.");
        return "redirect:/admin/";
    }

    @RequestMapping(value = {"/admin/colis/delete"}, method = RequestMethod.DELETE)
    public String deleteEmployee(Colis colis, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            return "redirect:/admin/";
        }
        Colis target_coli = colisService.findByIdColis(colis.getIdColis());
        colisService.deleteColis(target_coli);
        // Send view result
        redirectAttributes.addAttribute("successMessage", "Le colis a été suprimer");
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}