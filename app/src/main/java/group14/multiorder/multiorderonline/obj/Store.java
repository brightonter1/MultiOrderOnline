package group14.multiorder.multiorderonline.obj;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Store implements Parcelable {
    private String post_id="";
    private String user_id="";
    private String image="";
    private String title="";
    private String description="";
    private String tag="";
    private int shop_id;
    private String openClose="";
    private String address="";
    private String phoneNumber="";


    protected Store(Parcel in) {
        post_id = in.readString();
        user_id = in.readString();
        image = in.readString();
        title = in.readString();
        description = in.readString();
        tag = in.readString();
        shop_id = in.readInt();
        openClose = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(post_id);
        dest.writeString(user_id);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(tag);
        dest.writeInt(shop_id);
        dest.writeString(openClose);
        dest.writeString(address);
        dest.writeString(phoneNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    public String getOpenClose() {
        return openClose;
    }

    public void setOpenClose(String openClose) {
        this.openClose = openClose;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public Store(){}


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

    public String getTag() {
        return tag;
    }

    public void setTag(String Tag) {
        this.tag = Tag;
    }

    @Override
    public String toString() {
        return "Store{" +
                "post_id='" + post_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tag='" + tag + '\'' +
                ", shop_id=" + shop_id +
                ", openClose='" + openClose + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
