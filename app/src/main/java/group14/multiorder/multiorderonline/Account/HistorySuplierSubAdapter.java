package group14.multiorder.multiorderonline.Account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

public class HistorySuplierSubAdapter extends RecyclerView.Adapter<HistorySuplierSubAdapter.HistoryViewHolder> {
    Context _context;
    List<Menu> _menu = new ArrayList<>();

    public HistorySuplierSubAdapter(Context _context, List<Menu> _menu) {
        this._context = _context;
        this._menu = _menu;
    }

    @NonNull
    @Override
    public HistorySuplierSubAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_suplier_sub_history_item, viewGroup, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorySuplierSubAdapter.HistoryViewHolder historyViewHolder, int i) {
        Menu _currentMenu = _menu.get(i);
        historyViewHolder._name.setText(_currentMenu.getTitle());
        historyViewHolder._price.setText(_currentMenu.getPrice());
        historyViewHolder._piece.setText(_currentMenu.getAmount());
        Picasso.with(_context)
                .load(_currentMenu.getImage())
                .fit()
                .centerCrop()
                .into(historyViewHolder._image);


    }

    @Override
    public int getItemCount() {
        return _menu.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView _name, _price, _piece;
        public ImageView _image;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.history_item_name);
            _price = itemView.findViewById(R.id.history_item_price);
            _piece = itemView.findViewById(R.id.history_item_each);
            _image = itemView.findViewById(R.id.history_item_img);
        }
    }
}
