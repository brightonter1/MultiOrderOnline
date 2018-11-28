package group14.multiorder.multiorderonline.Cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private FirebaseFirestore _fileStore = FirebaseFirestore.getInstance();
    private FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    private Context _context;
    private List<Menu> _cart;
    Cart cart;

    public CartAdapter(Context _context, List _cart) {
        this._context = _context;
        this._cart = _cart;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_sub_cart, viewGroup, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        final Menu _currentMenu = _cart.get(i);
        cartViewHolder.menuTitle.setText(_currentMenu.getTitle());
        cartViewHolder.menuPrice.setText(_currentMenu.getPrice());
        cartViewHolder.amount.setText(_currentMenu.getAmount());
        Picasso.with(_context)
                .load(_currentMenu.getImage())
                .fit()
                .centerCrop()
                .into(cartViewHolder.menuImg);
    }

    @Override
    public int getItemCount() {
        return _cart.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        public ImageView menuImg;
        public TextView menuTitle;
        public TextView menuPrice;
        public ImageButton plus;
        public ImageButton minus;
        public TextView amount;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            menuImg = itemView.findViewById(R.id.sub_cart_image);
            menuTitle = itemView.findViewById(R.id.sub_cart_name);
            menuPrice = itemView.findViewById(R.id.sub_cart_price);
            plus = itemView.findViewById(R.id.sub_cart_add);
            minus = itemView.findViewById(R.id.sub_cart_minus);
            amount = itemView.findViewById(R.id.sub_cart_qty);
//            menuImg =  itemView.findViewById();
//            menuTitle = itemView.findViewById(R.id)
        }
    }
}
