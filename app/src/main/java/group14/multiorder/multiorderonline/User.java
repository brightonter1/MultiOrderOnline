package group14.multiorder.multiorderonline;

public class User {
    private String name;
    private String surname;
    private String gender;
    private int age;
    private String address;
    private String phone;
    private String email;
    private String image;
    private String pwd;

    public void setProfile(String name, String surname, String gender, int age, String address, String phone, String email, String image, String pwd){
        setName(name);
        setSurname(surname);
        setGender(gender);
        setAge(age);
        setAddress(address);
        setPhone(phone);
        setEmail(email);
        setImage(image);
        setPwd(pwd);
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
