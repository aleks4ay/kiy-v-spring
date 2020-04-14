package ua.aleks4ay.kiyv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.aleks4ay.kiyv.domain.model.Client;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean deleteById(String id);
    Client findById(String id);
}
