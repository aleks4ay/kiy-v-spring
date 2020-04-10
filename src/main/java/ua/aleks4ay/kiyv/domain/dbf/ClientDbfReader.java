package ua.aleks4ay.kiyv.domain.dbf;

import ua.aleks4ay.kiyv.domain.dao.UtilDao;
import ua.aleks4ay.kiyv.domain.model.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDbfReader implements ClientDbf {

    private static Connection connDbf = UtilDao.getConnDbf();

    @Override
    public List<Client> getAll() {

        List<Client> listClient = new ArrayList<>();

        try (Statement st = connDbf.createStatement()) {

            ResultSet rs1 = st.executeQuery("select ID, DESCR from SC172;");

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
            return listClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
