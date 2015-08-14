package com.markcorrado.funwithgit;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements CommitsFragment.OnFragmentInteractionListener{

    public static final String SHA_TAG = "sha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            CommitsFragment commitsFragment = new CommitsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, commitsFragment, CommitsFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void showFilesDetail(String sha) {
        CommitFileFragment newFragment = new CommitFileFragment();
        Bundle args = new Bundle();
        args.putSerializable(SHA_TAG, sha);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment, CommitFileFragment.TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
