package ua.aleks4ay.kiyv.domain.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "orders", indexes = {@Index( name = "order_idx", columnList = "id", unique = true)})

public class Order {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "docno")
    private String docNumber;

    @Column(name = "manager_id")
    private String idManager;

    @Column(name = "designer_id")
    private String idDesigner;

    @Column(name = "client_id")
    private String idClient;

    @Column(name = "price")
    double price;

    @Column
    private int durationTime;
    @Column
    private LocalDate dateCreate;
    @Column
    private LocalTime timeCreate;
    @Column
    private LocalDate dateToFactory;
    @Column
    private LocalDate dateEnd;


    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Description> descriptions;

    public Order() {
    }

    public Order(String id, String docNumber, String idManager, String idDesigner, String idClient) {
        this.id = id;
        this.docNumber = docNumber;
        this.idManager = idManager;
        this.idDesigner = idDesigner;
        this.idClient = idClient;
        this.descriptions = null;
    }

    public Order(String id, String docNumber, String idManager, String idDesigner, String idClient, double price,
                 int durationTime, LocalDate dateCreate, LocalTime timeCreate, LocalDate dateToFactory, LocalDate dateEnd) {
        this.id = id;
        this.docNumber = docNumber;
        this.idManager = idManager;
        this.idDesigner = idDesigner;
        this.idClient = idClient;
        this.price = price;
        this.durationTime = durationTime;
        this.dateCreate = dateCreate;
        this.timeCreate = timeCreate;
        this.dateToFactory = dateToFactory;
        this.dateEnd = dateEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getIdManager() {
        return idManager;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
    }

    public String getIdDesigner() {
        return idDesigner;
    }

    public void setIdDesigner(String idDesigner) {
        this.idDesigner = idDesigner;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalTime getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(LocalTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    public LocalDate getDateToFactory() {
        return dateToFactory;
    }

    public void setDateToFactory(LocalDate dateToFactory) {
        this.dateToFactory = dateToFactory;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }


    public Order getOrder() {
        return this;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", idManager='" + idManager + '\'' +
                ", idDesigner='" + idDesigner + '\'' +
                ", idClient='" + idClient + '\'' +
                ", price=" + price +
                ", durationTime=" + durationTime +
                ", dateCreate=" + dateCreate +
                ", timeCreate=" + timeCreate +
                ", dateToFactory=" + dateToFactory +
                ", dateEnd=" + dateEnd +
                '}';
    }


    /**
     * fields idDesigner, dateEnd and descriptions (List<Description>) NOT compare!!!
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Order.class)) {
            Order order = (Order) obj;
            boolean equalsId = this.id.equals(order.getId());
            boolean equalsDocNumber = this.docNumber.equals(order.getDocNumber());
            boolean equalsManager = this.idManager.equals(order.getIdManager());
            boolean equalsClient = this.idClient.equals(order.getIdClient());
            boolean equalsPrice = (this.price == order.getPrice());
            boolean equalsDurationTime = (this.durationTime == order.getDurationTime());
            boolean equalsDateCreate = this.dateCreate.equals(order.getDateCreate());
            boolean equalsTimeCreate = this.timeCreate.equals(order.getTimeCreate());
            boolean equalsDateToFactory = this.dateToFactory.equals(order.getDateToFactory());

            return equalsId && equalsDocNumber && equalsManager && equalsClient && equalsPrice && equalsDurationTime &&
                    equalsDateCreate && equalsTimeCreate && equalsDateToFactory;
        }
        return false;
    }
}



