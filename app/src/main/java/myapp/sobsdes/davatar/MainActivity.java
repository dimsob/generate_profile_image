package myapp.sobsdes.davatar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText et;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void generateIcon(View v) throws IOException {
        int hash = 0;
        String color = "#";
        et = (EditText) findViewById(R.id.editText);
        iv = (ImageView) findViewById(R.id.imageView);
        String abc = et.getText().toString().toUpperCase();

        if (abc.equals("")) {
            color = color + "333333";
        } else {
            int abc_length = abc.length();
            Log.d("Color", "abc_length " + abc_length);
            for (int i = 0; i < abc_length; i++) {
                hash = abc.charAt(i) + ((hash << 5) - hash);
                Log.d("Color", "hash " + hash);
            }

            for (int i = 0; i < 3; i++) {
                int value = (hash >> (i * 8)) & 0xFF;
                Log.d("Color", "value " + value);
                Log.d("Color", "Integer.toHexString(value) " + Integer.toHexString(value));
                color += ("78" + Integer.toHexString(value)+"27");


            }
        }
        Bitmap returnedBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        //Drawable bgDrawable = view.getBackground();
        Log.d("Color", "returnedBitmap " + returnedBitmap);
        canvas.drawColor(Color.parseColor(color.substring(0, 7)));
        iv.setImageBitmap(returnedBitmap);
        File f = new File(getApplicationContext().getCacheDir(), "img_ava.png");
        f.createNewFile();

//Convert bitmap to byte array

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        returnedBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);


        Log.d("Color", "file " + f.getAbsolutePath());
        Log.d("Color", "color new " + color);
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
}
