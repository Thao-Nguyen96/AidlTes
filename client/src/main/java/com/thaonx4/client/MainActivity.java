package com.thaonx4.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.thaonx4.client.databinding.ActivityMainBinding;
import com.thaonx4.server.IIPCExample;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private static final String AIDL_ACTION = "com.thaonx4.server.action.AIDL_ACTION";
    private static final String EMPTY_STRING = "";

    Boolean connected = false;
    IIPCExample iRemoteService = null;

    private ActivityMainBinding binding;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {

        iRemoteService = IIPCExample.Stub.asInterface(service);
        try {
            binding.txtServerPid.setText(String.valueOf(iRemoteService.getPid()));
            binding.txtServerConnectionCount.setText(String.valueOf(iRemoteService.getConnectionCount()));


            iRemoteService.setDisplayedValue(
                    this.getPackageName(),
                    Process.myPid(),
                    String.valueOf(binding.edtClientData.getText())
            );

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        connected = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Toast.makeText(this, "IPC server has disconnected unexpectedly", Toast.LENGTH_LONG).show();
        iRemoteService = null;
        connected = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnConnect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (connected) {
            disconnectToRemoteService();

            binding.txtServerPid.setText(EMPTY_STRING);
            binding.txtServerConnectionCount.setText(EMPTY_STRING);
            binding.btnConnect.setText(getString(R.string.connect));
            binding.linearLayoutClientInfo.setVisibility(View.INVISIBLE);

            connected = false;
        } else {
            connectToRemoteService();
            binding.linearLayoutClientInfo.setVisibility(View.VISIBLE);
            binding.btnConnect.setText(getString(R.string.disconnect));

            connected = true;
        }


    }

    private void disconnectToRemoteService() {
        if (connected) {
            unbindService(this);
        }
    }

    private void connectToRemoteService() {

        Intent intent = new Intent(AIDL_ACTION);

        Package pack = IIPCExample.class.getPackage();

        assert pack != null;
        intent.setPackage(pack.getName());

        bindService(intent, this, Context.BIND_AUTO_CREATE);

    }
}