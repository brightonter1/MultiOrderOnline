package group14.multiorder.multiorderonline.Account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.OrderDealer;

public class HistorySuplierAdapter  extends RecyclerView.Adapter<HistorySuplierAdapter.SuplierHolder>{
    Context _context;
    List<OrderDealer> _order = new ArrayList<>();

    public HistorySuplierAdapter(Context _context, List<OrderDealer> _order) {
        this._context = _context;
        this._order = _order;
    }

    @NonNull
    @Override
    public SuplierHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_suplier_sub_history, viewGroup, false);
        return new SuplierHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuplierHolder suplierHolder, int i) {
        OrderDealer _cureentOrder = _order.get(i);
        List menu = _cureentOrder.getMenu();
        suplierHolder._orderId.setText(String.valueOf(_cureentOrder.getOrderid()));
        suplierHolder._date.setText(_cureentOrder.getDate());
        suplierHolder._total.setText(String.valueOf(_cureentOrder.getTotal()));
        suplierHolder._status.setText(_cureentOrder.get_status());
        suplierHolder._address.setText(_cureentOrder.getAddress());
        HistorySuplierSubAdapter hisAdapter  = new HistorySuplierSubAdapter(_context, menu);
        suplierHolder._itemList.setHasFixedSize(true);
        suplierHolder._itemList.setLayoutManager(new LinearLayoutManager(_context));

        suplierHolder._itemList.setAdapter(hisAdapter);

    }

    @Override
    public int getItemCount() {
        return _order.size();
    }

    public class SuplierHolder extends RecyclerView.ViewHolder{
        public TextView _orderId, _date, _total, _status, _address;
        public RecyclerView _itemList;

        public SuplierHolder(@NonNull View itemView) {
            super(itemView);
            _orderId = itemView.findViewById(R.id.suplier_sub_orderid);
            _date = itemView.findViewById(R.id.suplier_sub_orderdate);
            _total = itemView.findViewById(R.id.suplier_sub_total);
            _status = itemView.findViewById(R.id.suplier_sub_status);
            _address = itemView.findViewById(R.id.suplier_sub_address);
            _itemList = itemView.findViewById(R.id.suplier_sub_list);
        }
    }
}
