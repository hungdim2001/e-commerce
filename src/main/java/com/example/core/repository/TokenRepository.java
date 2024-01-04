package com.example.core.repository;

import com.example.core.entity.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    @Modifying
    @Transactional
    @Query(value = "update Token t set t.token =?1 where t.token = ?2")
    void updateToken(String newToken,String oldToken);
    @Modifying
    @Transactional
    @Query(value = "delete from Token t where t.token = ?1")
    void removeToken(String token);

    boolean existsByToken(String oldToken);
    @Query(value = " \n" +
            "SELECT *\n" +
            "FROM (\n" +
            "         SELECT *, ROW_NUMBER() OVER (ORDER BY id DESC) AS rn\n" +
            "         FROM tokens\n" +
            "     ) AS subquery\n" +
            "WHERE rn = 1;", nativeQuery = true)
    Token getToken();
}
