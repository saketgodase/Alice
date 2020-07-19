package com.usaas.alice

import android.Manifest
import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.*

public class MainActivity : AppCompatActivity() {

    lateinit var searchtxtView: EditText
    lateinit var getsearchText: String
    val PERMISSION_CODE = 100
    val IMAGE_CAPTURE_CODE = 101


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.RECORD_AUDIO), 121)

        title = "Amica"
        searchtxtView = findViewById(R.id.newsearchView)

        /*val mongo = MongoClient("usaas.qrozb.gcp.mongodb.net", 27017)
        var vcredential = MongoCredential.createCredential("admin", "Usaas", "Monalisa@11".toCharArray())
        val database: MongoDatabase = mongo.getDatabase("Usaas")
        println("Credentials ::$vcredential")*/


        //Exit functionality//
        val btn_logout: Button = findViewById(R.id.btnlogout)
        btn_logout.setOnClickListener {
            moveTaskToBack(true)
        }

        //Button to capture Image -- temporary code -- to be removed//
        val btn_temp_capture_image: Button = findViewById(R.id.temp_image_cap)
        btn_temp_capture_image.setOnClickListener() {

            //Check for permissions before proceeding//
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {
                    //Request permission//
                    var Permission = arrayOf<String>(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(Permission, PERMISSION_CODE)
                } else {
                    //permission already granted//
                }
            }

            //startActivity(Intent(this@MainActivity, ImageProcessing::class.java))
        }


        //Create a Chat bot//
        val btn_Chat: Button = findViewById(R.id.chat_btn)
        btn_Chat.setOnClickListener() {
            //startActivity(Intent(this@MainActivity, Alice::class.java))
        }

        //Open the settings window//
        val btn_settings: Button = findViewById(R.id.settings_btn)
        btn_settings.setOnClickListener() {
            //startActivity(Intent(this@MainActivity, AlicaSettings::class.java))
            //startActivity(Intent(this@MainActivity, AlicaSettings::class.java))
        }

        //Search text using the enter key//
        searchtxtView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(
                v: View?,
                keyCode: Int,
                event: KeyEvent
            ): Boolean { // If the event is a key-down event on the "enter" button
                if (event.getAction() === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) { // Perform action on key press
                    getsearchText = searchtxtView.text.toString()
                    var intent = Intent(Intent.ACTION_WEB_SEARCH).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    var term = getsearchText
                    intent.putExtra(SearchManager.QUERY, term)
                    startActivity(intent)
                    searchtxtView.setText("")
                    return true
                }
                return false
            }
        })


    }

    //Search functionality with text//
    fun onSearchClick(view: View) {

        try {
            getsearchText = searchtxtView.text.toString()
            var intent = Intent(Intent.ACTION_WEB_SEARCH).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            var term = getsearchText
            intent.putExtra(SearchManager.QUERY, term)
            startActivity(intent)
            searchtxtView.setText("")
        } catch (e: Exception) { // TODO: handle exception
            print(e.stackTrace)
        }
    }

    //search functionality using voice//
    fun getSpeechInput(view: View) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 10)
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT)
                .show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> if (resultCode == Activity.RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                searchtxtView.setText(result[0])
                val speechValue = result[0]
                var intent = Intent(Intent.ACTION_WEB_SEARCH).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                var term = speechValue
                intent.putExtra(SearchManager.QUERY, term)
                startActivity(intent)
                searchtxtView.setText("")
            }
        }
    }

/*    override fun onBackPressed() {
        //moveTaskToBack(true)
        this.finish()
    }

   override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            false //I have tried here true also
        } else super.onKeyDown(keyCode, event)
    }*/

/*    fun getactivityList()
    {
        try
        {
            val list = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES).activities
            for (i in list.indices)
            {
                System.out.println("List of running activities" + list[i].name)
            }
        }
        catch (e1: PackageManager.NameNotFoundException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }
    }*/

}
