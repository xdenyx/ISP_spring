package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.firstName LIKE %:keyword% OR c.lastName LIKE %:keyword% OR c.email LIKE %:keyword% OR c.phoneNumber LIKE %:keyword% OR c.address LIKE %:keyword%")
    Page<Client> search(@Param("keyword") String keyword, Pageable pageable);
}
