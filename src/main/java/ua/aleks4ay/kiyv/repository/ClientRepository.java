package ua.aleks4ay.kiyv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.aleks4ay.kiyv.domain.model.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByKod(String kod);
}
