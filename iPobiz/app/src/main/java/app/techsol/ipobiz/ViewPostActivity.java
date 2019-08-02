package app.techsol.ipobiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewPostActivity extends AppCompatActivity {
    private ViewFlipper simpleViewFlipper;
    private ArrayList<String> mCategories=new ArrayList<>();


    int countInt, incrementalCount;
    DatabaseReference ProductReference;
    RecyclerView mProductRecycVw;
    PostModel postImage;
    String count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        ProductReference= FirebaseDatabase.getInstance().getReference().child("Post");
        mProductRecycVw=findViewById(R.id.main_recycler_vw);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mProductRecycVw.setLayoutManager(mLayoutManager);




    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<PostModel> options=new FirebaseRecyclerOptions.Builder<PostModel>()
                .setQuery(ProductReference, PostModel.class)
                .build();

        FirebaseRecyclerAdapter<PostModel, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<PostModel, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final PostModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                holder.postTitle.setText(model.getPosttitle());
                Glide.with(getApplicationContext()).load(model.getPostimg()).into(holder.postImage);




            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_layout, viewGroup,false);
                ProductViewHolder productViewHolder=new ProductViewHolder(view);
                return productViewHolder;
            }
        };

        mProductRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class ProductViewHolder extends  RecyclerView.ViewHolder{


        ImageView postImage, AddedItem;
        TextView postTitle;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            postTitle = (TextView) itemView.findViewById(R.id.dashPostTitle);



        }
    }
}
    
    

