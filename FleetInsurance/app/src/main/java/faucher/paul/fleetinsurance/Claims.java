package faucher.paul.fleetinsurance;

public class Claims {

    private int id;
    private String date;
    private String desc;
    private String res;



    Claims(int id, String date, String desc, String res){

        this.id = id;
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

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }


}
