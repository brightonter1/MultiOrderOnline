package group14.multiorder.multiorderonline.Adpter;

import android.content.Context;
import android.content.Intent;
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
import group14.multiorder.multiorderonline.Store.ViewMenuActivity;
import group14.multiorder.multiorderonline.obj.Store;

public class FastfoodAdapter extends RecyclerView.Adapter<FastfoodAdapter.ImageViewHolder>{
    private Context _context;
    private List<Store> _store;

    public FastfoodAdapter(Context context, List<Store> uploads){

        _context = context;
        _store = uploads;

    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_all, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FastfoodAdapter.ImageViewHolder imageViewHolder, int i) {
        final Store postCurrent = _store.get(i);
        imageViewHolder.textViewname.setText(postCurrent.getTitle());
        Picasso.with(_context)
                .load(postCurrent.getImage())
                .fit()
                .centerCrop()
                .into(imageViewHolder.imageView);

        imageViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewMenuActivity.class);
                intent.putExtra("STORE", postCurrent);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return _store.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewname;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewname = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}
