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
                '}';
    }

    public Menu(){}



    protected Menu(Parcel in) {
        this.post_id = in.readString();
        this.user_id = in.readString();
        this.image = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.price = in.readString();
        this.amount = in.readString();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.post_id);
        dest.writeString(this.user_id);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.price);
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
