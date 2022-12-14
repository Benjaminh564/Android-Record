package com.finalteam6.HighResAudioRecorder

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File
import java.util.*


class fragment_record : Fragment() {
    private var HRARMediaRecorder:android.media.MediaRecorder? = null;
    private var isRecording:Boolean = false;
    private var currentFilename:String = "";
    private var currentFilepath:String = "";
    private var startTime:Long = 0;

    var fragment_record_Record_Button:ImageButton? = null;
    var fragment_record_Record_Button_Icon:ImageView? = null;
    var fragment_record_Status:TextView? = null;
    var fragment_record_Timer:TextView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Request microphone permissions
        val requestPermissionLauncher =registerForActivityResult(ActivityResultContracts.RequestPermission()){};
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);

        //Grab UI References
        fragment_record_Record_Button = view.findViewById<ImageButton>(R.id.fragment_record_Record_Button);
        fragment_record_Record_Button_Icon = view.findViewById<ImageView>(R.id.fragment_record_Record_Button_Icon);
        fragment_record_Status = view.findViewById<TextView>(R.id.fragment_record_Status);
        fragment_record_Timer = view.findViewById<TextView>(R.id.fragment_record_Timer)

        //Add button listener
        fragment_record_Record_Button!!.setOnClickListener{clicked_fragment_record_Record_Button();}
        fragment_record_Record_Button_Icon!!.setOnClickListener{clicked_fragment_record_Record_Button();}

        //Setup timer thread
        val th = Thread{
            while (true){
                Thread.sleep(50);
                Globals.MA!!.runOnUiThread(Runnable{
                    GUIUpdateTimer();
                })
            }
        }
        th.start();
    }

    private fun clicked_fragment_record_Record_Button()
    {
        if(!isRecording)
        {
            attemptToStartRecording();
        }
        else
        {
            attemptToStopRecording();
        }
    }

    private fun attemptToStartRecording()
    {
        //Verify permissions
        var currentPermissions = ContextCompat.checkSelfPermission(Globals.MA!!.applicationContext,Manifest.permission.RECORD_AUDIO);
        if(currentPermissions != PackageManager.PERMISSION_GRANTED)
        {
            attemptToStopRecording();
            warnAboutPermissions();
            return;
        }

        //Verify storage capabilities
        var recordingFile:File? = null;
        try
        {
            val state = Environment.getExternalStorageState();
            val ctx:Context = Globals.MA!!.applicationContext;
            val audioDir = File(ctx.getExternalFilesDir(Environment.DIRECTORY_DCIM), "HighResAudioRecordings");
            audioDir.mkdirs();
            currentFilepath = audioDir.absolutePath;
            val currentTime:Date = Calendar.getInstance().time;
            currentFilename = currentTime.toString().replace(" ", "_") + ".ogg";
            recordingFile = File("$currentFilepath/$currentFilename");
        }
        catch (e:Exception)
        {
            attemptToStopRecording();
            warnAboutRecordingFail();
            return;
        }

        //Attempt to set settings and record
        try
        {
            HRARMediaRecorder = android.media.MediaRecorder();
            HRARMediaRecorder!!.setOutputFile(recordingFile!!.absolutePath);
            HRARMediaRecorder!!.setAudioSource(Globals.DesiredAudioSource);
            HRARMediaRecorder!!.setOutputFormat(Globals.DesiredOutputFormat);
            HRARMediaRecorder!!.setAudioEncoder(Globals.DesiredAudioEncoder);
            HRARMediaRecorder!!.setAudioEncodingBitRate(Globals.DesiredAudioEncodingBitRate);
            HRARMediaRecorder!!.setAudioChannels(Globals.DesiredAudioChannels);
            HRARMediaRecorder!!.setAudioSamplingRate(Globals.DesiredAudioSamplingRate);
            HRARMediaRecorder!!.prepare();
            HRARMediaRecorder!!.start();
        }
        catch (e:Exception)
        {
            attemptToStopRecording();
            warnAboutRecordingFail();
            return;
        }

        //By this point, we are successfully recording
        startTime = System.currentTimeMillis();
        isRecording = true;
        GUIStartRecording();
    }

    private fun attemptToStopRecording()
    {
        //Use separate try catch blocks because we want to make sure it actually stops
        var didHaveError = false;
        try{HRARMediaRecorder!!.stop();}catch(e:Exception){didHaveError=true;}
        try{HRARMediaRecorder!!.reset();}catch(e:Exception){didHaveError=true;}
        try{HRARMediaRecorder!!.release();}catch(e:Exception){didHaveError=true;}
        try{HRARMediaRecorder = null;}catch(e:Exception){didHaveError=true;}

        if(!didHaveError)
        {
            Toast.makeText(Globals.MA!!.applicationContext,"Saved file as $currentFilename",Toast.LENGTH_LONG).show();
        }

        //By this point we have stopped recording
        //By this point, we are successfully recording
        startTime = System.currentTimeMillis();
        isRecording = false;
        GUIStopRecording();
    }

    private fun warnAboutPermissions()
    {
        AlertDialog.Builder(Globals.MA!!)
            .setTitle("Permissions")
            .setMessage("HighResAudioRecorder needs microphone permissions in order to record audio.")
            .setPositiveButton("OK"){_,_->}
            .show();
    }

    private fun warnAboutRecordingFail()
    {
        AlertDialog.Builder(Globals.MA!!)
            .setTitle("Recording failure")
            .setMessage("HighResAudioRecorder was unable to start recording. Check your microphone permissions and your settings.")
            .setPositiveButton("OK"){_,_->}
            .show();
    }

    private fun GUIStartRecording()
    {
        fragment_record_Status!!.text = "Recording...";
        fragment_record_Record_Button_Icon!!.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_stop_24));
    }

    private fun GUIStopRecording()
    {
        fragment_record_Status!!.text = "Tap to Start Recording";
        fragment_record_Record_Button_Icon!!.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_fiber_manual_record_24));
    }

    private fun GUIUpdateTimer()
    {
        if(!isRecording)
        {
            fragment_record_Timer?.text = "00:00:00";
            return;
        }

        val millisecondsInAnHour:Long =  (1000*60*60);
        val millisecondsInAnMinute:Long =  (1000*60);
        val millisecondsInAnSecond:Long =  (1000);

        //millisecondsElapsed
        var millisecondsElapsed:Long = System.currentTimeMillis() - startTime;

        //Get Hour
        var HoursElapsed = millisecondsElapsed / millisecondsInAnHour;
        millisecondsElapsed -= HoursElapsed * millisecondsInAnHour;

        //Get Minute
        var MinutesElapsed = millisecondsElapsed / millisecondsInAnMinute;
        millisecondsElapsed -= MinutesElapsed * millisecondsInAnMinute;

        //Get Second
        var SecondsElapsed = millisecondsElapsed / millisecondsInAnSecond;
        millisecondsElapsed -= SecondsElapsed * millisecondsInAnSecond;

        //Get String representations
        var HoursElapsedString = HoursElapsed.toString();
        var MinutesElapsedString = MinutesElapsed.toString();
        var SecondsElapsedString = SecondsElapsed.toString();
        while(HoursElapsedString.length < 2) {HoursElapsedString= "0$HoursElapsedString";}
        while(MinutesElapsedString.length < 2) {MinutesElapsedString= "0$MinutesElapsedString";}
        while(SecondsElapsedString.length < 2) {SecondsElapsedString= "0$SecondsElapsedString";}


        fragment_record_Timer?.text = "$HoursElapsedString:$MinutesElapsedString:$SecondsElapsedString";
    }
}