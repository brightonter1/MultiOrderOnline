package group14.multiorder.multiorderonline.obj;

import android.os.Parcel;
import android.os.Parcelable;

public class Store implements Parcelable {
    private String post_id;
    private String user_id;
    private String image;
    private String title;
    private String description;
    private String Tag;
    private int shop_id;

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public Store(){}

    public Store(Parcel in){
        this.post_id = in.readString();
        this.user_id = in.readString();
        this.image = in.readString();
        this.title = in.readString();
        this.setDescription(in.readString());

    }



    @Override
    public String toString() {
        return "Store{" +
                "post_id='" + getPost_id() + '\'' +
                ", user_id='" + getUser_id() + '\'' +
                ", image='" + getImage() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeString(getPost_id());
        dest.writeString(getUser_id());
        dest.writeString(getImage());
        dest.writeString(getTitle());
        dest.writeString(getDescription());
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
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
