package group14.multiorder.multiorderonline.obj;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OrderCustomer implements Parcelable {

    private int order_id;
    private ArrayList<Menu> menu;
    private String status = "inprogress";
    private String date;
    private int total;

    public OrderCustomer(Parcel in) {
        order_id = in.readInt();
        menu = in.createTypedArrayList(Menu.CREATOR);
        status = in.readString();
        date = in.readString();
        total = in.readInt();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order_id);
        dest.writeTypedList(menu);
        dest.writeString(status);
        dest.writeString(date);
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderCustomer> CREATOR = new Creator<OrderCustomer>() {
        @Override
        public OrderCustomer createFromParcel(Parcel in) {
            return new OrderCustomer(in);
        }

        @Override
        public OrderCustomer[] newArray(int size) {
            return new OrderCustomer[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OrderCustomer(){};

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public ArrayList<Menu> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Menu> menu) {
        this.menu = menu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateTotal(){
        total = 0;
        for(Menu m: menu){
            total += Integer.parseInt(m.getPrice().replace("฿", ""));
        }
    }

}
