package com.yhtc.mobimouse;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends ActionBarActivity {

    Button connect_btn, left_click_btn, right_click_btn;

    ClientSetup clientSetup;

    String hostname, input, output;
    int portNumber;

    Socket socket;

    DataOutputStream dataOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect_btn = (Button) findViewById(R.id.connect_btn);
        left_click_btn = (Button) findViewById(R.id.left_click_btn);
        right_click_btn = (Button) findViewById(R.id.right_click_btn);

        left_click_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    clientSetup.leftClickPressed();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    clientSetup.leftClickReleased();
                }
                return false;
            }
        });

        right_click_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    clientSetup.rightClickPressed();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    clientSetup.rightClickReleased();
                }
                return false;
            }
        });



        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }

    public void connectServer(View view) {

        clientSetup = new ClientSetup("192.168.41.190", 11111);
        clientSetup.execute();
    }

    public void disconnectClient(View view) {
        clientSetup.exitClient();
        finish();
    }

    public class ClientSetup extends AsyncTask<Void, Void, Void>
    {

        ClientSetup(String hostname2, int portNumber2){
            hostname = hostname2;
            portNumber = portNumber2;
        }

        @Override
        protected Void doInBackground(Void... params)
        {


            try {
                socket = new Socket(hostname, portNumber);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                //dataOutputStream.writeUTF("u");
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
                finish();
            }
            catch (IOException e) {
                e.printStackTrace();
                finish();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        public void leftClickPressed() {

            try{
                dataOutputStream.writeUTF("L_CLICK_PRESS");
            }
            catch(IOException e) {
                e.printStackTrace();
                finish();
            }
        }

        public void rightClickPressed() {

            try{
                dataOutputStream.writeUTF("R_CLICK_PRESS");
            }
            catch(IOException e) {
                e.printStackTrace();
                finish();
            }
        }

        public void leftClickReleased() {

            try{
                dataOutputStream.writeUTF("L_CLICK_RELEASE");
            }
            catch(IOException e) {
                e.printStackTrace();
                finish();
            }
        }

        public void rightClickReleased() {

            try{
                dataOutputStream.writeUTF("R_CLICK_RELEASE");
            }
            catch(IOException e) {
                e.printStackTrace();
                finish();
            }
        }

        public void exitClient() {

            try{
                dataOutputStream.writeUTF("QUIT");
                socket.close();
                finish();
            }
            catch(IOException e){
                e.printStackTrace();
                finish();
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        /*@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }*/
    }

}
