package group14.multiorder.multiorderonline.History;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

public class HistorySubAdapter extends RecyclerView.Adapter<HistorySubAdapter.SubHisViewHolder> {
    private List<Menu> _menu;
    private Context _context;

    public HistorySubAdapter(List<Menu> _menu, Context _context) {
        this._menu = _menu;
        this._context = _context;
    }

    @NonNull
    @Override
    public HistorySubAdapter.SubHisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_order_history_viewdetail, viewGroup, false);
        return new SubHisViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorySubAdapter.SubHisViewHolder subHisViewHolder, int i) {
        Menu _currentMenu = _menu.get(i);
        subHisViewHolder._name.setText(_currentMenu.getTitle());
        subHisViewHolder._each.setText(_currentMenu.getAmount());
        subHisViewHolder._price.setText(String.valueOf(Integer.parseInt(_currentMenu.getPrice().replace("฿",""))*Integer.parseInt(_currentMenu.getAmount())));
        Picasso.with(_context)
                .load(_currentMenu.getImage())
                .fit()
                .centerCrop()
                .into(subHisViewHolder._img);
    }

    @Override
    public int getItemCount() {
        return _menu.size();
    }

    public class  SubHisViewHolder extends RecyclerView.ViewHolder{
        public TextView _name, _each, _price;
        public ImageView _img;
        public SubHisViewHolder(@NonNull View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.viewdetail_name);
            _each = itemView.findViewById(R.id.viewdetail_each);
            _price = itemView.findViewById(R.id.viewdetail_price);
            _img = itemView.findViewById(R.id.viewdetail_img);


        }
    }
}
