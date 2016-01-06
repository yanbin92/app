package com.developer.panacloud.wifip2p;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.Inflater;

/**
 * Created by abdulazizniazi on 9/9/15.
 */
public class MyDevicesListAdapter extends ArrayAdapter {
    ArrayList list,list1;
    WifiP2pDevice devices;
    Collection<WifiP2pDevice> collection;
    public MyDevicesListAdapter(Context context, ArrayList list,ArrayList list1) {
        super(context, android.R.layout.simple_list_item_1,list);
        this.list=list;
        this.list1=list1;
        this.devices=devices;
        this.collection = collection;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_item,parent,false);
//        view.setTag(devices);
//        view.setTag(position);

        TextView textView = (TextView) view.findViewById(R.id.myText);
        textView.setText(list.get(position).toString());
        view.setTag(list1.get(position));
        return view;
    }
}
