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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import group14.multiorder.multiorderonline.R;

import group14.multiorder.multiorderonline.obj.Menu;
import group14.multiorder.multiorderonline.obj.OrderDealer;

public class CartSummaryFragment extends Fragment{
    private Cart cart;
    private List<Menu> _menuList = new ArrayList<Menu>();
    private FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore _fileStore = FirebaseFirestore.getInstance();

    private RecyclerView sumView;
    private CartSummaryAdapter sumAdapter;

    private Toolbar toolbar;
    public CartSummaryFragment(){}

    private ArrayList<Menu> _order;

    private DatabaseReference _databaseRef;
    private int orderCount;
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
        Button btn = getView().findViewById(R.id.summary_comfirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });
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
                    mm = sortMenu(mm);
                    _order = mm;
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
    DatabaseReference _databaseRefs;
    int coder;
    private void confirmOrder(){
        _databaseRefs = FirebaseDatabase.getInstance().getReference("Corder");
        _databaseRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map map = (Map) dataSnapshot.getValue();
                coder = Integer.parseInt(String.valueOf(map.get("count")));
                Log.d("System", String.valueOf(map.get("count")));
                coder++;
                Log.d("System", "This is code : " + coder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Map<String,Object> value = new HashMap<String,Object>();
        value.put("count", coder);
        Log.d("System", "coder kuy :  " + coder);
        _databaseRefs.updateChildren(value);

////        Corder corder = new Corder();
////        corder.setCount(0);
//        DatabaseReference _refC = FirebaseDatabase.getInstance().getReference("Corder");
//        //_refC.setValue(corder);
//        _refC.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot menuSnapshot : dataSnapshot.getChildren()){
//                    Corder a = menuSnapshot.getValue(Corder.class);
//                    orderCount = a.getCount()+1;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        EditText address = getView().findViewById(R.id.summary_address);
        String addressStr = address.getText().toString();
        _databaseRef = FirebaseDatabase.getInstance().getReference("OderDealer/");
        OrderDealer od = new OrderDealer();
        ArrayList<Menu> menuOrder = new ArrayList<>();

        for(int i = 0; i < _order.size() ;i++){
            if(i+1 < _order.size()){
                if(_order.get(i).getShop_id() == _order.get(i+1).getShop_id()){
                    od.setAddress(addressStr);
                    od.setOrderid(orderCount);
                    od.setShop_id(_order.get(i).getShop_id());
                    menuOrder.add(_order.get(i));
                    od.setMenu(menuOrder);
                    od.setTotal(Integer.parseInt(_order.get(i).getPrice().replace("฿", ""))*Integer.parseInt(_order.get(i).getAmount())+od.getTotal());
                }else{
                    od.setAddress(addressStr);
                    od.setOrderid(1);
                    od.setShop_id(_order.get(i).getShop_id());
                    menuOrder.add(_order.get(i));
                    od.setMenu(menuOrder);
                    od.setTotal(Integer.parseInt(_order.get(i).getPrice().replace("฿", ""))*Integer.parseInt(_order.get(i).getAmount())+od.getTotal());
                    _databaseRef.child("shop id :"+ String.valueOf(_order.get(i).getShop_id()))
                            .child("order id :"+String.valueOf(coder))
                            .setValue(od);
                    od = new OrderDealer();
                    menuOrder = new ArrayList<>();
                }

            }else{
                od.setAddress(addressStr);
                od.setOrderid(1);
                od.setShop_id(_order.get(i).getShop_id());
                menuOrder.add(_order.get(i));
                od.setMenu(menuOrder);
                od.setTotal(Integer.parseInt(_order.get(i).getPrice().replace("฿", ""))*Integer.parseInt(_order.get(i).getAmount())+od.getTotal());
                _databaseRef.child("shop id :"+ String.valueOf(_order.get(i).getShop_id()))
                        .child("order id :"+String.valueOf(coder))
                        .setValue(od);
                od = new OrderDealer();
                menuOrder = new ArrayList<>();
            }
        }



    }

    private ArrayList<Menu> sortMenu(ArrayList<Menu> sm){
        for(int i = 0 ; i < sm.size()-1 ; i++){
            for(int j = 0; j < sm.size()-i-1; j++){
                if(sm.get(j).getShop_id() > sm.get(j+1).getShop_id()){
                    Menu _sortm = sm.get(j);
                    sm.set(j, sm.get(j+1));
                    sm.set(j+1, _sortm);
                }
            }
        }
        return sm;
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
