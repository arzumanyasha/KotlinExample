package com.example.kotlinexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            setNewText("Click!")

            //Scope is the way to organize coroutines into groupings
            //IO is a context. IO is for network or database requests
            //Main is for doing work on Main thread
            //Default is for doing any heave computation work
            //launch constructs coroutine and gets it already to go
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }
        }
    }

    private fun setNewText(input: String){
        val newText = text.text.toString() + "\n$input"
        text.text = newText
    }
    private suspend fun setTextOnMainThread(input: String) {
        //we can also write CoroutineScopte(Main).launch
        //withContext switches the context of the coroutine
        withContext (Main) {
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest() {
        logThread("fakeApiRequest")

        val result1 = getResult1FromApi() // wait until job is done

        if ( result1.equals("Result #1")) {

            setTextOnMainThread("Got $result1")

            val result2 = getResult2FromApi() // wait until job is done

            if (result2.equals("Result #2")) {
                setTextOnMainThread("Got $result2")
            } else {
                setTextOnMainThread("Couldn't get Result #2")
            }
        } else {
            setTextOnMainThread("Couldn't get Result #1")
        }
    }

    //*suspend mark method as something that can run asynchronous
    //it's not necessarily that method will work on main thread or
    //background thread. It just means that it's going to be able to be used in a coroutine
    //suspend function can be called only from coroutine or another suspend function
    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        //like Thread.sleep
        // Does not block thread. Just suspends the coroutine inside the thread
        delay(1000)
        return "Result #1"
    }

    private suspend fun getResult2FromApi(): String {
        logThread("getResult2FromApi")
        delay(1000)
        return "Result #2"
    }

    private fun logThread(methodName: String){
        println("debug: $methodName: ${Thread.currentThread().name}")
    }
}
