package group14.multiorder.multiorderonline.Cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

public class CartSummaryAdapter extends RecyclerView.Adapter<CartSummaryAdapter.CartSummaryHolder> {
    private FirebaseFirestore _fileStore = FirebaseFirestore.getInstance();
    private FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    private Context _context;
    private List<Menu> _menu;

    public CartSummaryAdapter(Context _context, List<Menu> _menu) {
        this._context = _context;
        this._menu = _menu;
    }

    @NonNull
    @Override
    public CartSummaryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_sub_summary, viewGroup, false);
        return new CartSummaryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartSummaryHolder cartSummaryHolder, int i) {
        Menu _currentMenu = _menu.get(i);
        Picasso.with(_context)
                .load(_currentMenu.getImage())
                .fit()
                .centerCrop()
                .into(cartSummaryHolder.sumImg);
        cartSummaryHolder.sumTitle.setText(_currentMenu.getTitle());
        cartSummaryHolder.sumQty.setText("จำนวน "+_currentMenu.getAmount()+" ชิ้น");
        cartSummaryHolder.sumTotal.setText("ราคา "+ String.valueOf(Integer.parseInt(_currentMenu.getAmount())*Integer.parseInt(_currentMenu.getPrice().replace("฿","")))+" บาท");


    }

    @Override
    public int getItemCount() {
        return _menu.size();
    }

    public class CartSummaryHolder extends RecyclerView.ViewHolder{
        protected ImageView sumImg;
        protected TextView sumTitle;
        protected TextView sumQty;
        protected TextView sumTotal;
        public CartSummaryHolder(@NonNull View itemView) {
            super(itemView);
            sumImg = itemView.findViewById(R.id.sub_summary_img);
            sumTitle = itemView.findViewById(R.id.sub_summary_name);
            sumQty = itemView.findViewById(R.id.sub_summary_qty);
            sumTotal = itemView.findViewById(R.id.sub_summary_sub_total);

        }
    }
}
