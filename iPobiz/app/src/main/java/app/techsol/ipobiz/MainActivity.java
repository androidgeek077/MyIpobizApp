package app.techsol.ipobiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    ImageView mSelectPostImgvw;
    EditText mTitleET;
    Button mSubmitPostBtn;
    String mTitleStr;


    private static final int RC_NAV_PHOTO_PICKER = 22;
    TextView tvName, tvAddress, tvStatus, tvHospital;
    ImageButton btnPickImg, btnAddStatusText;
    CircleImageView cvProfileImg;
    ProgressBar pbProfileUpdating;

    DatabaseReference mPostDbRef;
    StorageReference mFamilyProfileStorageRef;

    ValueEventListener currentMotherEventListener;
    private Uri selectedProfileImageUri;



    private EditText mEdtTxtCategory;
    private CardView mCVAddCategory;
    StorageReference profilePicRef;

    private String mStrCatTitle;
    private StorageReference mProfilePicStorageReference;
    private static final int RC_PHOTO_PICKER = 1;

    Button mSelectedImgBtn;
    ImageView profileImageView;
    String downloadUri;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    private PostModel PostModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPostDbRef = FirebaseDatabase.getInstance().getReference().child("Post");
        mProfilePicStorageReference = FirebaseStorage.getInstance().getReference().child("PostImage");
        profileImageView=findViewById(R.id.PostImgVw);
        pbProfileUpdating=findViewById(R.id.pbProfileUpdating);
        mTitleET=findViewById(R.id.TitleET);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProfilePicture();
            }
        });
        mSubmitPostBtn=findViewById(R.id.uploadImgBtn);
        mSubmitPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTitleET.getText().toString().isEmpty()){
                    mTitleET.setError("Please enter title of img");
                } else {
                    uploadImageOnServer();
                }
            }
        });



    }

    public void getProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            selectedProfileImageUri = selectedImageUri;
            profileImageView.setImageURI(selectedImageUri);
        }

    }
    private void uploadImageOnServer() {

        profilePicRef = mProfilePicStorageReference.child(selectedProfileImageUri.getLastPathSegment());
        profilePicRef.putFile(selectedProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(MainActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();

                profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadUri = uri.toString();
                        uploadProduct(downloadUri);
                    }
                });
            }
        });
    }
    public void uploadProduct(String ImageUrl){
        PostModel = new PostModel( mTitleET.getText().toString(),ImageUrl);
        mPostDbRef.push().setValue(PostModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(findViewById(R.id.getView), "Catagory Added", Snackbar.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(findViewById(R.id.getView), e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });


    }

}
