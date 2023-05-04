package com.zenika.skyjo.interfaces.repository;

import com.zenika.skyjo.domain.Manche;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MancheRepository extends MongoRepository<Manche, String> {
}