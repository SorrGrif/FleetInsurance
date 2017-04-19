package faucher.paul.fleetinsurance;

import java.io.File;

public class Claims {

    private int id;
    private String claimName;
    private String date;
    private String desc;
    private File res;


    Claims(String claimName, String date, String desc, File res){

        this.claimName = claimName;
        this.date = date;
        this.desc = desc;
        this.res = res;

    }

    Claims(int id, String claimName, String date, String desc, File res){

        this.id = id;
        this.claimName = claimName;
        this.date = date;
        this.desc = desc;
        this.res = res;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClaimName(){
        return claimName;
    }

    public void setClaimName(String claimName) {
        this.claimName = claimName;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public File getRes() {
        return res;
    }

    public void setRes(File res) {
        this.res = res;
    }


}
