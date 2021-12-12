package com.ead.authuser.user.api.controllers;

import com.ead.authuser.user.api.dtos.InstructorDto;
import com.ead.authuser.user.domain.enums.UserType;
import com.ead.authuser.user.domain.model.User;
import com.ead.authuser.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    UserService service;

    @PostMapping("subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto){
        Optional<User> userOptional = service.findById(instructorDto.getUserId());

        if(userOptional.isPresent()){
            var user = userOptional.get();
            user.setUserType(UserType.INSTRUCTOR);
            service.save(user);

            return ResponseEntity.ok(user);

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

    }
}
