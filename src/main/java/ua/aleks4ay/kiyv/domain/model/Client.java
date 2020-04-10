package ua.aleks4ay.kiyv.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue //(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "kod", unique = true)
    private String kod;

    @Column(name = "clientName")
    private String clientName;

    public Client() {
    }

    public Client(String kod, String clientName) {
        this.kod = kod;
        this.clientName = clientName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", kod='" + kod + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Client.class)) {
            Client client = (Client) obj;
            return this.kod.equals(client.getKod()) && this.clientName.equals(client.getClientName());
        }
        return false;
    }

    public Client getClient() {
        return this;
    }
}
