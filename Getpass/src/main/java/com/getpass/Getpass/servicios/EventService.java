package com.getpass.Getpass.servicios;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.getpass.Getpass.entidades.Event;
import com.getpass.Getpass.entidades.EventImage;
import com.getpass.Getpass.enums.Genre;
import com.getpass.Getpass.excepciones.ErrorService;
import com.getpass.Getpass.repositorio.EventRepository;

@Service
public class EventService {

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventImageService eventImageService;
	
	@Transactional
	public void createEvent(String eventName, Double price, Date date, String location, Genre genre, String description, 
			Integer ticketLimit, MultipartFile file) throws ErrorService {
		
		check(eventName, price, date, location, genre, description, ticketLimit, file);
		
		Event event = new Event();
		
		EventImage eventImage = eventImageService.uploadPhoto(null, file);
		
		event.setEventName(eventName);
		event.setPrice(price);
		event.setDate(date);
		event.setLocation(location);
		event.setGenre(genre);
		event.setDescription(description);
		event.setTicketLimit(ticketLimit);
		event.setEventImage(eventImage);
		
		eventRepository.save(event);
	}
	
	@Transactional
	public void delete(String id) throws ErrorService {

		Event event = eventId(id);
		eventRepository.delete(event);
	}
	
	@Transactional(readOnly = true)
	public Event eventId(String id) throws ErrorService {
		Optional<Event> result = eventRepository.findById(id);
		
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new ErrorService("No se pudo encontrar el evento");
		}
	}
	
	@Transactional
	public void changeCondition(String id) throws ErrorService {
		Event event = eventId(id);
		
		event.setEnabled(!event.getEnabled());
	}
	
	@Transactional
	public void edit(String id, String eventName, Double price, Date date, String location, Genre genre, String description, 
			Integer ticketLimit, MultipartFile file) throws ErrorService {
		
		check(eventName, price, date, location, genre, description, ticketLimit, file);
		
		Event event = eventId(id);
		
		event.setEventName(eventName);
		event.setPrice(price);
		event.setDate(date);
		event.setLocation(location);
		event.setGenre(genre);
		event.setDescription(description);
		event.setTicketLimit(ticketLimit);
		
		if (file != null && file.getSize() != 0) {
			EventImage eventImage = eventImageService.uploadPhoto(event.getEventImage().getId(), file);
			event.setEventImage(eventImage);
		}
		
		eventRepository.save(event);
		
	}
	
	public void check(String eventName, Double price, Date date, String location, Genre genre, String description, 
			Integer ticketLimit, MultipartFile file) throws ErrorService {
		
		if (eventName == null || eventName.isEmpty()) {
			throw new ErrorService("Error en el evento: esta vacio.");
		}

		if (price == null || price <= 0) {
			throw new ErrorService("Error en el precio: esta vacio o es negativa.");
		}

		if (date== null || date.before(new Date())) {
			throw new ErrorService("Error en el fecha: esta vacio o es anterior a la fecha actual.");
		}

		if (location == null || location.isEmpty()) {
			throw new ErrorService("Error en el localidad: esta vacio.");
		}

		if (genre == null) {
			throw new ErrorService("Error en el genero: esta vacio.");
		}

		if (description == null || description.isEmpty()) {
			throw new ErrorService("Error la descripcion: esta vacia.");
		}

		if (ticketLimit == null || ticketLimit <= 0) {
			throw new ErrorService("Error en la cantidad de ticket: Es 0 o menor a 0.");
		}

		if (file == null) {
			throw new ErrorService("Error en la foto: No se adjunto ninguna.");
		}
		
	}
	
}
