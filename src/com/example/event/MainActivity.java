package com.example.event;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button scanBtn,createBtn;
	TextView contentTxt;
String data = "";

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		scanBtn = (Button) findViewById(R.id.scan_button);
		//formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);
		scanBtn.setOnClickListener(this);
		
		 
        
        createBtn = (Button) findViewById(R.id.create_eventbut);
        
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                
                data = contentTxt.getText().toString();
                String[] op = data.split(",");
               final int sYear=Integer.parseInt(op[2]);
               final int sMonth=Integer.parseInt(op[3]);
               final int sDay=Integer.parseInt(op[4]);
               final int sHrs=Integer.parseInt(op[5]);
               final int sMin=Integer.parseInt(op[6]);
               final int eYear=Integer.parseInt(op[7]);
               final int eMonth=Integer.parseInt(op[8]);
               final int eDay=Integer.parseInt(op[9]);
               final int eHrs=Integer.parseInt(op[10]);
               final int eMin=Integer.parseInt(op[11]);
              

                Calendar beginCal = Calendar.getInstance();
                beginCal.set(sYear,sMonth,sDay,sHrs,sMin);
                long startTime = beginCal.getTimeInMillis();
                Calendar endCal = Calendar.getInstance();
                endCal.set(eYear,eMonth,eDay,eHrs,eMin);
                long endTime = endCal.getTimeInMillis();

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

                intent.putExtra(CalendarContract.Events.TITLE, op[0]);
                intent.putExtra(CalendarContract.Events.DESCRIPTION,  op[12]);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, op[1]);
            

                startActivity(intent);
            }
        });
    }
	 
		
	

/*@SuppressLint("NewApi")
public void insert() {
    Intent intent = new Intent(Intent.ACTION_INSERT,
            CalendarContract.Events.CONTENT_URI);
    // Add the calendar event details
    String loc = contentTxt.getText().toString();
    loc = loc.substring(12, loc.length() - 12);
    loc=loc.replaceAll("SUMMARY", "EVENT");
    intent.putExtra(CalendarContract.Events.TITLE,"" );
    intent.putExtra(CalendarContract.Events.DESCRIPTION,
            loc);
  
    String location = loc.substring(loc.indexOf("LOCATION") + 9, loc.indexOf("DESCRIPTION"));
    intent.putExtra(CalendarContract.Events.EVENT_LOCATION,
            location);
    Calendar startTime = Calendar.getInstance();
    startTime.set(2013, 8, 11);
   
    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
            startTime.getTimeInMillis());
    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
    // Use the Calendar app to add the new event.
    startActivity(intent);
}
*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId()==R.id.scan_button){
			//scan
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
			}
		}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		
		if (scanningResult != null) {
			//we have a result
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			//formatTxt.setText(scanFormat);
			contentTxt.setText(scanContent);
			}
		
		else{
		    Toast toast = Toast.makeText(getApplicationContext(), 
		        "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
		
		}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
}
