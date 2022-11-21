package com.finalteam6.HighResAudioRecorder

import android.media.MediaRecorder

class Globals
{
    companion object {
        var MA:MainActivity? = null;

        var DesiredAudioSource = MediaRecorder.AudioSource.MIC;
        var DesiredOutputFormat = MediaRecorder.OutputFormat.DEFAULT;
        var DesiredAudioEncoder = MediaRecorder.AudioEncoder.AAC;
        var DesiredAudioEncodingBitRate = 256000;
        var DesiredAudioChannels = 2;
        var DesiredAudioSamplingRate = 48000;
    }
}