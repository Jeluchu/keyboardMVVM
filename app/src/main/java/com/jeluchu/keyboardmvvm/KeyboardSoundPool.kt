package com.jeluchu.keyboardmvvm

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build

class KeyboardSoundPool(context: Context?) {
    private val soundPool: SoundPool
    private val volume = 1.0f
    private val sounds: Array<Sound>

    init {
        val soundsList = mutableListOf<Sound>()
        val soundFilesIds = SoundResManager.getSoundFilesIds()
        soundPool = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build()
            SoundPool.Builder().setMaxStreams(soundFilesIds.size).setAudioAttributes(audioAttributes).build()
        } else {
            SoundPool(soundFilesIds.size, AudioManager.STREAM_MUSIC, 0)
        }

        for (fileID in soundFilesIds) {
            val soundId = soundPool.load(context, fileID.value, 1)
            soundsList.add(Sound(soundId, fileID.key))
        }
        sounds = soundsList.toTypedArray()
    }

    fun updateSounds(soundingPitches: Array<Int>) {
        val soundsToStop = sounds.filter { it.isPlaying() && !soundingPitches.contains(it.pitch) }
        val soundsToPlay = sounds.filter { !it.isPlaying() && soundingPitches.contains(it.pitch) }

        for (sound in soundsToStop) stopSound(sound)
        for (sound in soundsToPlay) startSound(sound)
    }

    private fun startSound(sound: Sound) {
        val streamId = soundPool.play(sound.id, volume, volume, 1, 0, 1f)
        sound.streamId = streamId

    }

    private fun stopSound(sound: Sound) {
        soundPool.stop(sound.streamId)
        sound.streamId = 0
    }
}