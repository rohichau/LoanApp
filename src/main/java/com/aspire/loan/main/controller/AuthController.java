//package com.aspire.loan.main.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aspire.loan.main.model.User;
//import com.aspire.loan.main.service.UserService;
//import com.aspire.loan.main.util.JwtTokenUtil;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("/auth")
//@Api(tags = "Authentication")
//public class AuthController {
//
//    private final UserService userService;
//    private final JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    public AuthController(UserService userService, JwtTokenUtil jwtTokenUtil) {
//        this.userService = userService;
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
//    @PostMapping("/register")
////    @ApiOperation("Register a new user")
//    public ResponseEntity<String> registerUser(@RequestBody User user) {
//        userService.registerUser(user);
//        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
//    }
//
//    @PostMapping("/login")
////    @ApiOperation("User login")
//    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
//        if (userService.authenticateUser(email, password)) {
//            String token = jwtTokenUtil.generateToken(email);
//            return new ResponseEntity<>(token, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
//        }
//    }
//}
