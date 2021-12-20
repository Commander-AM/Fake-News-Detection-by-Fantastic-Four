package com.example.fakenewsdetectionandroidapp

//import android.app.ProgressDialog
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
//import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
//import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import org.tensorflow.lite.Interpreter
//import java.io.FileInputStream
//import java.nio.MappedByteBuffer
//import java.nio.channels.FileChannel
//import java.util.*
//import android.R
import android.os.Handler
import android.os.Looper
import android.util.Log
//import android.widget.Toast

//import android.view.View
//import org.tensorflow.lite.support.label.Category

/**
 * This is the main activity class that launches the main activity
 */
class MainActivity : AppCompatActivity() {

    //private val MODELASSETSPATH = "model.tflite"

    // Max Length of input sequence. The input shape for the model will be ( None , INPUT_MAXLEN ).
    //private val INPUT_MAXLEN = 171

    //private var tfLiteInterpreter: Interpreter? = null
    /**
     * label is an array of labels for the news
     */
    private val label = arrayOf("Fake", "True")
    /**
    * client stores object of TextClassificationCleint to run inference
    */
    private var client: TextClassificationClient? = null
    /**
    * handler is used to run on ui thread
    */
    private var handler: Handler? = null
    /**
    * result stores text view for showing result
    */
    private lateinit var result: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * runs on app creation and initializes the values
         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v(TAG, "onCreate")
        /**
         * messagetext contains textview or getting user entered text
         *
         * clear is button to clear text
         */
        val messagetext: EditText = findViewById(R.id.message_text)
        result = findViewById(R.id.result)
        val clear:Button = findViewById(R.id.button3)
        client = TextClassificationClient(applicationContext)
        handler = Handler(Looper.getMainLooper())

        /**
         * classifyButton contains button to classify the text
         */
        val classifyButton = findViewById<Button>(R.id.button)

        /**
         * runs classify function on classify button click
         */
        classifyButton.setOnClickListener {
            classify(messagetext.text.toString())
        }
        /**
         * clears all fields with clear button click
         */
        clear.setOnClickListener{
            messagetext.setText("")
            result.setText("")
        }

    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart")
        /**
         * runs the load method to load tflite model on ui thread
         */
        handler!!.post { client!!.load() }
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop")
        /**
         * closes the tflite model by running the unload method on ui thread
         */
        handler!!.post { client!!.unload() }
    }

    /**
     * Takes text as input and classifies it and runs the show result method
     *
     * @param text:string to classify
     */
    private fun classify(text: String) {
        handler!!.post {

            // Run text classification with TF Lite.
            val results: List<Result> = client!!.classify(text)

            // Show classification result on screen
            showResult(results)
        }
    }

    /**
     * Takes result from classify and shows the output in the result field
     * @param List: List of Result objects
     */
    private fun showResult(r: List<Result>){
        val a = r[0].confidence
        val l =label[r[0].title!!.toInt()]
        val m: String = "$l:$a"
        val b = r[1].confidence
        val n: String = label[r[1].title!!.toInt()] + ":" + b

        result.text = "News is $l\n Probabilities :   \n $n \n  $m"
    }
}
