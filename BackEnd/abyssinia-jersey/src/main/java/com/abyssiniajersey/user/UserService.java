package com.abyssiniajersey.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    @Value("${app.secretKey}")
    private String SECRET_KEY;


    //CREATE user
    public String createUser(User user) {

        //attributed that cannot be set by the user
        user.setId(null);
        user.setJoinedOn(LocalDate.now());
        user.setPassword(user.getFirstName() + "123");
        String password = user.getPassword();
        user.setUserType(UserType.WORKER);
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            return "username";

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());

        userRepository.save(user);
        return "added";
    }

    //Login
    public String login(LoginForm loginForm){
        User user = getUserByUsername(loginForm.getUsername());
        if(user!= null){
            if(!user.getUserType().equals(loginForm.getUserType())){
                return "invalid user";
            }
            if(BCrypt.checkpw(loginForm.getPassword(), user.getPassword())){
                // Create claims for the token
                Claims claims = Jwts.claims().setSubject(user.getUsername());
                claims.put("user", user);

                // Generate the token
                String token = Jwts.builder()
                        .setClaims(claims)
                        .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                        .compact();

                return token;
            }
            else{
                return  "password";
            }
        }
        else {
            return "user";
        }
    }

    //GET all users
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    //GET all workers
    public List<User> getWorkers(int page) {
        int pageSize = 8;
        int offset = (page - 1) * pageSize;

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<User> workerPage = userRepository.findByUserType(UserType.WORKER, pageable);

        if (!workerPage.hasContent()) {
            return null;
        }

        return workerPage.getContent();
    }

    //Get pages
    public double getTotalPages(){
        return Math.ceil(userRepository.countByUserType(UserType.WORKER) / 8.0);

    }

    //GET user by ID
    public User getUserById(String id){
        return userRepository.findById(id).orElse(null);
    }

    //GET user by Phone
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    //Update user
    public String updateUser(String id, User newUser){
        User user = getUserById(id);
        if(user == null){
            return "not-exist";
        }
        userRepository.save(user);
        return "updated";
    }

    //Change password
    public String changePassword(String username,String oldPassword,String newPassword){
        User user = getUserByUsername(username);
        if(user != null) {
            if (BCrypt.checkpw(oldPassword,user.getPassword())) {
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                user.setPassword(hashedPassword);
                userRepository.save(user);
                return "changed";
            }
            else{
                return "password";
            }
        }
        return "user";
    }

    public String forgotPassword(String username){
        User user = getUserByUsername(username);
        if(user ==  null){
            return "exists";
        }
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("sisayabrsh1@gmail.com");
            message.setSubject("Test Email");
            message.setText("This is a test email sent from Spring Boot.");

//            javaMailSender.send(message);
        return "true";

    }
    public String resetPassword(String username){
        User user = getUserByUsername(username);
        if(user ==  null){
            return "exists";
        }
        String hashedPassword = BCrypt.hashpw(user.getFirstName()+"123", BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return "changed";
    }

    //Delete user
    public String deleteUser(String id){
        User user = getUserById(id);
        if(user == null){
            return "not-exist";
        }
        userRepository.deleteById(id);
        return "deleted";
    }

    String mapUserToName(String user) {
        if (userRepository.findById(user).isPresent()) {
            return userRepository.findById(user).get().getFirstName();
        }
        return "";
    }
}

