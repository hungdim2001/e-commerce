package com.example.core.repository;

import com.example.core.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressByUserId(Long userId);
    @Query(value ="update addresses set is_default =false where user_id = ?1", nativeQuery = true)
    void updateDefault(Long userId);
}
