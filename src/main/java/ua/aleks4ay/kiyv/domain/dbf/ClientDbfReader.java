package ua.aleks4ay.kiyv.domain.dbf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.kiyv.domain.dao.UtilDao;
import ua.aleks4ay.kiyv.domain.model.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class ClientDbfReader implements ClientDbf {

    private static Connection connDbf;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_ALL = "select ID, DESCR from SC172;";

    public ClientDbfReader() {
        connDbf = UtilDao.getConnDbf();
        log.debug("Get connection to 'dbf-files' 1C from {}.", ClientDbfReader.class);
    }

    @Override
    public List<Client> getAll() {

        List<Client> listClient = new ArrayList<>();

        try (Statement st = connDbf.createStatement()) {
            ResultSet rs1 = st.executeQuery(SQL_GET_ALL);
            log.debug("Select all 'Clients' from 1C. SQL = {}.", SQL_GET_ALL);
            while (rs1.next()) {
                String name = rs1.getString(2);
                if (name != null) {
                    byte[] bytes = rs1.getBytes(2);
                    name = new String(bytes, "Windows-1251");
                } else {
                    name = "-";
                }
                listClient.add(new Client(rs1.getString(1), name));
            }
            log.debug("Was read {} Clients.", listClient.size());
            return listClient;
        } catch (Exception e) {
            log.warn("Exception during writing all 'Client'.", e);
        }
        log.debug("Clients not found.");
        return null;
    }
}
