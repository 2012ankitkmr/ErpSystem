package kumar.ankit.erpsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Ankit on 09-07-2016.
 */
public class Subject_Main extends Fragment implements ScreenShotable{


    final static String DB_URL="https://erpsystem-1bb7f.firebaseio.com/";

    Firebase mRootRef;


    TreeMap<String, Map<String,Map<String,String>>> vDatabase = null;
    Map<String,String>chapDatabase = null;

    ArrayList<String> vid_ids = new ArrayList<String>();



    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String StudentID = "StudentID";
    public static final String loginKey = "loginKey";
    public static final String ClassNum = "ClassNum";
    SharedPreferences sharedpreferences;


    String SUBJECTHEAD=null;
    private View containerView;
    protected int res;
    private Bitmap bitmap;


    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                Subject_Main.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    public Subject_Main() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.subject_main, container, false);

        Bundle args = getArguments();
        String sub=null;
        if (args  != null && args.containsKey("Subject"))
             sub = args.getString("Subject");
        SUBJECTHEAD = sub;
        Toast.makeText(getActivity(), sub, Toast.LENGTH_SHORT).show();



        mRootRef = new Firebase(DB_URL);

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        return rootView;
    }
ProgressDialog progressDialog;

    @Override
    public void onStart()
    {
        super.onStart();

        progressDialog= new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching Data...");

        progressDialog.show();


        Firebase VideoRef = mRootRef.child("Videos");
        // Log.e("Here","In Start");
        VideoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vDatabase = dataSnapshot.getValue(TreeMap.class);
                String cls = sharedpreferences.getString(ClassNum,"9");

                Map<String,Map<String,String>>vidDatabase = vDatabase.get(cls);
                //   Log.e("Here","Refresh");
                    Map<String,String> mymap = vidDatabase.get(SUBJECTHEAD);
                    for(String pics: mymap.keySet())
                    {
                        vid_ids.add(mymap.get(pics).replace("pic","vid"));
                        //Log.e("Pics added: ",mymap.get(pics));
                    }


                //Chapter number
                ArrayList<String> chapnum = new ArrayList<>();
chapnum.clear();
                for(int i = 1; i<=vid_ids.size();i++)
                {
                    chapnum.add("Chapter "+String.valueOf(i)+": ");
                }


                ListView listView = (ListView) getActivity().findViewById(R.id.Chapter_list_heading_list);
                ArrayAdapter<String> Adapter;
                Adapter = new ArrayAdapter<String>(getActivity(), R.layout.mychapter_num_list, chapnum);
                listView.setAdapter(Adapter);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase ChapterRef = mRootRef.child("Chapters");
        // Log.e("Here","In Start");
        ChapterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             chapDatabase =dataSnapshot.getValue(Map.class);

                //Chapter Data
                ArrayList<String> chapdata = new ArrayList<>();

                chapdata.clear();
                for(int i = 0; i<vid_ids.size();i++)
                {
                    chapdata.add(chapDatabase.get(vid_ids.get(i)));
                }


                ListView listView = (ListView) getActivity().findViewById(R.id.Chapter_list);
                ArrayAdapter<String> Adapter;
                Adapter = new ArrayAdapter<String>(getActivity(), R.layout.mychapter_data_list, chapdata);
                listView.setAdapter(Adapter);
                progressDialog.dismiss();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Video_Show vsfragment = new Video_Show();
                        final Bundle bundle = new Bundle();
                        bundle.putString("Chapter", vid_ids.get(position));
//                        Log.i("BUNDLE", bundle.toString());
                        vsfragment.setArguments(bundle);
                        // Pass image index
                        // i.putExtra("Subject",vid.get(position).name);
                        // startActivity(i);

                        getFragmentManager().beginTransaction().replace(R.id.container, vsfragment).commit();

                    }
                });


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }




}
