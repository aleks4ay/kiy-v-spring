package ua.aleks4ay.kiyv.copiller_db.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.kiyv.domain.dao.ClientDao;
import ua.aleks4ay.kiyv.domain.dao.ClientDaoJdbc;
import ua.aleks4ay.kiyv.domain.dao.UtilDao;
import ua.aleks4ay.kiyv.domain.dbf.ClientDbf;
import ua.aleks4ay.kiyv.domain.dbf.ClientDbfReader;
import ua.aleks4ay.kiyv.domain.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class CopyClient {

    private static final CopyClient copyClient = new CopyClient();
    private static final ClientDao clientDao = new ClientDaoJdbc(UtilDao.getConnPostgres());
    private static final ClientDbf clientDbfReader = new ClientDbfReader();
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    private CopyClient() {
    }

    public static CopyClient getInstance() {
        return copyClient;
    }

    public static void main(String[] args) {

        CopyClient.getInstance().doCopyNewRecord();

    }

    public void doCopyNewRecord() {
        long start = System.currentTimeMillis();
        log.info("Start writing 'C L I E N T S'.");

        List<Client> listNewClient = new ArrayList<>();
        List<Client> listUpdatingClient = new ArrayList<>();

        Map<String, String> oldClient = clientDao.getAll()
                .stream()
                .collect(Collectors.toMap(Client::getId, Client::getClientName));

        List<Client> listClientFrom1C = clientDbfReader.getAll();

        for (Client client : listClientFrom1C) {
            if (!oldClient.containsKey(client.getId())) {
                listNewClient.add(client);
            } else if (!oldClient.get(client.getId()).equals(client.getClientName())) {
                listUpdatingClient.add(client);
                oldClient.remove(client.getId());
            }
            else {
                oldClient.remove(client.getId());
            }
        }

        if (listNewClient.size() > 0) {
            log.debug("Save to DataBase. Must be added {} new clients.", listNewClient.size());
            clientDao.saveAll(listNewClient);
        }
        if (listUpdatingClient.size() > 0) {
            log.debug("Write change to DataBase. Must be updated {} clients.", listUpdatingClient.size());
            clientDao.updateAll(listUpdatingClient);
        }
        if (oldClient.size() > 0) {
            log.debug("Delete old client from DataBase. Must be deleted {} clients.", oldClient.size());
            clientDao.deleteAll(oldClient.keySet());
        }

        long end = System.currentTimeMillis();
        log.info("End writing 'C L I E N T S'. Time = {} c.", (double)(end-start)/1000);
    }
}
