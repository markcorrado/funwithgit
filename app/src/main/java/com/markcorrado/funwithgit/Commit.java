package com.markcorrado.funwithgit;

/**
 * Created by markcorrado on 11/16/14.
 */
public class Commit {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String toString()
    {
        return message;
    }

    private String message;
    private String sha;


}
