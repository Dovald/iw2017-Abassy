package com.abassy.tables;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Long> {
	List<Local> findByDireccionStartsWithIgnoreCase(String direccion);
}