package com.example.core.repository;

import com.example.core.entity.Area;
import com.example.core.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> ,AreaRepositoryCustom{
    Boolean existsByAreaCode(String areaCode);
}
