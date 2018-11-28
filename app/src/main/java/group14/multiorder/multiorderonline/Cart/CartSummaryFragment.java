package group14.multiorder.multiorderonline.Cart;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

public class CartSummaryFragment extends Fragment{
    private Cart cart;
    private List<Menu> _menuList = new ArrayList<Menu>();
    private FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore _fileStore = FirebaseFirestore.getInstance();

    private RecyclerView sumView;
    private CartSummaryAdapter sumAdapter;

    private Toolbar toolbar;
    public CartSummaryFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.account_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        TextView title = getActivity().findViewById(R.id.title_bar);
        title.setText("Summary");
        backBtn();
        showMenu();
    }
    private void showMenu(){
        sumView = getView().findViewById(R.id.summary_items);
        sumView.setHasFixedSize(true);
        sumView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cart = new Cart();
        _fileStore.collection("carts").document(_mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    cart = documentSnapshot.toObject(Cart.class);
                    ArrayList<Menu> mm = cart.get_menuList();
                    for(Menu aaa : mm){
                        //Log.d("CART","Add "+ aaa.getTitle());
                        _menuList.add(aaa);
                    }
                    sumAdapter = new CartSummaryAdapter(getActivity(), _menuList);
                    sumView.setAdapter(sumAdapter);
                    TextView total = getView().findViewById(R.id.summary_total);
                    total.setText("Total " + String.valueOf(cart.getTotal())+"฿");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public void backBtn(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_cart, new CartFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
