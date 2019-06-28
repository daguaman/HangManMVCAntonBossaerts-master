package anton.mobile.hangmanmvc;

import java.util.concurrent.ExecutionException;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import anton.mobile.hangmanmvc.controller.LoaderController;

public class LoaderActivity extends ActionBarActivity {
	private int val = 0;
	private ConnectivityManager cm;
	private NetworkInfo ni;
	private boolean isConnected = false;
	private TextView txtpb;
	private ProgressBar pb;
	private Handler mHandler;
	private Spinner spinner;
	private String word;
	private String category;
	private LoaderController controller;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loader);
		txtpb = (TextView) findViewById(R.id.textViewStatus);
		cm =(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		ni = cm.getActiveNetworkInfo();
		isConnected = ni.isConnected();
		controller = new LoaderController(isConnected);
		fillCategories();
		
		
	}
	public void selected(View v){
		ProgressBar p =(ProgressBar) findViewById(R.id.progressBar1);
		p.setVisibility(0);
		Spinner s = (Spinner) findViewById(R.id.spinnerCategories);
		this.category = String.valueOf(s.getSelectedItem());
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, getString(R.string.category_selection_message_p1) + " " + category + " " + getString(R.string.category_selection_message_p2), 10);
		toast.show();
		mHandler = new Handler();
		mHandler.postDelayed(myTask, 20);
		try {
			this.word = new AsyncTaskStuff().execute(category).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}
	private void fillCategories(){
		Spinner spinner = (Spinner) findViewById(R.id.spinnerCategories);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.categories, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
	
	Runnable myTask = new Runnable() {
		  @Override
		  public void run() {
			 
			 if (val >= 25 && val < 75) {
				 txtpb.setText(getString(R.string.load_message1));
			 } else if (val >= 75 && val < 125) {
				 txtpb.setText(getString(R.string.load_message2));
			 } else if (val >= 125 && val < 185) {
				 txtpb.setText(getString(R.string.load_message3));
				 } else if (val >= 185 && val < 200) {
				 txtpb.setText(getString(R.string.load_message4));
			 } else if (val == 200) {
				 mHandler.removeCallbacks(myTask);
				 goToMainActivity();
			 }
			 val++;
			 mHandler.postDelayed(myTask, 20);
		  }
	};
	
	public void goToMainActivity() {
		Intent intent = new Intent(getBaseContext(), MainActivity.class);
		intent.putExtra("WORD", this.word);
		startActivity(intent);
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loader, menu);
		return true;
	}

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
	class AsyncTaskStuff extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... stuff) {
			return controller.getWord(stuff[0]);
		}
		@Override
		protected void onPostExecute(String result) {
		    super.onPostExecute(result);
		}
		
	}
	
}
