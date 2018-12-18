package group14.multiorder.multiorderonline.History;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.OrderCustomer;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context _context;
    private List<OrderCustomer> cusorder;

    public HistoryAdapter(Context _context, List<OrderCustomer> cusorder) {
        this._context = _context;
        this.cusorder = cusorder;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_order_history_sub, viewGroup, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        OrderCustomer odc = cusorder.get(i);
        List _menu = odc.getMenu();
        historyViewHolder.order_id.setText(String.format("#ITEM123%03d", odc.getOrder_id()));
        historyViewHolder.order_date.setText(odc.getDate());
        historyViewHolder.order_status.setText(odc.getStatus());
        historyViewHolder.order_total.setText(String.valueOf(odc.getTotal()));
        historyViewHolder.his_sub_list.setHasFixedSize(true);
        historyViewHolder.his_sub_list.setLayoutManager(new LinearLayoutManager(_context));
        HistorySubAdapter sunAdapter = new HistorySubAdapter(_menu, _context);
        historyViewHolder.his_sub_list.setAdapter(sunAdapter);

    }

    @Override
    public int getItemCount() {
        return cusorder.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        public TextView order_id, order_date, order_status, order_total;
        public RecyclerView his_sub_list;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.suplier_sub_orderid);
            order_date = itemView.findViewById(R.id.order_his_sub_orderdate);
            order_status = itemView.findViewById(R.id.order_his_sub_status);
            order_total = itemView.findViewById(R.id.suplier_sub_total);
            his_sub_list = itemView.findViewById(R.id.suplier_sub_list);
        }
    }
}
