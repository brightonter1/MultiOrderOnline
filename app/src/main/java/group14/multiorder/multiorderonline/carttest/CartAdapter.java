package group14.multiorder.multiorderonline.carttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import group14.multiorder.multiorderonline.obj.Menu;
import java.util.List;

import group14.multiorder.multiorderonline.Cart.Cart;
import group14.multiorder.multiorderonline.R;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ImageViewHolder> {
    private FirebaseFirestore _fileStore = FirebaseFirestore.getInstance();
    private FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    private Context _context;
    private List<Menu> _cart;
    Cart cart;

    public CartAdapter(Context context, List cart){
        this._context = context;
        this._cart = cart;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_cart_item_test, viewGroup, false);
        return  new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, int i) {
        final Menu _currentMenu = _cart.get(i);
        imageViewHolder.cartTile.setText(_currentMenu.getTitle());
        Picasso.with(_context)
                .load(_currentMenu.getImage())
                .fit()
                .centerCrop()
                .into(imageViewHolder.cartImg);
        imageViewHolder.carBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _cart.remove(imageViewHolder.getAdapterPosition());
                notifyItemRemoved(imageViewHolder.getAdapterPosition());
                notifyItemRangeChanged(imageViewHolder.getAdapterPosition(), _cart.size());
                Cart updateCart = new Cart();
                for(Menu ct:  _cart){
                    updateCart.addMenu(ct);
                }

                _fileStore.collection("carts")
                        .document(_mAuth.getCurrentUser().getUid())
                        .set(updateCart).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                //Toast.makeText(_context, String.valueOf(imageViewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getCart(){
        cart = new Cart();
        _fileStore.collection("carts").document(_mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    cart = documentSnapshot.toObject(Cart.class);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return _cart.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView cartTile;
        public ImageView cartImg;
        public Button carBtn;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            cartTile = itemView.findViewById(R.id.cart_title);
            cartImg = itemView.findViewById(R.id.cart_img);
            carBtn = itemView.findViewById(R.id.cart_button);
        }
    }
}
