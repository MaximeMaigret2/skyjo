package com.zenika.skyjo.domain.common;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartieRepository extends MongoRepository<Partie, Long> {
}