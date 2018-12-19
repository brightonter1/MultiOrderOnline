package group14.multiorder.multiorderonline.Account;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.OrderDealer;
import group14.multiorder.multiorderonline.obj.Store;

public class ProgressOrderFragment extends Fragment {
    FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    DatabaseReference _daatabaseRef;
    List<OrderDealer> _SuplierOrder = new ArrayList<>();
    HistorySuplierAdapter _hisAdapter;
    Store myStore = new Store();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suplier_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showOrder();
    }

    void showOrder(){
        final RecyclerView orderList = getView().findViewById(R.id.suplier_history_list);
        orderList.setHasFixedSize(true);
        orderList.setLayoutManager(new LinearLayoutManager(getActivity()));
        _daatabaseRef = FirebaseDatabase.getInstance().getReference("Store/");//+_mAuth.getCurrentUser().getUid());
        _daatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Store store = ds.getValue(Store.class);
                    //Toast.makeText(getActivity(), store.getUser_id()+" " +_mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                    if(store.getUser_id().equals(_mAuth.getCurrentUser().getUid())){
                        myStore = store;
                    }
                }
                Log.d("System", "OderDealer/shop id ::"+String.valueOf(myStore.getShop_id()));
                DatabaseReference _dRef = FirebaseDatabase.getInstance().getReference("OderDealer/shop id :"+String.valueOf(myStore.getShop_id()));
                _dRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        _SuplierOrder.clear();
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            OrderDealer ord = data.getValue(OrderDealer.class);
                            if(ord.get_status().equals("shipping now")){
                                _SuplierOrder.add(ord);
                            }
                            Log.d("System", String.valueOf(ord.getShop_id())+" " +String.valueOf(ord.getOrderid()));
                        }
                        _hisAdapter = new HistorySuplierAdapter(getActivity(), _SuplierOrder);
                        orderList.setAdapter(_hisAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
