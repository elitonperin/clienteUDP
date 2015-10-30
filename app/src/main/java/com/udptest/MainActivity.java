package com.udptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    int UDP_SERVER_PORT = 9876;
    EditText editText = null;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        Button button = (Button)findViewById(R.id.button_enviar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t = new Thread(new runUdpClient(editText.getText().toString()));
                t.start();
                editText.getText().clear();
            }
        });
    }

    class runUdpClient implements Runnable {
        String msg;

        runUdpClient(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            DatagramSocket ds = null;
            try {
                ds = new DatagramSocket();
                InetAddress serverAddr = InetAddress.getByName("192.168.25.9");
                DatagramPacket dp;
                dp = new DatagramPacket(msg.getBytes(), msg.length(), serverAddr, UDP_SERVER_PORT);
                ds.send(dp);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (ds != null) {
                    ds.close();
                }
            }
        }
    }
}
