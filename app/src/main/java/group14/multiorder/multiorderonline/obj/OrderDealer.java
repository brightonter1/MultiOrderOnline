package group14.multiorder.multiorderonline.obj;

import java.util.ArrayList;
import java.util.Date;

public class OrderDealer {
    private int Orderid;
    private int shop_id;
    private ArrayList<Menu> menu;
    private String _status = "inprogress";
    //Date date;
    private int total;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOrderid() {
        return Orderid;
    }

    public void setOrderid(int orderid) {
        Orderid = orderid;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public ArrayList<Menu> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Menu> menu) {
        this.menu = menu;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
