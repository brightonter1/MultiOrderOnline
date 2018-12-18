package group14.multiorder.multiorderonline.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

public class menuAdapter extends ArrayAdapter<Menu> {

    ArrayList<Menu> menus = new ArrayList<>();
    Context context;

    public menuAdapter(Context context, int resouce, ArrayList<Menu> obj){
        super(context,resouce,obj);
        this.context = context;
        this.menus = obj;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_menu_sub, parent, false);
        TextView tName, tDes, tPrice, tImage;

        tName = v.findViewById(R.id.sub_name);
        tDes = v.findViewById(R.id.sub_des);
        tPrice = v.findViewById(R.id.sub_price);
//        tImage = v.findViewById(R.id.sub_image);

        Menu _row = menus.get(position);
        tName.setText(_row.getTitle());
        tDes.setText(_row.getDescription());
        tPrice.setText(_row.getPrice());
//        tImage.setText(_row.getImage());

        return v;
    }
}
