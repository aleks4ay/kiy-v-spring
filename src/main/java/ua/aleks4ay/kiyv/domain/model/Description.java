package ua.aleks4ay.kiyv.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
//@Table("description")
public class Description {
    @Id
    @GeneratedValue
    private long descrId;

    private Integer position;
    private String descrFirst;
    private String descrSecond;
    private Integer statusIndex;
    private Integer typeIndex;
    private LocalDateTime statusTime;
    private Integer quantity;

    @Column(nullable = false, updatable = false, scale = 2, precision = 10) // {1234567890.12}
    private BigDecimal amount;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID") //add column 'Descr.ORDER_ID' with link to table 'Order'
    private Order owner;

    public Description() {
    }

    public Description(Integer position, String descrFirst, String descrSecond, Integer statusIndex, Integer typeIndex,
                       LocalDateTime statusTime, Integer quantity, BigDecimal amount, Order owner) {
        this.position = position;
        this.descrFirst = descrFirst;
        this.descrSecond = descrSecond;
        this.statusIndex = statusIndex;
        this.typeIndex = typeIndex;
        this.statusTime = statusTime;
        this.quantity = quantity;
        this.amount = amount;
        this.owner = owner;
    }

    public long getDescrId() {
        return descrId;
    }

    public void setDescrId(long descrId) {
        this.descrId = descrId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDescrFirst() {
        return descrFirst;
    }

    public void setDescrFirst(String descrFirst) {
        this.descrFirst = descrFirst;
    }

    public String getDescrSecond() {
        return descrSecond;
    }

    public void setDescrSecond(String descrSecond) {
        this.descrSecond = descrSecond;
    }

    public Integer getStatusIndex() {
        return statusIndex;
    }

    public void setStatusIndex(Integer statusIndex) {
        this.statusIndex = statusIndex;
    }

    public Integer getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(Integer typeIndex) {
        this.typeIndex = typeIndex;
    }

    public LocalDateTime getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(LocalDateTime statusTime) {
        this.statusTime = statusTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Order getOwner() {
        return owner;
    }

    public void setOwner(Order owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Description{" +
                "descrId=" + descrId +
                ", position=" + position +
                ", descrFirst='" + descrFirst + '\'' +
                ", descrSecond='" + descrSecond + '\'' +
                ", statusIndex=" + statusIndex +
                ", typeIndex=" + typeIndex +
                ", statusTime=" + statusTime +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}
