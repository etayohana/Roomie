package com.roomy.dbtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;

public class FragmentEditProfile extends android.support.v4.app.Fragment {


    public int REQUEST_IMAGE_CAPTURE = 1;
    public ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root2 = inflater.inflate(R.layout.fragment_edit_profile, container, false);


        imageView =root2.findViewById(R.id.profile_image);
        ImageButton imageBtn =root2.findViewById(R.id.image_btn);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        final EditText fullName = root2.findViewById(R.id.profileedit_name_dialog);
        final EditText age = root2.findViewById(R.id.et_age);
        final EditText gender = root2.findViewById(R.id.profileedit_gender_dialog);
        final EditText education = root2.findViewById(R.id.profileedit_eduction_dialog);
        final EditText location = root2.findViewById(R.id.profileedit_location_dialog);
        final EditText aboutme = root2.findViewById(R.id.profileedit_aboutme_dialog);





        Button doneEdit = root2.findViewById(R.id.done_edit);

        doneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getIntent().putExtra("full name",fullName.getText().toString());
                getActivity().getIntent().putExtra("age",age.getText().toString());
                getActivity().getIntent().putExtra("gender",gender.getText().toString());
                getActivity().getIntent().putExtra("education",education.getText().toString());
                getActivity().getIntent().putExtra("location",location.getText().toString());
                getActivity().getIntent().putExtra("aboutme",aboutme.getText().toString());

                Fragment fragmentProfile = new FragmentProfile();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container11, fragmentProfile);
                fragmentTransaction.commit();




            }
        });



        return root2;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            //   getActivity().getIntent().putExtra("profile image",bitmap);


        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}







