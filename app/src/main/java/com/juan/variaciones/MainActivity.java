package com.juan.variaciones;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
        //prueba_1_ffmpeg();
        concat_two_videos_ffmpeg();
    }

    private void prueba_1_ffmpeg(){
        //Este metodo es solo para obtener la version
        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"

            String[] cmd = new String[1];
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
    }//FIN de prueba_1_ffmpeg


    private void concat_two_videos_ffmpeg(){
        //Para los path de los ficheros, como en:
        //http://oocms.org/question/2514896/how-to-mix-overlay-two-mp3-audio-file-into-one-mp3-file-not-concatenate
        //Este metodo para concatenar 2 videos


        String video_1 = Environment.getExternalStorageDirectory() + "/Variaciones/"  +"video1.mp4";
        String video_2 = Environment.getExternalStorageDirectory() + "/Variaciones/"  +"video2.mp4";
        String video_result = Environment.getExternalStorageDirectory() + "/Variaciones/"  +"videoresult.mp4";
        String audio_result = Environment.getExternalStorageDirectory() + "/Variaciones/"  +"sound_test.mp3";

        String audio_1 = Environment.getExternalStorageDirectory() + "/Variaciones/"  +"ruido.mp3";
        String audio_2 = Environment.getExternalStorageDirectory() + "/Variaciones/"  +"Intro.mp3";
        String audio_mezclado = Environment.getExternalStorageDirectory() + "/Variaciones/"  +"audio-mezclado.mp3";




        //Obtiene el audio del video mp4
        /*  este funciona
        String[] cmd = new String[4];
        cmd[0] = "-y";
        cmd[1] = "-i";
        cmd[2] = video_1;
        cmd[3] = audio_result;  */



        //para concatenar dos videos, funciona
        //como en
        //https://github.com/bgrins/videoconverter.js/issues/18

        /* String[] cmd = {"-i", video_1, "-i", video_2, "-v", "debug", "-strict", "-2", "-filter_complex",
                "[0:v] [0:a:0] [1:v] [1:a:0] concat=n=2:v=1:a=1 [v] [a]", "-map", "[v]", "-map", "[a]", video_result}; */


        //para mezclar 2 audios, mp3
        //String res = "-y -i " + a.getAbsolutePath() + " -i " + b.getAbsolutePath()
        // + " -filter_complex '[0:0][1:0] amix=inputs=2:duration=longest' -c:a libmp3lame " + c.getAbsolutePath();
        String[] cmd = {"-y", "-i", audio_1, "-i", audio_2, "-filter_complex",
                //"[0:0][1:0] amix=inputs=2:duration=longest", "-c:a", "libmp3lame", audio_mezclado};
        "[0:0][1:0] amix=inputs=2:duration=shortest", "-c:a", "libmp3lame", audio_mezclado};
        //Con shortest o longer selecciono que dure el audio mas corto o mas largo


        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"

            //String[] cmd = new String[1];
            //cmd[0] = stringComando;

            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Log.d(xxx, "en concat_two_videos_ffmpeg, ffmpeg.execute, onStart" );

                }

                @Override
                public void onProgress(String message) {
                    Log.d(xxx, "en concat_two_videos_ffmpeg, ffmpeg.execute, onProgress: " +message);

                }

                @Override
                public void onFailure(String message) {
                    Log.d(xxx, "en concat_two_videos_ffmpeg, ffmpeg.execute, onFailure: " +message);

                }

                @Override
                public void onSuccess(String message) {
                    Log.d(xxx, "en concat_two_videos_ffmpeg, ffmpeg.execute, onSuccess: " +message);

                }

                @Override
                public void onFinish() {
                    Log.d(xxx, "en concat_two_videos_ffmpeg, ffmpeg.execute, onFinish" );

                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Log.d(xxx, "en concat_two_videos_ffmpeg, FFmpegCommandAlreadyRunningException: " +e.getMessage());

        }
    }//FIN de prueba_1_ffmpeg
}
