package ua.aleks4ay.kiyv.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.kiyv.domain.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class ClientDaoJdbc implements ClientDao {

    private static Connection connPostgres;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_ONE = "SELECT clientname FROM client WHERE id = ?;";
    private static final String SQL_GET_ALL = "SELECT id, clientname FROM client;";
    private static final String SQL_DELETE = "DELETE FROM client WHERE id = ?;";
    private static final String SQL_SAVE = "INSERT INTO client (id, clientname) VALUES (?, ?);";
    private static final String SQL_UPDATE = "UPDATE client SET clientname = ? WHERE id = ?;";


    public ClientDaoJdbc(Connection conn) {
        connPostgres = conn;
        log.debug("Get connection to PostgreSQL from {}.", UtilDao.class);
    }

    @Override
    public Client getById(String id) {
        try {
            PreparedStatement statement = connPostgres.prepareStatement(SQL_GET_ONE);
            statement.setString(1, id);
            log.debug("Select 'Client'. SQL = {}. id = {}.", SQL_GET_ONE, id);
            ResultSet rs = statement.executeQuery();

            rs.next();
            log.debug("return new 'Client'. id = {}.", id);
            return new Client(id, rs.getString("clientname"));

        } catch (SQLException e) {
            log.warn("Exception during reading 'Client' with id = {}.", id, e);
        }
        log.debug("Client with id = {} not found.", id);
        return null;
    }

    @Override
    public List<Client> getAll() {
        List<Client> result = new ArrayList<>();
        try {
            Statement statement = connPostgres.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            log.debug("Select all 'Clients'. SQL = {}.", SQL_GET_ALL);
            while (rs.next()) {
                result.add(new Client( rs.getString("id"), rs.getString("clientname")));
            }
            log.debug("Was read {} Clients.", result.size());
            return result;
        } catch (SQLException e) {
            log.warn("Exception during reading all 'Client'.", e);
        }
        log.debug("Clients not found.");
        return null;
    }

   /* @Override
    public boolean save(Client newClient) {
        try (PreparedStatement statement = connPostgres.prepareStatement(SQL_SAVE)){
            log.debug("Prepared to save.");
            statement.setString(1, newClient.getId());
            statement.setString(2, newClient.getClientName());
            int result = statement.executeUpdate();
            if (result == 1) {
                connPostgres.commit();
                log.debug("Save new 'Client'. SQL = {}. Client = {}.", SQL_SAVE, newClient);
                return true;
            }
            else {
                connPostgres.rollback();
            }
        } catch (SQLException e) {
            log.warn("Exception during saving new 'Client'. SQL = {}. Client = {}.", SQL_SAVE, newClient, e);
        }
        log.debug("'Client' don't Saved!!! SQL = {}. Client = {}.", SQL_SAVE, newClient);
        return false;
    }

    @Override
    public boolean delete(String id) {
        try (PreparedStatement statement = connPostgres.prepareStatement(SQL_DELETE)){
            log.debug("Prepared to delete.");
            statement.setString(1, id);
            int result = statement.executeUpdate();
            if (result == 1) {
                connPostgres.commit();
                log.debug("Client with id = {} deleted. SQL = {}.", id, SQL_DELETE);
                return true;
            }
        } catch (SQLException e) {
            log.warn("Exception during delete old 'Client'. SQL = {}.", SQL_DELETE, e);
        }
        log.debug("Client with id = {} don't Deleted!!! SQL = {}.", id, SQL_DELETE);
        return false;
    }
*/

    @Override
    public boolean saveAll(List<Client> clientList) {
        try {
            int result = 0;
            for (Client client : clientList) {
                PreparedStatement ps = connPostgres.prepareStatement(SQL_SAVE);
                log.debug("Prepared new 'Client' to batch. SQL = {}. Client = {}.", SQL_SAVE, client);
                ps.setString(1, client.getId());
                ps.setString(2, client.getClientName());
                ps.addBatch();
                int[] numberOfUpdates = ps.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
            }
            if (result == clientList.size()) {
                log.debug("Try commit");
                connPostgres.commit();
                log.debug("Commit - OK. {} Clients Saved. SQL = {}.", result, SQL_SAVE);
                return true;
            }
            else {
                log.debug("Saved {}, but need to save {} clients. Not equals!!!", result, clientList.size());
                connPostgres.rollback();
            }
        } catch (SQLException e) {
            log.warn("Exception during saving {} new 'Client'. SQL = {}.", clientList.size() , SQL_SAVE, e);
        }
        return false;
    }


    @Override
    public boolean updateAll(List<Client> clientList) {
        try {
            int result = 0;
            for (Client client : clientList) {
                log.debug("Prepared updated 'Client' to batch. SQL = {}. Client = {}.", SQL_UPDATE, client);
                PreparedStatement ps = connPostgres.prepareStatement(SQL_UPDATE);
                ps.setString(1, client.getClientName());
                ps.setString(2, client.getId());
                ps.addBatch();
                int[] numberOfUpdates = ps.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();

                for (int i=0; i< numberOfUpdates.length; i++) {
                    System.out.println("k[" + i + "] = " + numberOfUpdates[i]);
                }
            }
            if (result == clientList.size()) {
                log.debug("Try commit");
                connPostgres.commit();
                log.debug("Commit - OK. {} Clients updated. SQL = {}.", result, SQL_UPDATE);
                return true;
            }
            else {
                connPostgres.rollback();
                log.debug("Updated {}, but need to update {} clients. Not equals!!!", result, clientList.size());
            }
        } catch (SQLException e) {
            log.warn("Exception during update {} changed 'Client'. SQL = {}.", clientList.size() , SQL_UPDATE, e);
        }
        return false;
    }

    @Override
    public boolean deleteAll(Collection<String> clientListId) {
        try {
            int result = 0;
            for (String id : clientListId) {
                log.debug("Prepared old 'Client' for delete to batch. SQL = {}. Id = {}.", SQL_DELETE, id);
                PreparedStatement ps = connPostgres.prepareStatement(SQL_DELETE);
                ps.setString(1, id);
                ps.addBatch();
                int[] numberOfUpdates = ps.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
            }
            if (result == clientListId.size()) {
                log.debug("Try commit");
                connPostgres.commit();
                log.debug("Commit - OK. {} Clients updated. SQL = {}.", result, SQL_DELETE);
                return true;
            }
            else {
                connPostgres.rollback();
                log.debug("Deleted {}, but need to delete {} clients. Not equals!!!", result, clientListId.size());
            }
        } catch (SQLException e) {
            log.warn("Exception during delete {} old 'Client'. SQL = {}.", clientListId.size() , SQL_DELETE, e);
        }
        return false;
    }
}



