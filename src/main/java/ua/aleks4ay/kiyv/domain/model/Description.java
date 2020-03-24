package ua.aleks4ay.kiyv.domain.model;

import ua.aleks4ay.kiyv.domain.name.StatusPosition;
import ua.aleks4ay.kiyv.domain.name.TypePosition;

public class Description {

    private int position = 0;
    private int quantity = 0;
    private String descrFirst = "";
    private String descrSecond = "";
    private String designer = null;
    private int statusIndex = 0;
    private int typeIndex = 0;
    private long statusTime = 0L;

//    private TypePosition type;
//    private StatusPosition status;

    public Description() {
    }

    public Description(int position, int quantity, String descrFirst, String descrSecond, String designer,
                       int statusIndex, int typeIndex, long statusTime) {
        this.position = position;
        this.quantity = quantity;
        this.descrFirst = descrFirst;
        this.descrSecond = descrSecond;
        this.designer = designer;
        this.statusIndex = statusIndex;
        this.typeIndex = typeIndex;
        this.statusTime = statusTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public int getStatusIndex() {
        return statusIndex;
    }

    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public long getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(long statusTime) {
        this.statusTime = statusTime;
    }
}
