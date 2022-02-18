package com.getpass.Getpass.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.getpass.Getpass.entidades.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, String> {

	@Query("SELECT u FROM Usuario u WHERE u.email LIKE :email")
	public Usuario UserByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.dni LIKE :dni")
	public Usuario UserByDni(@Param("dni") Integer dni);
	
	@Query("SELECT u FROM Usuario u WHERE u.nick LIKE :nick")
	public Usuario UserByNick(@Param("nick") String nick);
	
}
