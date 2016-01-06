package com.developer.panacloud.wifip2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by abdulazizniazi on 9/7/15.
 */
public class WifiBR extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;
    private ListView mList;

    public WifiBR(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       MainActivity activity,ListView list) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
        this.mList = list;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Toast.makeText(mActivity,"onReceive",Toast.LENGTH_SHORT).show();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
            Toast.makeText(mActivity,"Enabled Wifi",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity,"Disabled Wifi",Toast.LENGTH_SHORT).show();
                // Wi-Fi P2P is not enabled
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            if (mManager != null) {
                final ArrayList list = new ArrayList();
                final ArrayList list1 = new ArrayList();
                mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
//                        Log.d("TAG", "LISt " + wifiP2pDeviceList.getDeviceList());
                        Collection<WifiP2pDevice> lists = wifiP2pDeviceList.getDeviceList();

                        for(WifiP2pDevice device : lists){
                            Log.d("TAG","list "+device.deviceName);
                            list.add(device.deviceName);
                            list1.add(device.deviceAddress);
                            MyDevicesListAdapter adapter = new MyDevicesListAdapter(mActivity,list,list1);
                            mList.setAdapter(adapter);
                        }

//                        for(int i=0;i<lists.size();i++){
////                            if(lists.iterator().next().deviceAddress.equals(list.get(i))){
//////                                list.clear();
////                            }
////                            else{
//                                list.add(lists.iterator().next().deviceName);
//                                ArrayAdapter adapter = new ArrayAdapter(mActivity,android.R.layout.simple_list_item_1,list);
//                                mList.setAdapter(adapter);
////                            }
//
//                        }
//                        Log.d("TAG", "list length " + lists);
////                        list.add(lists.iterator().next().deviceAddress);
//                        Log.d("TAG","collecs "+lists.iterator().next().deviceAddress);
//                        Log.d("TAG","list "+list);


                    }
                });

            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
            Toast.makeText(mActivity,"ConnectionChanged",Toast.LENGTH_SHORT).show();
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
            Toast.makeText(mActivity,"DeviceChanged",Toast.LENGTH_SHORT).show();
        }
    }
}
