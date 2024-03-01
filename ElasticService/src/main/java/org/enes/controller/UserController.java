package org.enes.controller;

import lombok.RequiredArgsConstructor;
import org.enes.domain.User;
import org.enes.dto.request.UserRequestDto;
import org.enes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.enes.constants.RestApiUrl.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {
    private final UserService userService;

    @PostMapping(ADD)
    @CrossOrigin("*")
    public ResponseEntity<Void> save(@RequestBody UserRequestDto dto){
        userService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<Void> update(@RequestBody UserRequestDto dto){
        userService.update(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<Iterable<User>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }
}