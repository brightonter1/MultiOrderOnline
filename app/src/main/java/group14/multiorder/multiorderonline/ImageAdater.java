package group14.multiorder.multiorderonline;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import group14.multiorder.multiorderonline.obj.Store;

public class ImageAdater extends RecyclerView.Adapter<ImageAdater.ImageViewHolder> {
    private Context mContext;
    private List<Store> mPosts;

    public  ImageAdater(Context context, List<Store> uploads){
        mContext = context;
        mPosts = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_nearby, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, final int i) {
        final Store postCurrent = mPosts.get(i);
        imageViewHolder.textViewname.setText(postCurrent.getTitle());
        Picasso.with(mContext)
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
        return mPosts.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewname;
        public ImageView imageView;
        public ImageViewHolder(@NonNull final View itemView) {
            super(itemView);

            textViewname = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

        }
    }

}
