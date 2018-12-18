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

    @androidx.annotation.NonNull
    @Override
    public View getView(int position, @androidx.annotation.Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_menu_sub);
        TextView tName, tDes, tPrice, tImage;
        
        return v;
    }
}
