package com.markcorrado.funwithgit;

import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ListFragment {

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("One");
        stringArrayList.add("Two");
        stringArrayList.add("Three");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringArrayList);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        GitRestClient restClient = GitRestClient.getInstance(getString(R.string.server));
        restClient.setUserAgent("FunWithGit");
        restClient.get("hudl/hudlbot/commits", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
//                foodTruckArrayList = new ArrayList<FoodTruck>();
//                for (int i=0; i < response.length(); i++)
//                {
//                    try {
//                        JSONObject truckObject = response.getJSONObject(i);
//                        // Pulling items from the array
//                        String name = truckObject.getString("title");
//                        String copy = truckObject.getString("text");
//                        String latitude = truckObject.getString("latitude");
//                        String longitude = truckObject.getString("longitude");
//                        foodTruckArrayList.add(new FoodTruck(name, Double.parseDouble(latitude), Double.parseDouble(longitude), copy));
//                    } catch (JSONException e) {
//                        Log.e(TAG, "JSON Exception" + e);
//                    }
//                }
//
//                loadMap();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "Error loading git commits!", Toast.LENGTH_LONG).show();
                System.out.println(errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
