package group14.multiorder.multiorderonline.Cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    //Cart cart;
    private int _Total;
    private TextView to;

    public CartAdapter(Context _context, List _cart, int total, TextView totalP) {
        this._context = _context;
        this._cart = _cart;
        this._Total = total;
        this.to = totalP;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_sub_cart, viewGroup, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder cartViewHolder, int i) {
        final Menu _currentMenu = _cart.get(i);
        cartViewHolder.menuTitle.setText(_currentMenu.getTitle());
        cartViewHolder.menuPrice.setText(String.valueOf(Integer.parseInt(_currentMenu.getPrice().replace("฿", ""))*Integer.parseInt(_currentMenu.getAmount()))+"฿");
        cartViewHolder.amount.setText(_currentMenu.getAmount());
        Picasso.with(_context)
                .load(_currentMenu.getImage())
                .fit()
                .centerCrop()
                .into(cartViewHolder.menuImg);
        cartViewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus(cartViewHolder.amount, cartViewHolder.menuPrice, _cart.get(cartViewHolder.getAdapterPosition()), cartViewHolder.getAdapterPosition());
            }
        });

        cartViewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus(cartViewHolder.amount, cartViewHolder.menuPrice, _cart.get(cartViewHolder.getAdapterPosition()), cartViewHolder.getAdapterPosition());
            }
        });
        cartViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu um = _cart.get(cartViewHolder.getAdapterPosition());
                _Total = (_Total)-(Integer.parseInt(um.getPrice().replace("฿", "")) * Integer.parseInt(cartViewHolder.amount.getText().toString()));
                to.setText(String.valueOf(_Total)+"฿");
                _cart.remove(cartViewHolder.getAdapterPosition());
                notifyItemRemoved(cartViewHolder.getAdapterPosition());
                notifyItemRangeChanged(cartViewHolder.getAdapterPosition(), _cart.size());
                Cart updateCart = new Cart();
                for(Menu ct : _cart){
                    updateCart.addMenu(ct);
                }
                UpdateCart(updateCart);

            }
        });
    }

    private void plus(TextView pText, TextView priceText, Menu upM, int position){


        int am = Integer.parseInt(pText.getText().toString())+1;


        String _stringPrice = upM.getPrice().replace("฿", "");
        int _price = Integer.parseInt(_stringPrice)*am;
        pText.setText(String.valueOf(am));
        priceText.setText(String.valueOf(_price)+"฿");

        _Total = (_Total-((am-1)*Integer.parseInt(_stringPrice)))+_price;
        to.setText(String.valueOf((_Total))+"฿");
        Menu _newMenu = new Menu();
        _newMenu.setAmount(String.valueOf(am));
        _newMenu.setTitle(upM.getTitle());
        _newMenu.setPrice(_stringPrice);
        _newMenu.setImage(upM.getImage());
        _newMenu.setDescription(upM.getDescription());
        _cart.set(position, _newMenu);
        Cart updateCart = new Cart();
        for(Menu ct : _cart){
            updateCart.addMenu(ct);
        }
        UpdateCart(updateCart);

    }
    private void minus(TextView pText, TextView priceText, Menu upM, int position){
        int am = Integer.parseInt(pText.getText().toString());
        if(am > 1){
            am = am-1;
            String _stringPrice = upM.getPrice().replace("฿", "");
            int _price = Integer.parseInt(_stringPrice)*am;
            pText.setText(String.valueOf(am));
            priceText.setText(String.valueOf(_price)+"฿");

            _Total = (_Total-((am+1)*Integer.parseInt(_stringPrice)))+_price;
            to.setText(String.valueOf((_Total))+"฿");
        }


    }

    private void UpdateCart(Cart _update){
        _fileStore.collection("carts")
                .document(_mAuth.getCurrentUser().getUid())
                .set(_update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("CartAdapter", "up date cart success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("CartAdapter", "Fial to update CART "+ e.getMessage());
            }
        });

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
        public ImageButton delete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            menuImg = itemView.findViewById(R.id.sub_cart_image);
            menuTitle = itemView.findViewById(R.id.sub_cart_name);
            menuPrice = itemView.findViewById(R.id.sub_cart_price);
            plus = itemView.findViewById(R.id.sub_cart_add);
            minus = itemView.findViewById(R.id.sub_cart_minus);
            amount = itemView.findViewById(R.id.sub_cart_qty);
            delete = itemView.findViewById(R.id.sub_cart_delete);


        }
    }
}
