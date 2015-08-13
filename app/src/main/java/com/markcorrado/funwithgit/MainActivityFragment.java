package com.markcorrado.funwithgit;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ListFragment {

    ArrayList<Commit> mCommitArrayList;
    GitRestClient mRestClient;
    private OnFragmentInteractionListener mListener;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCommits();
    }

    private void getCommits() {
        mRestClient = GitRestClient.getInstance();
        mRestClient.setUserAgent("FunWithGit");
        mRestClient.getCommits(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responseArray) {
                super.onSuccess(statusCode, headers, responseArray);
                mCommitArrayList = new ArrayList<Commit>();
                if (responseArray.length() == 0) {
                    Toast.makeText(getActivity(), "No Commits", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < responseArray.length(); i++) {
                        try {
                            JSONObject fullJson = responseArray.getJSONObject(i);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            if (!fullJson.isNull("commit")) {
                                JSONObject commitJsonObject = fullJson.getJSONObject("commit");
                                Commit commit = gson.fromJson(commitJsonObject.toString(), Commit.class);
                                if (!fullJson.isNull("sha")) {
                                    String sha = fullJson.getString("sha");
                                    commit.setSha(sha);
                                }
                                mCommitArrayList.add(commit);
                            }
                        } catch (JSONException e) {
                            Log.e("TAG", "JSON parsing error: " + e);
                        }
                    }
                }
                setupAdapter();
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
                System.out.println(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println(throwable.getLocalizedMessage());
            }
        });
    }

    private void getFiles(String sha) {
        mRestClient = GitRestClient.getInstance();
        mRestClient.setUserAgent("FunWithGit");
        mRestClient.getFiles(sha, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<CommitFile> commitFileArrayList = new ArrayList<>();
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    if (!response.isNull("files")) {
                        JSONArray filesJsonArray = response.getJSONArray("files");
                        commitFileArrayList = gson.fromJson(filesJsonArray.toString(), new TypeToken<List<CommitFile>>() {
                        }.getType());
                    }
                } catch (JSONException e) {
                    Log.e("TAG", "JSON parsing error: " + e);
                }
                showFilesDetail(commitFileArrayList);
                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_LONG).show();
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
                System.out.println(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println(throwable.getLocalizedMessage());
            }
        });
    }

    private void setupAdapter() {
        ArrayAdapter<Commit> adapter = new ArrayAdapter<Commit>(getActivity(), android.R.layout.simple_list_item_1, mCommitArrayList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Commit commit = (Commit) l.getItemAtPosition(position);
        getFiles(commit.getSha());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void showFilesDetail(ArrayList<CommitFile> commitFiles) {
        if (mListener != null) {
            mListener.showFilesDetail(commitFiles);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void showFilesDetail(ArrayList<CommitFile> commitFiles);
    }
}
