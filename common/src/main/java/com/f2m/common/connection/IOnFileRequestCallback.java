package com.f2m.common.connection;

import java.io.InputStream;

/**
 * Created by sev_user on 10/17/2016.
 */
public interface IOnFileRequestCallback {
    public abstract void onResult(InputStream is, int flag);
}
