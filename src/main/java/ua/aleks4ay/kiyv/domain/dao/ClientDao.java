package ua.aleks4ay.kiyv.domain.dao;

import ua.aleks4ay.kiyv.domain.model.Client;

import java.util.List;

public interface ClientDao {

    boolean save(Client client);

    boolean delete(String kod);

    Client getByKod(String kod);

    List<Client> getAll();

    boolean saveAll(List<Client> clientList);

    boolean updateAll(List<Client> clientList);

    boolean isBlocking();
    void setBlock();
    void setUnblock();

}
