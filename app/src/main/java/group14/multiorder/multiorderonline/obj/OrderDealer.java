package group14.multiorder.multiorderonline.obj;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class OrderDealer implements Parcelable{
    private int Orderid;
    private int shop_id;
    private ArrayList<Menu> menu;
    private String _status = "inprogress";
    private String date;
    private String cus_uid;

    public String getCus_uid() {
        return cus_uid;
    }

    public void setCus_uid(String cus_uid) {
        this.cus_uid = cus_uid;
    }

    public OrderDealer(Parcel in) {
        Orderid = in.readInt();
        shop_id = in.readInt();
        menu = in.createTypedArrayList(Menu.CREATOR);
        _status = in.readString();
        date = in.readString();
        cus_uid = in.readString();
        total = in.readInt();
        address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Orderid);
        dest.writeInt(shop_id);
        dest.writeTypedList(menu);
        dest.writeString(_status);
        dest.writeString(date);
        dest.writeString(cus_uid);
        dest.writeInt(total);
        dest.writeString(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderDealer> CREATOR = new Creator<OrderDealer>() {
        @Override
        public OrderDealer createFromParcel(Parcel in) {
            return new OrderDealer(in);
        }

        @Override
        public OrderDealer[] newArray(int size) {
            return new OrderDealer[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //Date date;
    private int total;
    private String address;
    public  OrderDealer(){}

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
