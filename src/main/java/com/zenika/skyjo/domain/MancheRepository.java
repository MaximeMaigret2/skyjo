package com.zenika.skyjo.domain;

import com.zenika.skyjo.domain.Manche;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MancheRepository extends MongoRepository<Manche, String> {
}