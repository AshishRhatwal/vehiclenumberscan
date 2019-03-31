package mra.com.vehicletracker.Databaseclasses;

/**
 * Created by mr. A on 13-03-2019.
 */

public class PoliceAdd
{
    String id,pid,pname,pcontact,puser,ppassword;

    public PoliceAdd()
    {}

    public PoliceAdd(String id, String pid, String pname, String pcontact, String puser, String ppassword) {
        this.id = id;
        this.pid = pid;
        this.pname = pname;
        this.pcontact = pcontact;
        this.puser = puser;
        this.ppassword = ppassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcontact() {
        return pcontact;
    }

    public void setPcontact(String pcontact) {
        this.pcontact = pcontact;
    }

    public String getPuser() {
        return puser;
    }

    public void setPuser(String puser) {
        this.puser = puser;
    }

    public String getPpassword() {
        return ppassword;
    }

    public void setPpassword(String ppassword) {
        this.ppassword = ppassword;
    }
}
