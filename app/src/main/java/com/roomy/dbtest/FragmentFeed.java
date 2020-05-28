package com.roomy.dbtest;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFeed extends Fragment {

//notification
NotificationManager NotManager;
    final int NOTIF_ID = 1;



//location
    EditText resultTv;
    String stringl_location;


    FusedLocationProviderClient client;
    double latitude,longitude;
    final String WHEATHER_SERVICE_LINK  = "http://api.openweathermap.org/data/2.5/weather?id=2172797&APPID=5bda833ea98063658162aac5ac577075&units=metric&";//lat=32.08337216&lon=34.77137702
    final int LOCATION_PERMISSION_REQUEST = 1;



    private List<Post> feed = new ArrayList<>();
    private List<Post> feed_test = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PostAdapter adapter;

    private android.support.design.widget.FloatingActionButton add_post;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("feed");

    View rootView;

    public FragmentFeed() {
        // Required empty public constructor
    }

    String name_name="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_feed, container, false);
        add_post = rootView.findViewById(R.id.add_post);

        final FirebaseUser user = firebaseAuth.getCurrentUser();


        //notification
        NotManager = ( NotificationManager ) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );


        //locatin
        resultTv = rootView.findViewById(R.id.post_location);


        if(Build.VERSION.SDK_INT>=23) {
            int hasLocationPermission = getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if(hasLocationPermission!= PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_REQUEST);
            }
            else getLocation();
        }
        else getLocation();





        recyclerView = rootView.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(feed_test, getActivity());
        recyclerView.setAdapter(adapter);


        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.mission_dialog, null);



                final EditText date = dialogView.findViewById(R.id.post_date);
                final EditText deposit = dialogView.findViewById(R.id.post_deposit);
                final EditText location = dialogView.findViewById(R.id.post_location);
                final EditText romates = dialogView.findViewById(R.id.post_numberroomats);
                final EditText rooms = dialogView.findViewById(R.id.post_rooms);
                final EditText floor = dialogView.findViewById(R.id.post_floor);
                final EditText description = dialogView.findViewById(R.id.post_information);
                final EditText phone_number = dialogView.findViewById(R.id.post_phone_number);
                final TextView name_name1 = dialogView.findViewById(R.id.post_name_name);
                location.setHint(stringl_location);

                name_name1.setText(user.getDisplayName());

                builder.setView(dialogView).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {




                        String text = date.getText().toString();
                        String text1 = deposit.getText().toString();
                        String text2 = stringl_location;

                        if (("").equals(location.getText().toString())) {

                            // text2 = stringl_location;
                            Log.i("******", "inside dialog- if not empty -" + text2);
                        } else {
                            text2 = location.getText().toString();
                            Log.i("******", "inside dialog- if is empty -" + text2);
                        }
                        String text3 = romates.getText().toString();
                        String text4 = rooms.getText().toString();
                        String text5 = floor.getText().toString();
                        String text6 = description.getText().toString();
                        String text7 = phone_number.getText().toString();
                        String text8 = name_name1.getText().toString();


//                        if (TextUtils.isEmpty(text))
//                            date.setError(" date is required!");
//                        else if (TextUtils.isEmpty(text1))
//                            deposit.setError(" deposit is required!");
//                        else if (TextUtils.isEmpty(text2))
//                            location.setError(" location is required!");
//                        else if (TextUtils.isEmpty(text3))
//                            romates.setError(" enter number!");
//                        else if (TextUtils.isEmpty(text4))
//                            rooms.setError(" enter number of rooms!");
//                        else if (TextUtils.isEmpty(text5))
//                            floor.setError("enter floor number");
//                        else if (TextUtils.isEmpty(text6))
//                            description.setError("description is required!");
//                        else if (TextUtils.isEmpty(text7))
//                            phone_number.setError(" phone is required!");



                            feed_test.add(new Post(text, text1, text2, text6, text3, text4, text5, text7, text8));
                            recyclerView.setAdapter(adapter);
                            //adapter.notifyItemInserted(feed.size()-1);
                            users.setValue(feed_test);

                            //update the database
                            //users.child(firebaseAuth.getCurrentUser().getUid()).setValue(missions);

                            //Snackbar.make(coordinatorLayout,"Mission added",Snackbar.LENGTH_LONG).show();


                        //notif

                        String channelId = null;
                        if (Build.VERSION.SDK_INT >= 26) {

                            channelId = "some_channel_id";
                            CharSequence channelName = "Some Channel";
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                            notificationChannel.enableLights(true);
                            notificationChannel.setLightColor(Color.RED);
                            notificationChannel.enableVibration(true);
                            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                            NotManager.createNotificationChannel(notificationChannel);
                        }

                        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(getActivity(), channelId);
                        builder1.setSmallIcon(R.drawable.icon_rin)
                                .setContentTitle("new post")
                                .setContentText("Your post has been published successfully!");

                        builder1.setPriority(android.app.Notification.PRIORITY_MAX);

                        Notification notification = builder1.build();

                        notification.defaults = Notification.DEFAULT_VIBRATE;
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                        NotManager.notify(NOTIF_ID,notification);









                    }
                      }).show();
            }
        });
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {

           /*     View headerView  = navigationView.getHeaderView(0);
                TextView userTv = headerView.findViewById(R.id.navigation_header_text_view);
*/
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {//sign up or sign in

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading " + user.getDisplayName() + " missions, please wait...");
                    progressDialog.show();

                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            feed_test.clear();

                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Post post = snapshot.getValue(Post.class);
                                    feed_test.add(post);
                                    adapter = new PostAdapter(feed_test, getActivity());
                                    recyclerView.setAdapter(adapter);
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


            }
        };


        return rootView;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==LOCATION_PERMISSION_REQUEST) {
            if(grantResults[0]!= PackageManager.PERMISSION_GRANTED) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Attention!").setMessage("Ypu must give location permission to the app if you want to knbow the wheather")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:"+getActivity().getPackageName()));
                                startActivity(intent);
                            }
                        }).show();
            }
            else getLocation();
        }
    }

    private void getLocation() {

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        LocationRequest request = LocationRequest.create();
        request.setInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        LocationCallback callback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location location = locationResult.getLastLocation();

                // coordinateTv.setText(location.getLatitude()+" , "+location.getLongitude());

                //Web service
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                StringRequest request1 = new StringRequest(WHEATHER_SERVICE_LINK + "lat=" + latitude + "&lon=" + longitude, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            StringBuffer sb  = new StringBuffer();

                            JSONObject rootObject = new JSONObject(response);

                            sb.append(rootObject.getString("name"));

                            stringl_location=sb.toString();
                            Log.i("******", "inside volly -" + stringl_location);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(request1);
                queue.start();

            }
        };

        if(Build.VERSION.SDK_INT>=23 && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            client.requestLocationUpdates(request,callback,null);

    }


}
