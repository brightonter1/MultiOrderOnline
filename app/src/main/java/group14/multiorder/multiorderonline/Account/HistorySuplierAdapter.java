package group14.multiorder.multiorderonline.Account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;
import group14.multiorder.multiorderonline.obj.OrderCustomer;
import group14.multiorder.multiorderonline.obj.OrderDealer;

public class HistorySuplierAdapter  extends RecyclerView.Adapter<HistorySuplierAdapter.SuplierHolder>{
    Context _context;
    List<OrderDealer> _order = new ArrayList<>();
    String ch="";

    public HistorySuplierAdapter(Context _context, List<OrderDealer> _order, String ch) {
        this._context = _context;
        this._order = _order;
        this.ch = ch;
    }

    @NonNull
    @Override
    public SuplierHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_suplier_sub_history, viewGroup, false);
        return new SuplierHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SuplierHolder suplierHolder, int i) {
        OrderDealer _cureentOrder = _order.get(i);
        List menu = _cureentOrder.getMenu();
        if(this.ch.equals("fin")){
            suplierHolder._confirm.setVisibility(View.INVISIBLE);
            suplierHolder._cancle.setVisibility(View.INVISIBLE);
        }
        suplierHolder._orderId.setText(String.format("#ITEM123%03d", _cureentOrder.getOrderid()));
        suplierHolder._date.setText(_cureentOrder.getDate());
        suplierHolder._total.setText(String.valueOf(_cureentOrder.getTotal()).replace("฿","")+"฿");
        suplierHolder._status.setText(_cureentOrder.get_status());
        suplierHolder._address.setText(_cureentOrder.getAddress());
        HistorySuplierSubAdapter hisAdapter  = new HistorySuplierSubAdapter(_context, menu);
        suplierHolder._itemList.setHasFixedSize(true);
        suplierHolder._itemList.setLayoutManager(new LinearLayoutManager(_context));

        suplierHolder._itemList.setAdapter(hisAdapter);
        suplierHolder._confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm(suplierHolder);
            }
        });
        suplierHolder._cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel(suplierHolder);
            }
        });

    }
    List<OrderCustomer>  _orderCUs = new ArrayList<>();
    ArrayList<Menu> changStatusMenu = new ArrayList<>();
    OrderDealer nowOrder = new OrderDealer();

    private void cancel(final SuplierHolder sup){
        sup._progress.setVisibility(View.VISIBLE);
        DatabaseReference _ref = FirebaseDatabase.getInstance().getReference();
        int position = sup.getAdapterPosition();
        nowOrder = _order.get(position);
        changStatusMenu = new ArrayList<>();
        ArrayList<Menu> nowMenu = nowOrder.getMenu();
        for(Menu mm : nowMenu){

                mm.setStatus("Cancelled");
                nowOrder.set_status("Cancelled");

            changStatusMenu.add(mm);
        }
        nowOrder.setMenu(changStatusMenu);
//        _ref.child("OderDealer")
//                .child("shop id :"+String.valueOf(nowOrder.getShop_id()))
//                .child("order id :"+String.valueOf(nowOrder.getOrderid()))
//                .setValue(nowOrder);
        _ref.child("OrderCustomer")
                .child(nowOrder.getCus_uid())
                //.child("order id :"+String.valueOf(nowOrder.getOrderid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        _orderCUs.clear();
                        OrderCustomer cusOrder = new OrderCustomer();
                        ArrayList<Menu> cusMenu = new ArrayList<>();
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            if(data.getValue(OrderCustomer.class).getOrder_id() == nowOrder.getOrderid()){
                                cusOrder = data.getValue(OrderCustomer.class);
                            }

                        }

                        for(Menu mm: cusOrder.getMenu()){
                            Menu eM = new Menu();
                            eM = mm;
                            for(Menu cM: changStatusMenu){
                                if (mm.getTitle().equals(cM.getTitle()) && mm.getShop_id() == cM.getShop_id()){
                                    eM = cM;
                                }
                            }
                            cusMenu.add(eM);
                        }
                        cusOrder.setMenu(cusMenu);
                        DatabaseReference _dataRef = FirebaseDatabase.getInstance().getReference();
                        _dataRef.child("OrderCustomer")
                                .child(nowOrder.getCus_uid())
                                .child("order id :"+String.valueOf(nowOrder.getOrderid()))
                                .setValue(cusOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DatabaseReference _DeRef = FirebaseDatabase.getInstance().getReference();
                                _DeRef.child("OderDealer").
                                        child("shop id :"+String.valueOf(nowOrder.getShop_id()))
                                        .child("order id :"+String.valueOf(nowOrder.getOrderid()))
                                        .setValue(nowOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        sup._progress.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
    private void confirm(final SuplierHolder sup){
        sup._progress.setVisibility(View.VISIBLE);
        DatabaseReference _ref = FirebaseDatabase.getInstance().getReference();
        int position = sup.getAdapterPosition();
        nowOrder = _order.get(position);
        changStatusMenu = new ArrayList<>();
        ArrayList<Menu> nowMenu = nowOrder.getMenu();
        for(Menu mm : nowMenu){
            if(mm.getStatus().equals("inprogress")){
                mm.setStatus("shipping now");
                nowOrder.set_status("shipping now");
            }else  if(mm.getStatus().equals("shipping now")){
                mm.setStatus("Delivered");
                nowOrder.set_status("Delivered");
            }
            changStatusMenu.add(mm);
        }
        nowOrder.setMenu(changStatusMenu);
//        _ref.child("OderDealer")
//                .child("shop id :"+String.valueOf(nowOrder.getShop_id()))
//                .child("order id :"+String.valueOf(nowOrder.getOrderid()))
//                .setValue(nowOrder);
        _ref.child("OrderCustomer")
                .child(nowOrder.getCus_uid())
                //.child("order id :"+String.valueOf(nowOrder.getOrderid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        _orderCUs.clear();
                        OrderCustomer cusOrder = new OrderCustomer();
                        ArrayList<Menu> cusMenu = new ArrayList<>();
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            if(data.getValue(OrderCustomer.class).getOrder_id() == nowOrder.getOrderid()){
                                cusOrder = data.getValue(OrderCustomer.class);
                            }

                        }

                        for(Menu mm: cusOrder.getMenu()){
                            Menu eM = new Menu();
                            eM = mm;
                            for(Menu cM: changStatusMenu){
                                if (mm.getTitle().equals(cM.getTitle()) && mm.getShop_id() == cM.getShop_id()){
                                    eM = cM;
                                }
                            }
                            cusMenu.add(eM);
                        }
                        cusOrder.setMenu(cusMenu);
                        DatabaseReference _dataRef = FirebaseDatabase.getInstance().getReference();
                        _dataRef.child("OrderCustomer")
                                .child(nowOrder.getCus_uid())
                                .child("order id :"+String.valueOf(nowOrder.getOrderid()))
                                .setValue(cusOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DatabaseReference _DeRef = FirebaseDatabase.getInstance().getReference();
                                _DeRef.child("OderDealer").
                                child("shop id :"+String.valueOf(nowOrder.getShop_id()))
                                .child("order id :"+String.valueOf(nowOrder.getOrderid()))
                                .setValue(nowOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        sup._progress.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
    //private void cancle(){}

    @Override
    public int getItemCount() {
        return _order.size();
    }

    public class SuplierHolder extends RecyclerView.ViewHolder{
        public TextView _orderId, _date, _total, _status, _address;
        public RecyclerView _itemList;
        public Button _confirm, _cancle;
        public ProgressBar _progress;

        public SuplierHolder(@NonNull View itemView) {
            super(itemView);
            _orderId = itemView.findViewById(R.id.suplier_sub_orderid);
            _date = itemView.findViewById(R.id.suplier_sub_orderdate);
            _total = itemView.findViewById(R.id.suplier_sub_total);
            _status = itemView.findViewById(R.id.suplier_sub_status);
            _address = itemView.findViewById(R.id.suplier_sub_address);
            _itemList = itemView.findViewById(R.id.suplier_sub_list);
            _confirm = itemView.findViewById(R.id.suplier_sub_history_confirm);
            _cancle = itemView.findViewById(R.id.suplier_sub_history_cancle);
            _progress = itemView.findViewById(R.id.suplier_subhis_progress);
        }
    }
}
