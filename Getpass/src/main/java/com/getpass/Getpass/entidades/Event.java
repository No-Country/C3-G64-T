package com.getpass.Getpass.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import com.getpass.Getpass.enums.Genre;

@Entity
public class Event {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String eventName;
	private Double price;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String location;
	
	@Enumerated(EnumType.STRING)
	private Genre genre;
	
	private String description;
	private Integer ticketLimit;
	private Boolean enabled;
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ticket> ticketList;
	
	@OneToOne
	private EventImage eventImage;
	
	public Event() {
		this.enabled = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTicketLimit() {
		return ticketLimit;
	}

	public void setTicketLimit(Integer ticketLimit) {
		this.ticketLimit = ticketLimit;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean condition) {
		this.enabled = condition;
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	public EventImage getEventImage() {
		return eventImage;
	}

	public void setEventImage(EventImage eventImage) {
		this.eventImage = eventImage;
	}
	
	

}
