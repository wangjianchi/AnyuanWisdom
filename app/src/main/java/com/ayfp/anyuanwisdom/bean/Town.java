package com.ayfp.anyuanwisdom.bean;

/**
 * @author:: wangjianchi
 * @time: 2017/12/12  14:46.
 * @description:
 */

public class Town {
    private int id;
    private String town_name;
    private String village_name;
    private boolean select;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTown_name() {
        return town_name;
    }

    public void setTown_name(String town_name) {
        this.town_name = town_name;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
