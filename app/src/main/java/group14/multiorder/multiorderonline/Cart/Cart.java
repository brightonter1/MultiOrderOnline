package group14.multiorder.multiorderonline.Cart;

public class Cart {

    private String imgPath;
    private String name;
    private String price;
    private String piece;

    public Cart(String imgPath, String name, String price, String piece) {
        this.imgPath = imgPath;
        this.name = name;
        this.price = price;
        this.piece = piece;
    }

    public Cart() { }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }
}
