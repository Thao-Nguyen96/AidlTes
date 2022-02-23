package com.thaonx4.server;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.thaonx4.server.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void onStart() {
        super.onStart();

        RecentClient recentClient = new RecentClient();

        Client client = recentClient.client;

        if (client == null) {
            binding.linearLayoutClientState.setVisibility(View.INVISIBLE);
            binding.connectionStatus.setText(getString(R.string.no_connected_client));
        } else {
            binding.linearLayoutClientState.setVisibility(View.VISIBLE);
            binding.connectionStatus.setText(getString(R.string.last_connected_client_info));
        }

        if (client != null) {
            binding.txtData.setText(client.getClientData());
            binding.txtIpcMethod.setText(client.getIpcMethod());
            binding.txtPackageName.setText(client.getClientPackageName());
            binding.txtServerPid.setText(client.getClientProcessId());
        }
    }
}