package com.getpass.Getpass.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.getpass.Getpass.entidades.Event;
import com.getpass.Getpass.enums.Genre;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

	@Query("SELECT e FROM Event e WHERE e.eventName LIKE :eventName")
	public Event UserByEventName(@Param("eventName") String eventName);
	
	@Query("SELECT r FROM Event r WHERE LOWER(r.genre) = LOWER(:#{#genre})")
    public List<Event> findByRole(@Param("genre") Genre genre);

}