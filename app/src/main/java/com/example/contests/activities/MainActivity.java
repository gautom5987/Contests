package com.example.contests.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContestsAdapter.ContestListener {

    private ActivityMainBinding binding;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBar.myToolBar);

        setListeners();
        getContests();
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
                                contestList.add(contest);
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
                Log.d(TAG, "onErrorResponse: Failed!");
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onContestClicked(Contest contest) {

    }
}