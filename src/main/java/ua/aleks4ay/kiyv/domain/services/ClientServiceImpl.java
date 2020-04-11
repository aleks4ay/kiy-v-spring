package ua.aleks4ay.kiyv.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.aleks4ay.kiyv.domain.model.Client;
import ua.aleks4ay.kiyv.repository.ClientRepository;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    @Resource
    private ClientRepository clientRepository;

    @Override
    public Client getOne(int id) {
        Client client = clientRepository.findById(id).orElse(null);
        return client;
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public Client save(Client newClient) {
        Client clientFromDb = clientRepository.findByKod(newClient.getId());
        if (clientFromDb == null) {
            return clientRepository.save(newClient);
        }
        else if (! clientFromDb.equals(newClient) ) {
            clientFromDb.setClientName(newClient.getClientName());
            clientRepository.flush();
            return clientFromDb;
        }
        return newClient;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        if (clientRepository.findById(id) != null) {
            clientRepository.deleteById(id);
            if (getOne(id) == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean saveAll(List<Client> clientList) {
        for (Client client : clientList) {
            save(client);
        }
        return true;
    }

    /*    @Override
    @Transactional
    public Client update(Client client) {
        Client oldClient = clientRepository.findByKod(client.getKod());
        if ( oldClient != null) {
            oldClient.setClientName(client.getClientName());
            clientRepository.flush();
            return oldClient;
        }
        return null;
    }*/
}


