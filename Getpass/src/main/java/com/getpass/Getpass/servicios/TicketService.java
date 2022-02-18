package com.getpass.Getpass.servicios;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.getpass.Getpass.entidades.Event;
import com.getpass.Getpass.entidades.Ticket;
import com.getpass.Getpass.entidades.Usuario;
import com.getpass.Getpass.excepciones.ErrorService;
import com.getpass.Getpass.repositorio.TicketRepository;

@Service
public class TicketService {
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserService userService;
	
	@Transactional
	public void createTicket(String eventId, String userId) throws ErrorService {
		
		if (eventId == null || userId == null || eventId.isEmpty() || userId.isEmpty()) {
			throw new ErrorService("El evento o usuario no tiene ID");
		}
		
		Ticket ticket = new Ticket();
		
		Event event = eventService.eventId(eventId);
		
		if (event.getTicketLimit() <= event.getTicketList().size()) {
			throw new ErrorService("Se alcanzo el limite de los ticket");
		}
		
		Usuario user = userService.userId(userId);
		
		ticket.setEvent(event);
		ticket.setUser(user);
		
		ticketRepository.save(ticket);
		
	}
	
	@Transactional
	public void delete(String id) throws ErrorService {

		Ticket ticket = ticketId(id);
		ticketRepository.delete(ticket);
	}
	
	@Transactional(readOnly = true)
	public Ticket ticketId(String id) throws ErrorService {
		Optional<Ticket> result = ticketRepository.findById(id);
		
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new ErrorService("No se pudo encontrar el ticket");
		}
	}

}
