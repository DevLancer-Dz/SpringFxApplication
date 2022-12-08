package com.damine.app.controllers;

import com.damine.app.models.User;
import com.damine.app.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class HomeController implements Initializable {
    @Autowired
    private UserService userService;

    @FXML
    private Label text;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = new User();

        if(userService.getAll().isEmpty()){
            user.setId(1L);
            user.setName("damine");
            user = userService.addUser(user);
            text.setText(user.toString());
        }else{
            Optional<User> user1 = userService.getUser(1L);
            text.setText(user1.toString());
        }
//        User user;
//        if(userService.getAll().isEmpty()){
//            User damine = new User(1, "damine");
//            user = userService.addUser(damine);
//            text.setText(damine.toString());
//        }else{
//            user = userService.getUser(1L).get();
//            text.setText(user.toString());
//        }
    }
}
