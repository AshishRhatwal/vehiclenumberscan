package mra.com.vehicletracker;

/**
 * Created by mr. A on 13-03-2019.
 */

public class Vehicleinfo
{
    String id,name,color,number,cname,type;

    public  Vehicleinfo()
    {

    }

    public Vehicleinfo(String id, String name, String color,String number,String cname,String type) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.number=number;
        this.cname=cname;
        this.type=type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
