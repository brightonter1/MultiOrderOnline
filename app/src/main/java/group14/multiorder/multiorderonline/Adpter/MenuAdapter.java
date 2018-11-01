package group14.multiorder.multiorderonline.Adpter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import group14.multiorder.multiorderonline.Post;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ImageViewHolder>{


    private Context _context;
    private List<Menu> _menus;

    public MenuAdapter(Context context , List uploads){
        _context = context;
        _menus = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_menuitem, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        final Menu _menuCurrent = _menus.get(i);
        imageViewHolder._menuName.setText(_menuCurrent.getTitle());
        imageViewHolder._menuDes.setText(_menuCurrent.getDescription());
        imageViewHolder._menuPrice.setText(_menuCurrent.getPrice());
        Picasso.with(_context)
                .load(_menuCurrent.getImage())
                .fit()
                .centerCrop()
                .into(imageViewHolder._menuImg);
    }

    @Override
    public int getItemCount() {
        return _menus.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView _menuName;
        public TextView _menuDes;
        public ImageView _menuImg;
        public Button _menuPrice;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            _menuName = itemView.findViewById(R.id.menuitem_name);
            _menuDes = itemView.findViewById(R.id.menuitem_des);
            _menuImg = itemView.findViewById(R.id.menuitem_img);
            _menuPrice = itemView.findViewById(R.id.menuitem_price);
        }
    }

}
