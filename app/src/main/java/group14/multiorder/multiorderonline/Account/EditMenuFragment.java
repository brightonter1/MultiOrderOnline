package group14.multiorder.multiorderonline.Account;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.SelectPhotoDialog;
import group14.multiorder.multiorderonline.obj.Menu;
import group14.multiorder.multiorderonline.util.UniversalImageLoader;

public class EditMenuFragment extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener{
    public static final String TAG = "EditMenuFragment";
    private DatabaseReference _dataRef;
    TextView _title;
    EditText _price;
    EditText _description;
    ImageView _editImg;
    Button saveButton;
    Bundle bundle;

    Menu newMenu;

    //vars
    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;
    private byte[] mUploadBytes;
    private double mProgress = 0;

    private int checkImg = 0;

    private Menu _menu = new Menu();

    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        newMenu = new Menu();
        super.onActivityCreated(savedInstanceState);
        _title = getView().findViewById(R.id.edit_menu_title);
        _price = getView().findViewById(R.id.edit_menu_price);
        _description = getView().findViewById(R.id.edit_menu_description);
        _editImg = getView().findViewById(R.id.edit_menu_image);
        bundle = this.getArguments();
        _title.setText(bundle.get("menutitle").toString());
        _price.setText(bundle.get("menuprice").toString());
        _description.setText(bundle.get("menudescription").toString());
        Picasso.with(getContext())
                .load(bundle.getString("menuimage").toString())
                .fit()
                .centerCrop()
                .into(_editImg);
        _dataRef = FirebaseDatabase.getInstance().getReference("Menus/"+bundle.getString("shoptitle").toString());


        newMenu.setImage(bundle.getString("menuimage"));
        newMenu.setTitle(bundle.getString("menutitle"));
        newMenu.setShop_id(bundle.getInt("menushopID"));



        saveButton = getView().findViewById(R.id.edit_menu_save_btn);
        postInit();
        selectImageInit();

    }


    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePath: setting the image to imageview");
        UniversalImageLoader.setImage(imagePath.toString(), _editImg);
        //assign to global variable
        mSelectedBitmap = null;
        mSelectedUri = imagePath;
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        _editImg.setImageBitmap(bitmap);
        //assign to a global variable
        mSelectedUri = null;
        mSelectedBitmap = bitmap;
    }

    private void selectImageInit(){
        _editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "getImageBitmap: setting the image to imageview");
                checkImg = 1;
                SelectPhotoDialog dialog = new SelectPhotoDialog();
                dialog.show(getFragmentManager(), getString(R.string.dialog_select_photo));
                dialog.setTargetFragment(EditMenuFragment.this, 1);
            }
        });
    }
    private void postInit(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Post image ");

                newMenu.setDescription(_description.getText().toString());
                newMenu.setPrice(_price.getText().toString());
                Log.d("System", bundle.getString("menuimage") + bundle.getString("menutitle")
                        + String.valueOf((bundle.getInt("menushopID"))) + _description.getText().toString() + _price.getText().toString());


                if(!_title.getText().toString().isEmpty()
                        &&!_description.getText().toString().isEmpty()
                        &&!_price.getText().toString().isEmpty()
                        &&checkImg == 1){
                    if(mSelectedBitmap != null && mSelectedUri == null){
                        UploadImage(mSelectedBitmap);
                    }
                    else if(mSelectedBitmap == null && mSelectedUri != null){
                        UploadImage(mSelectedUri);
                    }

                }else if(!_title.getText().toString().isEmpty()
                        &&!_description.getText().toString().isEmpty()
                        &&!_price.getText().toString().isEmpty()
                        &&checkImg == 0){


                    _dataRef.child(bundle.getString("menutitle")).setValue(newMenu);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new MenuFragment())
                            .addToBackStack(null)
                            .commit();
                }
                        else{
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void UploadImage(Bitmap bitmap){
        Log.d(TAG, "uploadNewPhoto: uploading a new image bitmap to storage");
        EditMenuFragment.BackgroundImageResize resize = new EditMenuFragment.BackgroundImageResize(bitmap);
        //PostFragment.BackgroundImageResize resize = new PostFragment.BackgroundImageResize(bitmap);
        Uri uri = null;
        resize.execute(uri);
    }
    private void UploadImage(Uri imagePath){
        Log.d(TAG, "uploadNewPhoto: uploading a new image uri to storage");
        EditMenuFragment.BackgroundImageResize resize = new EditMenuFragment.BackgroundImageResize(null);
        resize.execute(imagePath);
    }
    public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }
    public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]>{
        Bitmap _bitmap;

        public BackgroundImageResize(Bitmap bitmap) {
            this._bitmap = bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected byte[] doInBackground(Uri... uris) {
            Log.d(TAG, "doInBackground: Strat");

            if(_bitmap == null){
                try {
                    _bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uris[0]);

                }catch (IOException e){
                    Log.e(TAG, "doInBackground: IOException: "+ e.getMessage());
                }
            }
            byte[] bytes = null;
            Log.d(TAG, "doInBackground: megabytes before compression: "+ _bitmap.getByteCount()/1000000);
            bytes = getBytesFromBitmap(_bitmap, 100);
            Log.d(TAG, "doInBackground: megabytes before compression: "+ bytes.length/1000000);
            return bytes;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            mUploadBytes = bytes;
            excecuteUploadTask();
        }


    }

    private void excecuteUploadTask(){
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Menus/"+newMenu.getTitle());

        UploadTask uploadTask = storageReference.putBytes(mUploadBytes);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Post Success", Toast.LENGTH_SHORT).show();

                //insert the download uri into firebase database
                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String UI = uri.toString();
                        //System.out.print("URI = "+UI);
                        Log.d(TAG, "URI = "+UI);
                        //reference = FirebaseDatabase.getInstance().getReference();
                        newMenu.setImage(UI);
                        //_menu.setDescription(_description.getText().toString());
                        //post.setPost_id(postId);

                        //_menu.setPrice(_price.getText().toString());
                        //_menu.setTitle(_name.getText().toString());
                        //_menu.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        reference = FirebaseDatabase.getInstance().getReference();
                        reference.child("Menus")
                                .child(newMenu.getTitle())
                                //.child(_name.getText().toString())
                                //.child(postId);
                                .setValue(newMenu);
                        //resetFields();
                        Log.d(TAG, "OnSuccess: firebase download url"+ UI);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new MenuFragment())
                                .addToBackStack(null)
                                .commit();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.print("ERERER");
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "could not upload photo", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double currentProgress = (100*taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                if(currentProgress > (mProgress+15)){
                    mProgress = (100*taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Log.d(TAG, "onProgress: upload is "+ mProgress+ "& done");
                    Toast.makeText(getActivity(), mProgress + "%", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
