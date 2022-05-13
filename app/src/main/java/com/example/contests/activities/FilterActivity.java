package com.example.contests.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.contests.R;
import com.example.contests.adapters.ContestsAdapter;
import com.example.contests.adapters.SitesAdapter;
import com.example.contests.databinding.ActivityFilterBinding;
import com.example.contests.databinding.ActivityMainBinding;
import com.example.contests.models.Contest;
import com.example.contests.models.Site;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements SitesAdapter.SiteListener {

    private ActivityFilterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
        getSites();
    }

    private void setListeners() {
        binding.toolBar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getSites() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://kontests.net/api/v1/sites";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            List<Site> siteList = new ArrayList<>();
                            int n = jsonArray.length();

                            for(int i=0;i<n;i++) {
                                JSONArray siteData = jsonArray.getJSONArray(i);
                                Site site = new Site();
                                site.name = siteData.getString(0);
                                site.code = siteData.getString(1);
                                site.url = siteData.getString(2);
                                siteList.add(site);
                            }
                            SitesAdapter sitesAdapter = new SitesAdapter(siteList,
                                    FilterActivity.this);
                            binding.sitesRecyclerView.setAdapter(sitesAdapter);
                            binding.sitesRecyclerView.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            Log.d("demo", "onResponse: I was here");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onSiteClicked(Site site) {

    }
}