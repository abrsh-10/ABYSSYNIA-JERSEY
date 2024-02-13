package com.abyssiniajersey.user;

import com.abyssiniajersey.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(value = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("customUserAuthenticationManager")
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    //Login
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody LoginForm loginForm){
        try {
            System.out.println(loginForm.getUsername() + loginForm.getPassword());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
            String username = authentication.getName();
            System.out.println(username);

            // get the user to extract the id
            User user1 = userService.getUserByUsername(username);

            if(!loginForm.getUserType().equals(user1.getUserType())){
                Map<String,Object> errorRes = new HashMap<>();
                errorRes.put("httpResponse",HttpStatus.BAD_REQUEST);
                if(loginForm.getUserType().equals(UserType.WORKER)){
                    errorRes.put("message","Dear owner you should not login as a worker");
                }
                else {
                    errorRes.put("message", "You are not allowed to login as an owner");
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
            }

            User user = User.builder().
                    id(user1.getId()).
                    username(username).
                    email(user1.getEmail()).
                    firstName(user1.getFirstName()).
                    lastName(user1.getLastName()).
                    userType(user1.getUserType()).
                    build();
            String token = jwtUtil.createToken(user);
            Map<String,Object> loginRes = new HashMap<>();
            loginRes.put("username",username);
            loginRes.put("token",token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            Map<String,Object> errorRes = new HashMap<>();
            errorRes.put("httpResponse",HttpStatus.BAD_REQUEST);
            errorRes.put("message","Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }catch (Exception e){
            Map<String,Object> errorRes = new HashMap<>();
            errorRes.put("httpResponse",HttpStatus.BAD_REQUEST);
            errorRes.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }
//        Map<String, Object> response = new HashMap<>();
//        switch (userService.login(loginForm)) {
//            case "user" -> {
//                response.put("message", "user not found");
//                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//            }
//            case "invalid user" -> {
//                response.put("message", "you are not allowed to enter");
//                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//            }
//            case "password" -> {
//                response.put("message", "password incorrect");
//                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//            }
//        }
//        String token = userService.login(loginForm);
//        response.put("token",token);
//        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //Create user
    @PostMapping
//    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        String status = userService.createUser(user);
        switch (status) {
            case "phone" -> {
                response.put("message","Phone number already registered");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            case "added" -> {
                response.put("message","Employee created successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        response.put("message","Unknown error, BUG");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Get users
    @GetMapping
//    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }

    //Get workers
    @GetMapping("/workers/{page}")
//    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<User>> getWorkers(@PathVariable("page") int page) {
        return new ResponseEntity<>(userService.getWorkers(page),HttpStatus.OK);
    }

    //Get pages
    @GetMapping("/getPages")
//    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<Map<String, Object>> getPages() {
        Map<String, Object> response = new HashMap<>();
        response.put("pages", userService.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }

    //GET user by email
    @GetMapping("/phone/{phone}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String phone){
        return new ResponseEntity<>(userService.getUserByUsername(phone),HttpStatus.OK);
    }

    //Update user
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable("id") String id,
            @RequestBody User newUser
    ) {
        String result = userService.updateUser(id, newUser);
        Map<String, Object> response = new HashMap<>();

        if (result.equals("not-exist")) {
            response.put("message", "User doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("message", "Employee updated successfully");
        return ResponseEntity.ok(response);
    }

    //Change password
    @PutMapping("change-password/{phone}/{oldPassword}/{newPassword}")
    public ResponseEntity<Map<String,Object>> changePassword(@PathVariable String phone,@PathVariable String oldPassword,@PathVariable String newPassword){
        Map<String,Object> response = new HashMap<>();
        if(userService.changePassword(phone,oldPassword,newPassword).equals("password")){
            response.put("message","Old password not correct");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        else if(userService.changePassword(phone,oldPassword,newPassword).equals("user")){
            response.put("message","Phone number doesn't exist");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        else{
            response.put("message","Password changed successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

   //Forgot password
    @PostMapping("/forgot-password/{phoneNumber}")
    public void forgotPassword(@PathVariable String phoneNumber) {
        System.out.println(userService.forgotPassword(phoneNumber));
    }

    @PostMapping("/reset-password/{phoneNumber}")
//    @PreAuthorize("hasAuthority('OWNER')")
    public void resetPassword(@PathVariable String phoneNumber) {
        System.out.println(userService.resetPassword(phoneNumber));
    }

    //Delete user
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<Map<String,Object>> deleteUser(@PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        if(Objects.equals(userService.deleteUser(id), "non-exist")){
            response.put("message","User doesn't exist");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("message","Employee deleted successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
