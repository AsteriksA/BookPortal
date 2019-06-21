package com.gold.repository;

import com.gold.model.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Long> {

    PublisherEntity findByName(String name);
    void deleteById(Long id);
    Optional<PublisherEntity> findById(Long id);
}
