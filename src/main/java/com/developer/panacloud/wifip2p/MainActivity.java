package com.developer.panacloud.wifip2p;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private WifiBR mReceiver;
    private IntentFilter mIntentFilter;
    private ListView myList;
    static Collection<WifiP2pDevice> collection;
    static ArrayList<String> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myList = (ListView) findViewById(R.id.myList);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "found device", Toast.LENGTH_SHORT).show();
                Log.d("TAG","discoverPeer list -> "+list);
//                ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,list);
//                myList.setAdapter(adapter);
            }

            @Override
            public void onFailure(int reasonCode) {
                if (reasonCode == WifiP2pManager.P2P_UNSUPPORTED) {
                    Log.d("TAG", "P2P isn't supported on this device.");
                }
                    else if(reasonCode == WifiP2pManager.NO_SERVICE_REQUESTS){
                        Log.d("TAG","NO_SERVICE_REQUESTS" );
                    }
                Toast.makeText(getApplicationContext(), "No Device Found"+reasonCode, Toast.LENGTH_SHORT).show();
            }
        });
//        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
//                Toast.makeText(MainActivity.this,""+adapterView.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,""+view.getTag(),Toast.LENGTH_SHORT).show();

                final WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = (String) view.getTag();
                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
//                                success logic
//                        Log.d("TAg", "connected to");
                        Toast.makeText(MainActivity.this,"Connected to "+adapterView.getItemAtPosition(i),Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(int reason) {
//                                failure logic
//                        Log.d("TAg", "conn fail");
                        Toast.makeText(MainActivity.this,"Disconnected "+adapterView.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new WifiBR(mManager, mChannel, this, myList);
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
