package group14.multiorder.multiorderonline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SelectPhotoDialog extends DialogFragment{

    private static final String TAG = "SelectPhotoDialog";
    private static final int  PICKFILE_REQUEST_CODE = 1234;
    private static final int  CAMERA_REQUEST_CODE = 4321;

    public interface onPhotoSelectedLister{
        void getImagePath(Uri imagePath);
        void getImageBitmap(Bitmap bitmap);
    }

    onPhotoSelectedLister _OnPhotoSelectedLister;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_selectphoto,
                container,
                false);
        TextView selectPhoto = view.findViewById(R.id.dialog_chosephoto);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: accessing phones memory");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        TextView takePhoto = view.findViewById(R.id.dialog_chosephoto);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starting camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //pick from mem
        if(requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();
            Log.d(TAG, "OnActivityResult: image uri" + selectedImageUri);
            // send uri to post
            _OnPhotoSelectedLister.getImagePath(selectedImageUri);
            getDialog().dismiss();
        }
        //photo with camera
        else if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onACitivitiResult: took new photo");
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            // send bitmap to psot
            _OnPhotoSelectedLister.getImageBitmap(bitmap);
            getDialog().dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        try {
            _OnPhotoSelectedLister = (onPhotoSelectedLister) getActivity();
        }catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastException: "+ e.getMessage());
        }
        super.onAttach(context);
    }
}
