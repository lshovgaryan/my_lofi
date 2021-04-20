package com.example.mylofi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

	lateinit var speechRecognizer: SpeechRecognizer
	lateinit var recognizerIntent: Intent
	var keeper: String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		checkVoiceCommandPermission()

		speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
		recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
			putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
			putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.ENGLISH)
		}

		speechRecognizer.setRecognitionListener(object: RecognitionListener {
			override fun onReadyForSpeech(params: Bundle?) = Unit

			override fun onRmsChanged(rmsdB: Float) {
			}

			override fun onBufferReceived(buffer: ByteArray?) {
			}

			override fun onPartialResults(partialResults: Bundle?) {
			}

			override fun onEvent(eventType: Int, params: Bundle?) {
			}

			override fun onBeginningOfSpeech() {
			}

			override fun onEndOfSpeech() {
			}

			override fun onError(error: Int) {
			}

			override fun onResults(results: Bundle?) {
				val resultArray = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
				resultArray?.let {
					keeper = it.get(0)
					Toast.makeText(this@MainActivity, keeper, Toast.LENGTH_LONG).show()
					speechRecognizer.startListening(recognizerIntent)
				}
			}

		})
	}

	override fun onResume() {
		super.onResume()
		speechRecognizer.startListening(recognizerIntent)
	}

	private fun checkVoiceCommandPermission() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
		}
	}
}