package com.pos.pos.repository;

import com.pos.pos.entity.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends JpaRepository<QuotationEntity, Long> {}