package ua.aleks4ay.kiyv.copiller_db.reader;

import ua.aleks4ay.kiyv.domain.dao.ClientDao;
import ua.aleks4ay.kiyv.domain.dao.ClientDaoJdbc;
import ua.aleks4ay.kiyv.domain.dbf.ClientDbf;
import ua.aleks4ay.kiyv.domain.dbf.ClientDbfReader;
import ua.aleks4ay.kiyv.domain.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CopyClient {

    private static final CopyClient copyClient = new CopyClient();

    private CopyClient() {
    }

    public static CopyClient getInstance() {
        return copyClient;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        CopyClient.getInstance().doCopyNewRecord();

        long end = System.currentTimeMillis();
        System.out.println("time = " + (double)(end-start) + " mc." );
    }

    public synchronized void doCopyNewRecord() {
        ClientDao clientDao = new ClientDaoJdbc();
        ClientDbf clientDbfReader = new ClientDbfReader();

        while ( clientDao.isBlocking()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        clientDao.setBlock();

        System.out.println("start write  'C L I E N T S'");

        List<Client> listNewClient = new ArrayList<>();
        List<Client> listUpdatingClient = new ArrayList<>();
        Map<String, String> oldClient = clientDao.getAll()
                .stream()
                .collect(Collectors.toMap(Client::getKod, Client::getClientName));

        List<Client> tempListClient = clientDbfReader.getAll();

        for (Client client : tempListClient) {
            if (!oldClient.containsKey(client.getKod())) {
                listNewClient.add(client);
            } else if (!oldClient.get(client.getKod()).equals(client.getClientName())) {
                listUpdatingClient.add(client);
            }
        }

        System.out.println("Add " + listNewClient.size() + " new Clients.");
        System.out.println("Update " + listUpdatingClient.size() + " old Clients.");

        clientDao.saveAll(listNewClient);
        clientDao.updateAll(listUpdatingClient);
        System.out.println("End write to DB\n");
        try {
            clientDao.setUnblock();

            Thread.sleep(2000);
            System.out.println("End sleep 2000");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
