package faucher.paul.fleetinsurance;

/**
 * Created by PaulOSX on 2017-04-06.
 */

public class Users {

    private int id;
    private String name;
    private String address;
    private String phoneNum;
    private String claimStatus;
    private String planStatus;
    private String res;

    public Users(String name, String address, String phoneNum,
                 String claimStatus, String planStatus, String res) {

        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.claimStatus = claimStatus;
        this.planStatus = planStatus;
        this.res = res;

    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }
}
