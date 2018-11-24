package group14.multiorder.multiorderonline.Adpter;


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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import group14.multiorder.multiorderonline.Cart.Cart;
import group14.multiorder.multiorderonline.Post;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ImageViewHolder>{

    private FirebaseFirestore _fileStore = FirebaseFirestore.getInstance();
    private FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    private Context _context;
    private List<Menu> _menus;
    private Menu _menuCurrent;
    private Cart cart;

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
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, int i) {
        _menuCurrent = _menus.get(i);
        imageViewHolder._menuName.setText(_menuCurrent.getTitle());
        imageViewHolder._menuDes.setText(_menuCurrent.getDescription());
        imageViewHolder._menuPrice.setText(_menuCurrent.getPrice() + "฿");
        Picasso.with(_context)
                .load(_menuCurrent.getImage())
                .fit()
                .centerCrop()
                .into(imageViewHolder._menuImg);
        imageViewHolder._menuPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MenuAdapter", imageViewHolder._menuName.getText().toString());
                addToCart(_menuCurrent);
            }
        });
    }

    private void addToCart(final Menu mm){
        cart = new Cart();
        Log.d("MenuAdapter", "add "+ mm.getTitle());
        _fileStore.collection("carts").document(_mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){

                        cart = documentSnapshot.toObject(Cart.class);
                        Log.d("MenuAdapter", "SIZE = "+cart.get_menuList().get(0).getTitle());


                    }else {
                        Log.d("MenuAdapter", "TT");
                    }
            }
        });
//        cart.addMenu(mm);
//        _fileStore.collection("carts")
//                .document(_mAuth.getCurrentUser().getUid())
//                .set(cart).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d("MenuAdapter", "add "+ mm.getTitle());
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("MenuAdapter", e.getMessage());
//            }
//        });
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
