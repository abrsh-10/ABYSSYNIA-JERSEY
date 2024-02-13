package com.abyssiniajersey.jersey;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/jersey")
@CrossOrigin("*")
public class JerseyController {

    private final JerseyService jerseyService;
    @GetMapping()
    public ResponseEntity<List<Jersey>> getJerseys(){
        return ResponseEntity.ok(jerseyService.getJerseys());
    }
    @PostMapping()
    public ResponseEntity<Map<String,Object>> addJersey(@RequestBody NewJersey newJersey){
        Map<String,Object> response = new HashMap<>();
        if(Objects.equals(jerseyService.addJersey(newJersey), "added")){
            response.put("message","created");
            return ResponseEntity.ok(response);
        }
        response.put("message","error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/best-sellers")
    public ResponseEntity<List<Jersey>> getBestSellers(){
        return ResponseEntity.ok(jerseyService.getBestSellers());
    }

}
