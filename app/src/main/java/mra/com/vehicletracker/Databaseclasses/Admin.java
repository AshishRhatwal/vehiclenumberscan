package mra.com.vehicletracker.Databaseclasses;

/**
 * Created by mr. A on 19-03-2019.
 */

public class Admin
{
    String id,name,pass;

    public Admin()
    {}

    public Admin(String id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
