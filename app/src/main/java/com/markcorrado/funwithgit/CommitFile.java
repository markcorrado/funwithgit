package com.markcorrado.funwithgit;

import java.io.Serializable;

/**
 * Created by markcorrado on 11/16/14.
 */
public class CommitFile implements Serializable {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private String status;
    private String filename;

    public String toString()
    {
        return filename;
    }
}
