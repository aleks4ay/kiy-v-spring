package ua.aleks4ay.kiyv.domain.dao;

import ua.aleks4ay.kiyv.domain.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoJdbc implements ClientDao {

    private static Connection connPostgres = UtilDao.getConnPostgres();

    @Override
    public Client getByKod(String kod) {
        try {
            PreparedStatement statement = connPostgres.prepareStatement("SELECT clientname FROM client WHERE kod = ?;");
            statement.setString(1, kod);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return new Client(rs.getString("kod"), rs.getString("clientname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        List<Client> result = new ArrayList<>();
        try {
            Statement statement = connPostgres.createStatement();
            ResultSet rs = statement.executeQuery("SELECT kod, clientname FROM client;");
            while (rs.next()) {
                result.add(new Client( rs.getString("kod"), rs.getString("clientname")));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Client newClient) {
        try (PreparedStatement statement = connPostgres.prepareStatement("INSERT INTO client (id, kod, clientname) " +
                "VALUES (nextval('hibernate_sequence'), ?, ?);")){

            statement.setString(1, newClient.getKod());
            statement.setString(2, newClient.getClientName());
            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String kod) {
        try (PreparedStatement statement = connPostgres.prepareStatement("DELETE FROM client WHERE kod = ?;")){
            statement.setString(1, kod);
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean saveAll(List<Client> clientList) {
        try {
            connPostgres.setAutoCommit(false);

            for (Client client : clientList) {
                PreparedStatement ps = connPostgres.prepareStatement("INSERT INTO client (id, kod, clientname) " +
                        "VALUES (nextval('hibernate_sequence'), ?, ?);");
                ps.setString(1, client.getKod());
                ps.setString(2, client.getClientName());
                ps.addBatch();
                ps.executeBatch();
            }
            connPostgres.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean updateAll(List<Client> clientList) {
        try {
            connPostgres.setAutoCommit(false);

            for (Client client : clientList) {
                PreparedStatement ps = connPostgres.prepareStatement("UPDATE client SET clientname = ? WHERE kod = ?;");
                ps.setString(1, client.getClientName());
                ps.setString(2, client.getKod());
                ps.addBatch();
                ps.executeBatch();
            }
            connPostgres.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean isBlocking() {
        try (Statement statement = connPostgres.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT blocking FROM table_flag WHERE nane = 'client';");
            while (rs.next()) {
                return rs.getBoolean("blocking");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void setBlock() {
        try (Statement statement = connPostgres.createStatement()){
            statement.executeUpdate("UPDATE table_flag SET blocking = TRUE WHERE nane = 'client';");
            connPostgres.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUnblock() {
        try (Statement statement = connPostgres.createStatement()){
            statement.executeUpdate("UPDATE table_flag SET blocking = FALSE WHERE nane = 'client';");
            connPostgres.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



