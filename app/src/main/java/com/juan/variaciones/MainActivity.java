package com.juan.variaciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class MainActivity extends AppCompatActivity {
    public String xxx = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(xxx, "en onCreate" );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                ejecutar();
            }
        });


        //Para hacer la configuracion inicial de ffmpeg.
        //Como en:
        //http://writingminds.github.io/ffmpeg-android-java/
        //y
        //https://github.com/WritingMinds/ffmpeg-android-java
        final ConfiguracionInicial configuracionInicial = new ConfiguracionInicial(this);
        String dato = configuracionInicial.getValor();

        if(dato == null){
        //ejecutar la instalacion inicial
            FFmpeg ffmpeg = FFmpeg.getInstance(this);
            try {
                ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                    @Override
                    public void onStart() {
                        Log.d(xxx, "en onCreate, ffmpeg.loadBinary, onStart" );

                    }

                    @Override
                    public void onFailure() {
                        Log.d(xxx, "en onCreate, ffmpeg.loadBinary, onFailure" );

                    }

                    @Override
                    public void onSuccess() {
                        Log.d(xxx, "en onCreate, ffmpeg.loadBinary, onSuccess" );

                    }

                    @Override
                    public void onFinish() {
                        Log.d(xxx, "en onCreate, ffmpeg.loadBinary, onFinish" );

                        configuracionInicial.setValor("Configuracion valida");

                    }
                });
            } catch (FFmpegNotSupportedException e) {
                // Handle if FFmpeg is not supported by device
                Log.d(xxx, "en onCreate, FFmpegNotSupportedException: " +e.getMessage());

            }

            ffmpeg = null;

        }else {

        }
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

    private void ejecutar(){
        prueba_1_ffmpeg();
    }

    private void prueba_1_ffmpeg(){
        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"

            String[] cmd = new String[4];
            cmd[0] ="-version";

            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Log.d(xxx, "en prueba_1_ffmpeg, ffmpeg.execute, onStart" );

                }

                @Override
                public void onProgress(String message) {
                    Log.d(xxx, "en prueba_1_ffmpeg, ffmpeg.execute, onProgress: " +message);

                }

                @Override
                public void onFailure(String message) {
                    Log.d(xxx, "en prueba_1_ffmpeg, ffmpeg.execute, onFailure: " +message);

                }

                @Override
                public void onSuccess(String message) {
                    Log.d(xxx, "en prueba_1_ffmpeg, ffmpeg.execute, onSuccess: " +message);

                }

                @Override
                public void onFinish() {
                    Log.d(xxx, "en prueba_1_ffmpeg, ffmpeg.execute, onFinish" );

                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Log.d(xxx, "en prueba_1_ffmpeg, FFmpegCommandAlreadyRunningException: " +e.getMessage());

        }
    }
}
