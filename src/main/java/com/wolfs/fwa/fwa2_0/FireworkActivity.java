package com.wolfs.fwa.fwa2_0;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.JSchException;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireworkActivity extends AppCompatActivity{
    public ArrayAdapter<String> adapterFeuerwerke;
    ListView listviewFeuerwerke;
    List<String> output = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ArrayList<String> listFeuerwerke = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firework);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //for(int i=0; i<100; i++){
        //    listFeuerwerke.add("Test " + i);
        //}

        listviewFeuerwerke = (ListView) this.findViewById(R.id.listFeuerwerke);
        adapterFeuerwerke = new ArrayAdapter<>(
                this,
                R.layout.feuerwerke_items,
                R.id.itemFeuerwerk,
                output
        );
        //adapterFeuerwerke.add("Test");
        //listviewFeuerwerke.setAdapter(adapterFeuerwerke);


    }

    public void onClick_Refresh(View view) {
        String prefix = "192.168.178.";
        List<String> avaliableIps = getAllAvaliableIps(prefix);
        Map<String, String> hostAnswers = getAnswerFromIps(avaliableIps);
        for(String piIp : hostAnswers.keySet()){
            System.out.println("ip:" + piIp);
            System.out.println("answer: " + hostAnswers.get(piIp));
            adapterFeuerwerke.add(piIp);
            listviewFeuerwerke.setAdapter(adapterFeuerwerke);
        }
    }

    private Map<String, String> getAnswerFromIps(List<String> ips){
        Map<String, String> hosts = new HashMap<>();
        for (String ip : ips) {
            try {
                String answer = GeneralSshStuff.executeCommand("ls", ip);
                if(!StringUtils.isEmpty(answer)){
                    hosts.put(ip, answer);
                }
            } catch (JSchException e) {
                System.out.println(e);
            }
        }
        return hosts;
    }

    private List<String> getAllAvaliableIps(String prefix){
        List<String> allAvaliableIps = new ArrayList<>();

        for(int i=149; i<151; i++) {
            String ip = prefix + i;

            if(isIpAviliable(ip)){
                allAvaliableIps.add(ip);
                System.out.println("on:" + ip);
                Toast.makeText(getApplicationContext(), "on: " + ip, Toast.LENGTH_LONG).show();
            }else{
                System.out.println("off:" + ip);
                Toast.makeText(getApplicationContext(), "off: " + ip, Toast.LENGTH_SHORT).show();
        }
        }
        System.out.println("Fertig");
        Toast.makeText(getApplicationContext(), "Pingen fertig", Toast.LENGTH_LONG).show();
        return allAvaliableIps;
    }

    private boolean isIpAviliable(String ip){
        InetAddress in;
        //in = null;

        try {
            in = InetAddress.getByName(ip);
            //in = InetAddress.getByName(ip);



            if (in.isReachable(1000)) {
                //return true;
            } else {
                //return false;
            }
        } catch (IOException e) {
            System.out.println(e);

            //return false;
        }
        return true;

    }
}
