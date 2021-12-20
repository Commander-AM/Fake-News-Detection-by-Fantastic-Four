package com.example.fakenewsdetectionandroidapp

import android.content.Context
import android.util.Log
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier
//import com.example.fakenewsdetectionandroidapp.TextClassificationClient
import java.io.IOException
import java.util.*

//import org.tensorflow.lite.support.label.Category

/** Load TfLite model and provide predictions with task api.  */
class TextClassificationClient(private val context: Context) {
    /**
     * Stores classfier
     */
    var classifier: BertNLClassifier? = null

    /**
     * loads classifier from tflite file
     * @throws IOException if classifier not found
     */
    fun load() {
        try {
            classifier = BertNLClassifier.createFromFile(context, MODEL_PATH)
        } catch (e: IOException) {
            Log.e(TAG, e.message!!)
        }
    }

    /**
     * unloads classifier if it exists
     */
    fun unload() {
        classifier?.close()
        classifier = null
    }

    /**
     * classifies given text
     * @param text is a string to be classified
     * @return results which is a sorted list of Result objects
     */
    fun classify(text: String?): List<Result> {
        val apiResults = classifier!!.classify(text)
        val results: MutableList<Result> = ArrayList<Result>(apiResults.size)
        for (i in apiResults.indices) {
            val category = apiResults[i]
            results.add(Result("" + i, category.label, category.score))
        }
        results.sort()
        return results
    }

    companion object {
        const val TAG = "TaskApi"
        private const val MODEL_PATH = "bert.tflite"
    }
}


