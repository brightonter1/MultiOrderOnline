package group14.multiorder.multiorderonline.Cart;

import java.util.ArrayList;

import group14.multiorder.multiorderonline.obj.Menu;

public class Cart {
    private ArrayList<Menu> _menuList = new ArrayList<Menu>();

    public Cart(){}

    public void addMenu(Menu mm){
        _menuList.add(mm);
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
}
