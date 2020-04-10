package ua.aleks4ay.kiyv.domain.services;

import ua.aleks4ay.kiyv.domain.model.Client;

import java.util.List;

public interface ClientService {

    Client save(Client client);

    boolean delete(int id);

//    Client update(Client order);

    Client getOne(int id);

    List<Client> getAll();

    boolean saveAll(List<Client> clientList);

}
