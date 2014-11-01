package com.example.yuntan.pingpongcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.WebSocket.READYSTATE;

public class MainActivity extends Activity {

    private GameController _client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();

        final TextView logView = (TextView)findViewById(R.id.logView);
        final EditText edit_ip = (EditText) findViewById(R.id.edit_ip);
        Button btn_connect = (Button)findViewById(R.id.btn_connect);
        final Button btn_space = (Button)findViewById(R.id.btn_space);

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_client != null && _client.getReadyState() == READYSTATE.OPEN)
                    _client.close();

                try {
                    _client = new GameController(new URI(edit_ip.getText().toString()));
                } catch (final URISyntaxException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            logView.append(e.getMessage() + "\n");
                        }
                    });
                    e.printStackTrace();
                }

                _client.setListener(new GameControllerListenerInterface() {
                    @Override
                    public void Message(final String str) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                logView.append(str + "\n");
                            }
                        });
                    }
                });

                btn_space.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (_client != null && _client.getReadyState() == READYSTATE.OPEN)
                            _client.send("Space Key");
                    }
                });

                _client.connect();
                logView.append("connecting...\n");
            }
        });

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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
