package com.pneedle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pneedle.model.User;
import com.pneedle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JsonController {

    private final UserService userService;

    @Autowired
    public JsonController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getCompanyResponseBody(@RequestParam("q") String input) {
        List<User> users = userService.findAll();
        List<String> users_string = new ArrayList<>();
        for (User user:users) {
            users_string.add(user.getEmail());
        }
        users_string = getStrings(users_string, input);
        ObjectMapper mapper = new ObjectMapper();
        String resp = "";
        try {
            resp = mapper.writeValueAsString(users_string);
        } catch (JsonProcessingException e) {
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    public static List<String> getStrings(List<String> users, final String input) {
        return users.stream().filter(s -> s.toLowerCase().contains(input.toLowerCase())).collect(Collectors.toList());
    }
}
