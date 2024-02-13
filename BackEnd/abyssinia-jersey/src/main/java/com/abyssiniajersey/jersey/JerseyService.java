package com.abyssiniajersey.jersey;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JerseyService {
    private final JerseyRepository jerseyRepository;
    public List<Jersey> getJerseys(){
        return jerseyRepository.findAll();
    }
    public String addJersey(NewJersey newJersey){
        String jerseyName = newJersey.getInitialYear() + "/" + newJersey.getFinalYear() + " " + newJersey.getClub() + " " + newJersey.getKit().toString().charAt(0)+ newJersey.getKit().toString().substring(1).toLowerCase()+ " Jersey";
        String SKU =newJersey.getInitialYear() + "-" + newJersey.getFinalYear() + "-" + newJersey.getClub().toUpperCase() + "-" + newJersey.getKit()+ "-JERSEY";
        Jersey jersey = Jersey.builder()
                .club(newJersey.getClub())
                .kit(newJersey.getKit())
                .name(jerseyName)
                .categories(newJersey.getCategories())
                .currentPrice(newJersey.getCurrentPrice())
                .initialPrice(newJersey.getInitialPrice())
                .size(newJersey.getSize())
                .description(newJersey.getDescription())
                .year(newJersey.getInitialYear()+"/"+newJersey.getFinalYear())
                .rating(5)
                .imageUrl(newJersey.getImageUrl())
                .inStock(true)
                .quantity(newJersey.getQuantity())
                .SKU(SKU)
                .build();
        jerseyRepository.save(jersey);
        return "added";
    }

    public List<Jersey> getBestSellers(){
        int page = 1;
        List<Jersey> jerseys = jerseyRepository.findAll();
        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Jersey> jerseyPage = jerseyRepository.findAll(pageable);

        if (!jerseyPage.hasContent()) {
            return null;
        }

        return jerseyPage.getContent();
    }
}
