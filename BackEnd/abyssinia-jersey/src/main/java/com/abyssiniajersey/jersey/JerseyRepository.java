package com.abyssiniajersey.jersey;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JerseyRepository extends MongoRepository<Jersey,String> {
}
