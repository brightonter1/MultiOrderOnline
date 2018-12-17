package group14.multiorder.multiorderonline.Cart;

import java.util.ArrayList;

import group14.multiorder.multiorderonline.obj.Menu;

public class Cart {
    private ArrayList<Menu> _menuList = new ArrayList<Menu>();
    private int total = 0;

    public Cart(){}

    public void addMenu(Menu mm){
        _menuList.add(mm);
        String ppp = mm.getPrice().replace("฿", "");

        total += Integer.parseInt(ppp)*Integer.parseInt(mm.getAmount());
    }


    public ArrayList<Menu> get_menuList() {
        return _menuList;
    }

    public void set_menuList(ArrayList<Menu> _menuList) {
        this._menuList = _menuList;
    }

    public int getSize(){
        return _menuList.size();
    }

    void plus_amount(int posi){
        Menu cMenu = _menuList.get(posi);
        cMenu.setAmount(String.valueOf(Integer.parseInt(cMenu.getAmount())+1));
        _menuList.set(posi, cMenu);

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
