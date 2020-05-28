package com.roomy.dbtest;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ArticleViewHolder> {

    Context context1;

    private List<Profile> profiles;
    Context context;

    public ProfileAdapter(List<Profile> articles) {
        this.profiles = articles;
        //this.context=context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
        context1 = recyclerView.getContext();

    }

    interface NewsListener {
        void onArticleClikced(int position, View view);

    }

    NewsListener listener;

    public void setListener(NewsListener listener) {
        this.listener = listener;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView age;
        TextView city;
        TextView education;
        TextView gender;
        TextView aboutme;
        CircleImageView image;



        public ArticleViewHolder(@NonNull final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.profile_name);
            age=itemView.findViewById(R.id.profile_age_dialog);
            city = itemView.findViewById(R.id.text_location);
            education = itemView.findViewById(R.id.text_education);
            gender = itemView.findViewById(R.id.text_gender);
            aboutme=itemView.findViewById(R.id.text_information);
            image = itemView.findViewById(R.id.profile_image_adapter);
/*

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onArticleClikced(getAdapterPosition(),itemView);
                }
            });
*/

        }
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_card,viewGroup,false);
        ArticleViewHolder holder = new ArticleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int i) {

        Profile profile = profiles.get(i);

        //**************************************************************************/
        //   Glide.with(context).load(article.getPhoto()).into(articleViewHolder.photo);
        //**************************************************************************/


        articleViewHolder .name.setText(profile.getName());
        articleViewHolder.age.setText(profile.getAge());
        articleViewHolder .education.setText(profile.getEducation());
        articleViewHolder .city.setText(profile.getCity());
        articleViewHolder .gender.setText(profile.getGender());
        articleViewHolder.aboutme.setText(profile.getAboutme());

        Bitmap b= decodeBase64(profile.getPic());

        articleViewHolder.image.setImageBitmap(b);
        //Bitmap bitmap = articles_photos.get(i);
       /* articleViewHolder .body.setText(article.getBody());
        articleViewHolder .tittle.setText(article.getTitle());
       */ //articleViewHolder .photo.setImageBitmap(article.getPhoto());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }




}

