package ua.aleks4ay.kiyv.copiller_db.reader;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.aleks4ay.kiyv.domain.dao.UtilDao;
import ua.aleks4ay.kiyv.domain.model.Client;
import ua.aleks4ay.kiyv.domain.services.ClientService;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class CopyClientSpring {

    private static ConfigurableApplicationContext appCtx = null;
    private static ClientService clientService = null;
    private static Connection connDbf = UtilDao.getConnDbf();

    static {
        appCtx = new ClassPathXmlApplicationContext("persistence-config.xml");
        clientService = appCtx.getBean(ClientService.class);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        new CopyClientSpring().doCopyNewRecord();

        long end = System.currentTimeMillis();
        System.out.println("time = " + (double)(end-start) + " mc." );
    }

    public void doCopyNewRecord() {
        System.out.println("start write  'C L I E N T S'");

        List<Client> newClient = new ArrayList<>();

        Map<String, Client> oldClient = clientService.getAll()
                .stream()
                .collect(Collectors.toMap(Client::getId, Client::getClient));

        try (Statement st = connDbf.createStatement()){
            ResultSet rs1 = st.executeQuery("select ID, DESCR from SC172;");
            while (rs1.next()) {
                String name = rs1.getString(2);
                if (name != null) {
                    name = new String(rs1.getBytes(2), "Windows-1251");
                } else {
                    name = "-";
                }
                Client client = new Client(rs1.getString(1), name);
                if ( oldClient.containsKey(client.getId()) && oldClient.get(client.getId()).getClientName().equals(name) ) {
                    continue;
                }
                else {
                    newClient.add(client);
                }
            }
            System.out.println("Add " + newClient.size() + " new Clients.");
            clientService.saveAll(newClient);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
