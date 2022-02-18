package com.getpass.Getpass.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.getpass.Getpass.entidades.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

}
