package ua.teampush.appteampush;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
public class LoginActivity extends Activity implements View.OnClickListener {

    TextView textview;
    TextView textview2;
    Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setResult(RESULT_OK, new Intent()); //return -1 code to parent window (loading and wearein)
        textview = (TextView) findViewById(R.id.registerText);
        textview2 = (TextView) findViewById(R.id.passwordEditText);
        textview.setOnClickListener(this);


        log = (Button) findViewById(R.id.loginButton);
        log.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loading, menu);
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

    public void onBackPressed(){
        this.finish();
    }

    public void onClick(View v){ //There we should connect to DB
        final EditText string1 = (EditText)findViewById(R.id.loginEditText);
        final EditText string2 = (EditText)findViewById(R.id.passwordEditText);
        final Button but = (Button)findViewById(R.id.loginButton);
        new Thread(new Runnable() {
            public void run() {

                try{
                    URL url = new URL("http://192.168.65.151:8080/TeamPushServer/UserServelet");
                    URLConnection connection = url.openConnection();

                    String inputString = string1.getText().toString()+"/"+string2.getText().toString();
                    //inputString = URLEncoder.encode(inputString, "UTF-8");

                    Log.d("inputString", inputString);

                    connection.setDoOutput(true);
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    out.write(inputString);
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String returnString="";

                    returnString = in.readLine();
                    but.setText(returnString);
                    in.close();

                }catch(Exception e)
                {
                    Log.d("Exception",e.toString());
                }
            }
        }).start();
        if(v.getId() == R.id.registerText){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, 1);
        }else if(v.getId() == R.id.loginButton){
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);
            //WE NEED TO SAVE LOG AND PASS TO PREFERENCES HERE
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK) {
            if (requestCode == 1 || requestCode == 0) {
                this.finish();
            }
        }
    }

}