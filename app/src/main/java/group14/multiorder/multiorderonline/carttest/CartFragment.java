package group14.multiorder.multiorderonline.carttest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import group14.multiorder.multiorderonline.obj.Menu;

import java.util.ArrayList;
import java.util.List;

import group14.multiorder.multiorderonline.Cart.Cart;
import group14.multiorder.multiorderonline.R;

public class CartFragment  extends Fragment{
    Cart cart;
    List<Menu> _menuList = new ArrayList<Menu>();
    FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore _fileStore = FirebaseFirestore.getInstance();

    RecyclerView _cartView;
    CartAdapter _cartAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart_test, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _cartView = getView().findViewById(R.id.cart_test);
        _cartView.setHasFixedSize(true);
        _cartView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cart = new Cart();
        _fileStore.collection("carts").document(_mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    cart = documentSnapshot.toObject(Cart.class);
                    Log.d("CART", String.valueOf(cart.getSize()));
                    ArrayList<Menu> mm = cart.get_menuList();
                    for(Menu aaa : mm){
                        Log.d("CART","Add "+ aaa.getTitle());
                        _menuList.add(aaa);
                    }
                    _cartAdapter = new CartAdapter(getActivity(), _menuList);
                    _cartView.setAdapter(_cartAdapter);
                    _cartAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
