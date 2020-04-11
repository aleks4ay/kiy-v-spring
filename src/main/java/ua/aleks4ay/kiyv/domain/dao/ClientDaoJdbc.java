package ua.aleks4ay.kiyv.domain.dao;

import ua.aleks4ay.kiyv.domain.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientDaoJdbc implements ClientDao {

    private static Connection connPostgres = UtilDao.getConnPostgres();

    @Override
    public Client getByKod(String id) {
        try {
            PreparedStatement statement = connPostgres.prepareStatement("SELECT clientname FROM client WHERE id = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return new Client(rs.getString("id"), rs.getString("clientname"));
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
            ResultSet rs = statement.executeQuery("SELECT id, clientname FROM client;");
            while (rs.next()) {
                result.add(new Client( rs.getString("id"), rs.getString("clientname")));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Client newClient) {
        try (PreparedStatement statement = connPostgres.prepareStatement("INSERT INTO client (id, clientname) " +
                "VALUES (?, ?);")){

            statement.setString(1, newClient.getId());
            statement.setString(2, newClient.getClientName());
            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try (PreparedStatement statement = connPostgres.prepareStatement("DELETE FROM client WHERE id = ?;")){
            statement.setString(1, id);
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
                PreparedStatement ps = connPostgres.prepareStatement("INSERT INTO client (id, clientname) " +
                        "VALUES (?, ?);");
                ps.setString(1, client.getId());
                ps.setString(2, client.getClientName());
                ps.addBatch();
                ps.executeBatch();
            }
            connPostgres.commit();
            connPostgres.setAutoCommit(true);
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
                PreparedStatement ps = connPostgres.prepareStatement("UPDATE client SET clientname = ? WHERE id = ?;");
                ps.setString(1, client.getClientName());
                ps.setString(2, client.getId());
                ps.addBatch();
                ps.executeBatch();
            }
            connPostgres.commit();
            connPostgres.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAll(Collection<String> clientListId) {
        try {
            connPostgres.setAutoCommit(false);

            for (String id : clientListId) {
                PreparedStatement ps = connPostgres.prepareStatement("DELETE FROM client WHERE id = ?;");
                ps.setString(1, id);
                ps.addBatch();
                ps.executeBatch();
            }
            connPostgres.commit();
            connPostgres.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean isBlocking() {
        try (Statement statement = connPostgres.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT status FROM table_flag WHERE parametr_nane = 'blocking';");
            while (rs.next()) {
                return rs.getBoolean("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void setBlock() {
        try (Statement statement = connPostgres.createStatement()){
            statement.executeUpdate("UPDATE table_flag SET status = TRUE WHERE parametr_nane = 'blocking';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUnblock() {
        try (Statement statement = connPostgres.createStatement()){
            statement.executeUpdate("UPDATE table_flag SET status = FALSE WHERE parametr_nane = 'blocking';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



