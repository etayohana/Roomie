package com.roomy.dbtest;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ArticleViewHolder> {
    private List<Post> feed;
    Context context1;
    MyNewsListener listener;
    String phone_number;
    Context context;

    public PostAdapter(List<Post> articles, Context context1) {
        this.feed = articles;
        this.context1 = context1;
        //this.context=context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
        context1 = recyclerView.getContext();

    }


    interface MyNewsListener {
        void onArticleClikced(int position, View view);

    }


    public void setListener(MyNewsListener listener) {
        this.listener = listener;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        ImageView Profile_photo;
//        ImageView Apartment_photo;
        TextView deposit;
        TextView location;
        TextView date;
        Button btn;
        TextView name;
//        TextView roomats;
//        TextView rooms;
//        TextView floor;
//        TextView phone;


        public ArticleViewHolder(View itemView) {
            super(itemView);


            date = itemView.findViewById(R.id.post_date_adapt);
            deposit = itemView.findViewById(R.id.post_deposit_adapt);
            location = itemView.findViewById(R.id.post_location_adapt);
            btn = itemView.findViewById(R.id.btn_to_more);
            name = itemView.findViewById(R.id.user_name_post_in_feed);

            btn.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            View tempView = (View) btn.getTag(R.layout.bigpost);
            Log.v("123", "ertwetrertterwettr");

            LayoutInflater li = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AlertDialog.Builder builder = new AlertDialog.Builder(context1);
            View dialogView = li.inflate(R.layout.bigpost, null);

            builder.setView(dialogView).setCancelable(true);
            final AlertDialog dialog1 = builder.create();

            Window window = dialog1.getWindow();
            window.setLayout(900, 600);
            dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog1.show();

            final FloatingActionButton btn_call = dialogView.findViewById(R.id.call);
            final TextView name = dialogView.findViewById(R.id.BPname);
            final TextView information = dialogView.findViewById(R.id.BPinformation);
            final TextView date = dialogView.findViewById(R.id.date);
            final TextView location = dialogView.findViewById(R.id.BPlocation);
            final TextView rooms = dialogView.findViewById(R.id.BProoms);
            final TextView roomats = dialogView.findViewById(R.id.BProomats);
            final TextView floor = dialogView.findViewById(R.id.Bpfloor);
            final TextView cost = dialogView.findViewById(R.id.cost);
            final ImageView profileimage = dialogView.findViewById(R.id.bigPostProfileimage);
            final ImageView roomimage = dialogView.findViewById(R.id.bigPostRoomImage);

            name.setText(feed.get(getAdapterPosition()).getName_name());
            //      information.setText(feed.get(getAdapterPosition()).getInformation());
            date.setText(feed.get(getAdapterPosition()).getDate());
            location.setText(feed.get(getAdapterPosition()).getLocation());
            rooms.setText(feed.get(getAdapterPosition()).getRooms());
            roomats.setText(feed.get(getAdapterPosition()).getRomates());
            floor.setText(feed.get(getAdapterPosition()).getFloor());
            //     cost.setText(feed.get(getAdapterPosition()).getInformation());

            profileimage.setImageResource(feed.get(getAdapterPosition()).getImagProfile());
            roomimage.setImageResource(R.drawable.roommenu);
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_number.toString()));
                    if (ActivityCompat.checkSelfPermission(context1, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context1.startActivity(intent);
                }
            });
        }
    }


    @Override
    public ArticleViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post,viewGroup,false);
        ArticleViewHolder holder = new ArticleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int position) {

        Post post = feed.get(position);

        /**************************************************************************/
     //   Glide.with(context).load(article.getPhoto()).into(articleViewHolder.photo);
        //**************************************************************************/


        articleViewHolder .date.setText(post.getDate());
        articleViewHolder .deposit.setText(post.getDeposit());
        articleViewHolder .location.setText(post.getLocation());
        articleViewHolder .name.setText(post.getName_name());
        phone_number = post.getPhone_number();
//        articleViewHolder.rooms.setText(post.getRooms());
//        articleViewHolder.roomats.setText(post.getRomates());
//        articleViewHolder.floor.setText(post.getFloor());
//        articleViewHolder.description.setText(post.getDescription());


        //Bitmap bitmap = articles_photos.get(i);
       /* articleViewHolder .body.setText(article.getBody());
        articleViewHolder .tittle.setText(article.getTitle());
       */ //articleViewHolder .photo.setImageBitmap(article.getPhoto());
    }



    @Override
    public int getItemCount() {
        return feed.size();
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



}
