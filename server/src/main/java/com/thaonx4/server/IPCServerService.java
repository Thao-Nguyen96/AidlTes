package com.thaonx4.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class IPCServerService extends Service {

    static int connectionCount = 0;
    public static final String NOT_SENT = "Not sent!";
    public static final String AIDL_ACTION ="com.thaonx4.server.action.AIDL_ACTION";

    private final IIPCExample.Stub aidlBinder = new IIPCExample.Stub() {
        @Override
        public int getPid() {
            return Process.myPid();
        }

        @Override
        public int getConnectionCount() {
            return IPCServerService.connectionCount;
        }

        @Override
        public void setDisplayedValue(String packageName, int pid, String data) {

            String clientData;
            RecentClient recentClient = new RecentClient();

            if (data == null || TextUtils.isEmpty(data)) {
                clientData = NOT_SENT;
            } else {
                clientData = data;
            }

            recentClient.client = new Client(
                    packageName,
                    String.valueOf(pid),
                    clientData,
                    "AIDL"
            );
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("mianactivity", " start");
        connectionCount++;

        if (intent.getAction().equals(AIDL_ACTION)) {
            return aidlBinder;
        } else {
            return null;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        RecentClient recentClient = new RecentClient();
        recentClient.client = null;
        return super.onUnbind(intent);
    }
}
