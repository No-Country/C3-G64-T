package com.getpass.Getpass.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.getpass.Getpass.entidades.EventImage;

@Repository
public interface EventImageRepository extends JpaRepository<EventImage, String> {

}
