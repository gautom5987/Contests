package com.example.contests.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.contests.R;
import com.example.contests.adapters.ContestsAdapter;
import com.example.contests.databinding.ActivityMainBinding;
import com.example.contests.models.Contest;
import com.example.contests.utility.Constants;
import com.example.contests.utility.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ContestsAdapter.ContestListener {

    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBar.myToolBar);

        checkSharedPreferences();
        setListeners();
        getContests();
    }

    void checkSharedPreferences() {
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!sharedPreferences.getBoolean(Constants.IS_DATA_SET,false)) {
            editor.putBoolean(Constants.CODE_ATCODER,true);
            editor.putBoolean(Constants.CODE_CODECHEF,true);
            editor.putBoolean(Constants.CODE_CODEFORCES,true);
            editor.putBoolean(Constants.CODE_CODEFORCES_GYM,true);
            editor.putBoolean(Constants.CODE_CS_ACADEMY,true);
            editor.putBoolean(Constants.CODE_HACKER_EARTH,true);
            editor.putBoolean(Constants.CODE_HACKER_RANK,true);
            editor.putBoolean(Constants.CODE_KICK_START,true);
            editor.putBoolean(Constants.CODE_LEET_CODE,true);
            editor.putBoolean(Constants.CODE_TOPH,true);
            editor.putBoolean(Constants.CODE_TOPCODER,true);

            editor.putBoolean(Constants.IS_DATA_SET,true);

            editor.apply();
        }
    }

    private void setListeners() {
        binding.toolBar.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getContests() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://kontests.net/api/v1/all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            List<Contest> contestList = new ArrayList<>();
                            int n = jsonArray.length();

                            for(int i=0;i<n;i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Contest contest = new Contest();
                                contest.name = jsonObject.getString("name");
                                contest.startTime = jsonObject.getString("start_time");
                                contest.endTime = jsonObject.getString("end_time");
                                contest.url = jsonObject.getString("url");
                                contest.site = jsonObject.getString("site");
                                contest.status = jsonObject.getString("status");
                                if(sharedPreferences.getBoolean(contest.site,true)) {
                                    contestList.add(contest);
                                }
                            }
                            ContestsAdapter contestsAdapter = new ContestsAdapter(contestList,
                                    MainActivity.this);
                            binding.contestListView.setAdapter(contestsAdapter);
                            binding.contestListView.setVisibility(View.VISIBLE);
                            binding.progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: I was here");
                            e.printStackTrace();
                        }
                    }
                    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBar.setVisibility(View.GONE);
                binding.errorText.setVisibility(View.VISIBLE);
                Log.d(TAG, "onErrorResponse: Failed!");
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onContestClicked(Contest contest) {
        DateTime dateTime = new DateTime();

        String message = "Name : "+contest.name+"\n\n";
        message += "Platform : "+contest.site+"\n\n";
        if(contest.site.equals("CodeChef")) {
            message += "Start time : "+dateTime.getCodechefDate(contest.startTime)+" UTC\n\n";
            message += "End time : "+dateTime.getCodechefDate(contest.endTime)+" UTC\n\n";
        } else {
            message += "Start time : "+dateTime.getReadableDate(contest.startTime)+" UTC\n\n";
            message += "End time : "+dateTime.getReadableDate(contest.endTime)+" UTC\n\n";
        }


        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(contest.url));
                        startActivity(browserIntent);
                    }
                })
                .setNegativeButton("Ok",null)
                .show();
    }
}