package com.roomy.dbtest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CoordinatorLayout coordinatorLayout;
  //  MissionAdapter adapter;
    List<Mission> missions = new ArrayList<>();
    String fullName;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment mFragment;
    FrameLayout frameLayout;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  /*      final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new MissionAdapter(missions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
*/
        frameLayout = findViewById(R.id.container11);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        coordinatorLayout = findViewById(R.id.coordinator);
        frameLayout.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogView  = getLayoutInflater().inflate(R.layout.sign_dialog,null);
                final EditText usernameEt = dialogView.findViewById(R.id.username_input);
                final EditText fullnameEt = dialogView.findViewById(R.id.fullname_input);
                final EditText passwordEt = dialogView.findViewById(R.id.password_input);
                switch (item.getItemId()) {
                    case R.id.item_sign_up:
                        builder.setView(dialogView).setPositiveButton("Register", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String username  = usernameEt.getText().toString();
                                fullName = fullnameEt.getText().toString();
                                String password = passwordEt.getText().toString();
                                //sign up the user
                                firebaseAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful())
                                            Snackbar.make(coordinatorLayout,"Sign up successful",Snackbar.LENGTH_SHORT).show();
                                        else
                                            Snackbar.make(coordinatorLayout,"Sign up failed",Snackbar.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).show();
                        break;


                    case R.id.item_profile:
                        frameLayout.setVisibility(View.VISIBLE);
                       // recyclerView.setVisibility(View.GONE);
                        mFragment = null;
                        fragmentManager = getSupportFragmentManager();
                        //create fragment transaction
                        fragmentTransaction = fragmentManager.beginTransaction();
                        FragmentProfile fragProfile = new FragmentProfile();
                        fragmentTransaction.replace(R.id.container11, fragProfile);
                        fragmentTransaction.commit();
                        break;

                    case R.id.item_feed:
                        frameLayout.setVisibility(View.VISIBLE);
                      //  recyclerView.setVisibility(View.GONE);
                        mFragment = null;
                        fragmentManager = getSupportFragmentManager();
                        //create fragment transaction
                        fragmentTransaction = fragmentManager.beginTransaction();
                        FragmentFeed fragmentFeed = new FragmentFeed();
                        fragmentTransaction.replace(R.id.container11, fragmentFeed);
                        fragmentTransaction.commit();
                        break;

                    case R.id.item_sign_in:
                        frameLayout.setVisibility(View.VISIBLE);
                        fullnameEt.setVisibility(View.GONE);
                        builder.setView(dialogView).setPositiveButton("Login", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String username  = usernameEt.getText().toString();
                                String password = passwordEt.getText().toString();

                                //Sign in the user

                                firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful())
                                            Snackbar.make(coordinatorLayout,"Sign in successful",Snackbar.LENGTH_SHORT).show();
                                        else
                                            Snackbar.make(coordinatorLayout,"Sign in failed",Snackbar.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).show();
                        break;
                    case R.id.item_sign_out:
                    frameLayout.setVisibility(View.GONE);
                        firebaseAuth.signOut();
                        break;
                }
                return false;
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                View headerView  = navigationView.getHeaderView(0);
                TextView userTv = headerView.findViewById(R.id.navigation_header_text_view);

                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null) {//sign up or sign in

                   if(fullName!=null)  { //sign up - update profile with full name

                       user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(fullName).build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               fullName = null;
                               if(task.isSuccessful())
                                   Snackbar.make(coordinatorLayout,user.getDisplayName() + " Welcome!!!",Snackbar.LENGTH_SHORT).show();
                           }
                       });
                   }

                   userTv.setText(user.getDisplayName() + " logged in");
              //     collapsing.setTitle(user.getDisplayName() + " missions");

                    navigationView.getMenu().findItem(R.id.item_sign_in).setVisible(false);
                    navigationView.getMenu().findItem(R.id.item_sign_up).setVisible(false);
                    navigationView.getMenu().findItem(R.id.item_sign_out).setVisible(true);
                    navigationView.getMenu().findItem(R.id.item_feed).setVisible(true);
                    navigationView.getMenu().findItem(R.id.item_profile).setVisible(true);
                    //navigationView.getMenu().findItem(R.id.item_requests).setVisible(true);

                    //Read the user data base - missions

                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading " + user.getDisplayName() + " profile, please wait...");
                    progressDialog.show();

                    users.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            missions.clear();

                            if(dataSnapshot.exists()) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Mission mission = snapshot.getValue(Mission.class);
                                    missions.add(mission);
                                }
                               // adapter.notifyDataSetChanged();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else {
                    userTv.setText("Please log in");
                 //   collapsing.setTitle("Please log in");

                    navigationView.getMenu().findItem(R.id.item_sign_in).setVisible(true);
                    navigationView.getMenu().findItem(R.id.item_sign_up).setVisible(true);
                    navigationView.getMenu().findItem(R.id.item_sign_out).setVisible(false);
                    navigationView.getMenu().findItem(R.id.item_feed).setVisible(false);
                    navigationView.getMenu().findItem(R.id.item_profile).setVisible(false);
                   // navigationView.getMenu().findItem(R.id.item_requests).setVisible(false);

                    missions.clear();
           //         adapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            //  Toast.makeText(this, "Home button pressed", Toast.LENGTH_SHORT).show();
            drawerLayout.openDrawer(Gravity.START);
        }
        return super.onOptionsItemSelected(item);
    }



}
