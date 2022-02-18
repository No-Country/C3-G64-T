package com.getpass.Getpass.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.getpass.Getpass.entidades.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

}
