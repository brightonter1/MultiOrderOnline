package group14.multiorder.multiorderonline.obj;

import android.os.Parcel;
import android.os.Parcelable;

public class Menu implements Parcelable {

    private String post_id;
    private String user_id;
    private String image;
    private String title;
    private String description;
    private String price;
    private String amount = "1";
    private int shop_id;
    private String status  = "inprogress";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Menu(Parcel in) {
        post_id = in.readString();
        user_id = in.readString();
        image = in.readString();
        title = in.readString();
        description = in.readString();
        price = in.readString();
        amount = in.readString();
        shop_id = in.readInt();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(post_id);
        dest.writeString(user_id);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(amount);
        dest.writeInt(shop_id);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public int getShop_id() {
        return shop_id;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "post_id='" + post_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", amount='" + amount + '\'' +
                ", shop_id=" + shop_id +
                ", status='" + status + '\'' +
                '}';
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public Menu(){}


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
