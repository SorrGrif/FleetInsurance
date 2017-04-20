package faucher.paul.fleetinsurance;

public class Claims {

    private int id;
    private String claimName;
    private String date;
    private String desc;
    private String res;


    Claims(String claimName, String date, String desc, String res){

        this.claimName = claimName;
        this.date = date;
        this.desc = desc;
        this.res = res;

    }

    Claims(int id, String claimName, String date, String desc, String res){

        this.id = id;
        this.claimName = claimName;
        this.date = date;
        this.desc = desc;
        this.res = res;

    }

    Claims()
    {

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

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }


}
