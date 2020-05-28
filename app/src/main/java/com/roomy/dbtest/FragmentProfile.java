package com.roomy.dbtest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class FragmentProfile extends Fragment {
    //  image loade
    public int REQUEST_IMAGE_CAPTURE = 1;
    public ImageView imageView;
    Bitmap bitmap;





    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("profile");
    private ImageButton edit;
    private RecyclerView recyclerView;
    private List<Profile> profiles = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private ProfileAdapter adapter;
    View root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        edit = root.findViewById(R.id.edit_profile);
        recyclerView = root.findViewById(R.id.recv);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProfileAdapter(profiles);
        recyclerView.setAdapter(adapter);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {

       final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {//sign up or sign in

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading " + user.getDisplayName() + " missions, please wait...");
                    progressDialog.show();

                    users.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            profiles.clear();
//load from DB
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Profile profile1 = snapshot.getValue(Profile.class);
                                    profiles.add(profile1);
                                    adapter = new ProfileAdapter(profiles);
                                    recyclerView.setAdapter(adapter);
                                }
                                adapter.notifyDataSetChanged();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                View dialogView  = getLayoutInflater().inflate(R.layout.profile_dialog,null);

                                final EditText name = dialogView.findViewById(R.id.profile_name_dialog);
                                final EditText age= dialogView.findViewById(R.id.et_age);
                                final EditText location = dialogView.findViewById(R.id.profile_location_dialog);
                                final EditText gender = dialogView.findViewById(R.id.profile_gender_dialog);
                                final EditText education = dialogView.findViewById(R.id.profile_eduction_dialog);
                                final EditText aboutme= dialogView.findViewById(R.id.profile_aboutme_dialog);

                                //image
                                  // image loader
                                 final ImageView image_profile =dialogView.findViewById(R.id.profile_image_dialog_);


                                final ImageButton btn_to_pic = dialogView.findViewById(R.id.takee_picture);
                                btn_to_pic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

                                    }
                                });
                                image_profile.setImageBitmap(bitmap);



                                ////

                                builder.setView(dialogView).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String text = name.getText().toString();
                                        String text1= age.getText().toString();
                                        String text2 = gender.getText().toString();
                                        String text3 = location.getText().toString();
                                        String text4 = education.getText().toString();
                                        String text5=aboutme.getText().toString();

                                        String bit_str = decodeBitmapTostring(bitmap);




                                        Profile profile = new Profile(text,text1,text2,text3,text4,text5,bit_str);
                                        profiles.add(profile);
                                        adapter.notifyItemInserted(profiles.size()-1);
//                                        users.child(firebaseAuth.getCurrentUser().getUid()).setValue(profile);

                                        //update the database
                                        users.child(firebaseAuth.getCurrentUser().getUid()).setValue(profiles);

                                        //Snackbar.make(coordinatorLayout,"Mission added",Snackbar.LENGTH_LONG).show();
                                    }
                                }).show();

                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }};

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView  = getLayoutInflater().inflate(R.layout.profile_dialog,null);

                final EditText name = dialogView.findViewById(R.id.profile_name_dialog);
                final EditText age= dialogView.findViewById(R.id.et_age);
                final EditText location = dialogView.findViewById(R.id.profile_location_dialog);
                final EditText gender = dialogView.findViewById(R.id.profile_gender_dialog);
                final EditText education = dialogView.findViewById(R.id.profile_eduction_dialog);
                final EditText aboutme= dialogView.findViewById(R.id.profile_aboutme_dialog);

               final ImageView image_profile =dialogView.findViewById(R.id.profile_image_dialog_);
                final ImageButton btn_to_pic = dialogView.findViewById(R.id.takee_picture);

                btn_to_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }
                });
                image_profile.setImageBitmap(bitmap);
                builder.setView(dialogView).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String text = name.getText().toString();
                        String text1= age.getText().toString();
                        String text2 = gender.getText().toString();
                        String text4 = education.getText().toString();
                        String text5= aboutme.getText().toString();
                        String text3 = location.getText().toString();
                      //  image loader

                        String bit_str = decodeBitmapTostring(bitmap);


                        Profile profile = new Profile(text,text1,text2,text3,text4,text5,bit_str);
                        profiles.remove(0);
                        profiles.add(profile);
                        adapter = new ProfileAdapter(profiles);
                        recyclerView.setAdapter(adapter);
//                                        users.child(firebaseAuth.getCurrentUser().getUid()).setValue(profile);

                        //update the database
                        users.child(firebaseAuth.getCurrentUser().getUid()).setValue(profiles);

                        //Snackbar.make(coordinatorLayout,"Mission added",Snackbar.LENGTH_LONG).show();
                    }
                }).show();

            }
        });



        return root;

    }






// image loader

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            bitmap = (Bitmap) data.getExtras().get("data");
            //image_profile.setImageBitmap(bitmap);
            //   getActivity().getIntent().putExtra("profile image",bitmap);

        }

    }


    public String decodeBitmapTostring(Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] data = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(data,Base64.DEFAULT);

        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }





    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);


    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }



}




