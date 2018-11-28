package group14.multiorder.multiorderonline.Cart;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;

import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import group14.multiorder.multiorderonline.MainActivity;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private Toolbar toolbar;
    Cart cart;
    List<Menu> _menuList = new ArrayList<Menu>();
    FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore _fileStore = FirebaseFirestore.getInstance();

    RecyclerView _cartView;
    CartAdapter _cartAdapter;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.account_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        TextView title = getActivity().findViewById(R.id.title_bar);
        title.setText("Shopping Cart");
        backBtn();
        showMenu();
        processBtn();
    }

    private void processBtn(){
        Button btn = getActivity().findViewById(R.id.cart_process);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_cart, new CartSummaryFragment())
                        .commit();
            }
        });
    }

    private void showMenu(){
        final TextView _totalPrice = getView().findViewById(R.id.account_total);
//        Toast.makeText(getActivity(), "asdasdsad", Toast.LENGTH_SHORT).show();
        _cartView = getView().findViewById(R.id.cart_recycler);
        _cartView.setHasFixedSize(true);
        _cartView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cart = new Cart();
        _fileStore.collection("carts").document(_mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){
                    //Toast.makeText(getActivity(), "asssssssssssssss", Toast.LENGTH_SHORT).show();
                    cart = documentSnapshot.toObject(Cart.class);
                    Log.d("CART", String.valueOf(cart.getSize()));
                    ArrayList<Menu> mm = cart.get_menuList();
                    for(Menu aaa : mm){
                        Log.d("CART","Add "+ aaa.getTitle());
                        _menuList.add(aaa);
                    }
                    _cartAdapter = new CartAdapter(getActivity(), _menuList, cart.getTotal(), _totalPrice);
                    _cartView.setAdapter(_cartAdapter);
                    ProgressBar cartProgress = getView().findViewById(R.id.cart_progress);
                    cartProgress.setVisibility(View.INVISIBLE);
                    _cartAdapter.notifyDataSetChanged();
                    _totalPrice.setText(String.valueOf(cart.getTotal())+"฿");

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "asdasdsad", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backBtn(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment.this.getActivity().finish();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_cart, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:
        }
        return super.onOptionsItemSelected(item);
    }
}
