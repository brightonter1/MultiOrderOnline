package group14.multiorder.multiorderonline.Account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import group14.multiorder.multiorderonline.BaseFragment;
import group14.multiorder.multiorderonline.History.HistoryAdapter;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.OrderCustomer;

public class TrackOrderFragment extends BaseFragment {
    FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    DatabaseReference _daatabaseRef;
    List<OrderCustomer> _CustomerOrder = new ArrayList<>();
    HistoryAdapter _hisAdapter;
    Toolbar toolbar;
    public TrackOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = setToolbar("Track Order");
        backBtn();
        ShowHis();
    }

    private void ShowHis(){
        final RecyclerView orderHisList = getView().findViewById(R.id.order_his_list);
        orderHisList.setHasFixedSize(true);
        orderHisList.setLayoutManager(new LinearLayoutManager(getActivity()));

        _daatabaseRef = FirebaseDatabase.getInstance().getReference("OrderCustomer/"+_mAuth.getCurrentUser().getUid());
        _daatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot orderhis: dataSnapshot.getChildren()){
                    _CustomerOrder.clear();
                    OrderCustomer ordercus = orderhis.getValue(OrderCustomer.class);
                    if(!ordercus.getStatus().equals("shipped")){
                        _CustomerOrder.add(ordercus);
                    }
                }
                _hisAdapter = new HistoryAdapter(getActivity(), _CustomerOrder);
                orderHisList.setAdapter(_hisAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public Toolbar setToolbar(String nPager) {
        return super.setToolbar(nPager);
    }

    @Override
    public void backBtn() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new AccountFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
