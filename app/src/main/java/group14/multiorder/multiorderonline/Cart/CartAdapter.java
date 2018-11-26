package group14.multiorder.multiorderonline.Cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import group14.multiorder.multiorderonline.R;

public class CartAdapter extends ArrayAdapter<Cart> {

    private Context context;
    private ArrayList<Cart> carts = new ArrayList<Cart>();


    public CartAdapter(@NonNull Context context, int resource, ArrayList<Cart> carts) {
        super(context, resource);
        this.carts = carts;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View cartItem = LayoutInflater.from(context).inflate(R.layout.fragment_cart_item,
                parent,
                false);

        ImageView menuImage = cartItem.findViewById(R.id.cartitem_menu_image);
        TextView menuName = cartItem.findViewById(R.id.cartitem_mune_name);
        ImageView menuPrice = cartItem.findViewById(R.id.cartitem_menu_price);
        TextView menuPiece = cartItem.findViewById(R.id.cartitem_menu_piece);

        Cart row = carts.get(position);

        menuName.setText(row.getName());
        menuPiece.setText(row.getPiece());
    }
}
