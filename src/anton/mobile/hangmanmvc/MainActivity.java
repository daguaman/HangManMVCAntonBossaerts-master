package anton.mobile.hangmanmvc;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import anton.mobile.hangmanmvc.controller.MainController;
import anton.mobile.hangmanmvc.observer.Observer;
/*
base code author : Sue Smith 
source : http://code.tutsplus.com/tutorials/create-a-hangman-game-user-interaction--mobile-21893
! added onrestore and onsave functions to provide screen tilting support
! added MVC structure + word entry from Loader
! adjusted structure to be more modular
	*/
public class MainActivity extends ActionBarActivity implements Observer {
	public static final String STATE_LIVES = "lives";
	public static final String WORD = "word";
	public static final String STATE_PRESSED = "pressed";
	public static final String STATE_SELECTION = "selection";
	private Handler mHandler;
	private String word, selection;
	private int lives;
	private boolean pressed[];
	private MainController controller;
	private TextView[] charViews;
	private LinearLayout wordLayout;
	//letter button grid
	private GridView letters;
	//letter button adapter
	private LetterAdapter ltrAdapt;
	private	ImageView[] bodyParts;
	private boolean lost, won;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String word = this.getIntent().getStringExtra("WORD");
		setContentView(R.layout.activity_main);
		mHandler = new Handler();
		this.controller = new MainController();
		this.controller.registerObserver(this);
		this.controller.setWord(word);
		wordLayout = (LinearLayout)findViewById(R.id.word);
		letters = (GridView)findViewById(R.id.letters);
		won = false;
		lost = false;
		update();
	}
	public void letterPressed(View view){
	//find out which letter was pressed
	String ltr=((TextView)view).getText().toString();
	letters.setEnabled(false);
	char letterChar = ltr.charAt(0);
	//disable view
	disableButtons();
	this.controller.checkLetter(letterChar);
}
	private void disableButtons(){
		letters.setEnabled(false);
		
	}
	@SuppressLint("NewApi")
	private void setUpBoard(){
		int length = this.selection.length();
		TextView[] charViews;
		charViews = new TextView[length];

		//remove any existing letters
		wordLayout.removeAllViews();

		//loop through characters
		for(int c=0; c<length; c++){
			charViews[c] = new TextView(this);
			//set the current letter
			charViews[c].setText(""+this.selection.charAt(c));
			//set layout
			charViews[c].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			charViews[c].setGravity(Gravity.CENTER);

			//add to display
			wordLayout.addView(charViews[c]);
		}
		ltrAdapt=new LetterAdapter(this);
		ltrAdapt.setArray(pressed);
		letters.setAdapter(ltrAdapt);
		letters.setEnabled(true);
		bodyParts = new ImageView[6];
		bodyParts[0] = (ImageView)findViewById(R.id.head);
		bodyParts[1] = (ImageView)findViewById(R.id.body);
		bodyParts[2] = (ImageView)findViewById(R.id.arm1);
		bodyParts[3] = (ImageView)findViewById(R.id.arm2);
		bodyParts[4] = (ImageView)findViewById(R.id.leg1);
		bodyParts[5] = (ImageView)findViewById(R.id.leg2);
		for(int p=0; p<6; p++){
			bodyParts[p].setVisibility(View.INVISIBLE);
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	@Override
  	public void onRestoreInstanceState(Bundle savedInstanceState){
  		super.onRestoreInstanceState(savedInstanceState);
  		word = savedInstanceState.getString(WORD);
  		lives = savedInstanceState.getInt(STATE_LIVES);
  		pressed = (boolean[]) savedInstanceState.getSerializable(STATE_PRESSED);
  		selection = savedInstanceState.getString(STATE_SELECTION);
  		this.controller.restoreGame(word, lives, pressed, selection);
		update();
  		
  	}
  	@Override
  	public void onSaveInstanceState(Bundle savedInstanceState){
  		savedInstanceState.putString(WORD, this.controller.getWord());
  		savedInstanceState.putInt(STATE_LIVES, this.controller.getLives());
  		savedInstanceState.putSerializable(STATE_PRESSED, this.controller.getPressed());
  		savedInstanceState.putString(STATE_SELECTION, this.controller.getSelection());
  		super.onSaveInstanceState(savedInstanceState);
  		
  	}
  	public void drawStuff(){
  		setUpBoard();
  		int livesLost = 6-this.lives;
  		if(livesLost > 6){
  			livesLost = 6;
  		}
		for(int p=0; p<livesLost; p++){
			bodyParts[p].setVisibility(View.VISIBLE);
		}
		if (this.lost){
			handleLost();
		}
		if(this.won){
			handleWon();
		}
  	}
  	public void handleWon(){
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, getString(R.string.victory_message), 30);
		toast.show();
		disableButtons();

  	}
  	public void handleLost(){
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, getString(R.string.defeat_message), 30);
		toast.show();
		disableButtons();

  	}
  	private void fillVariables(){
		this.word = controller.getWord();
		this.lives = controller.getLives();
		this.pressed = controller.getPressed();
		this.selection = controller.getSelection();
		this.won = this.controller.isCorrect();
		this.lost = this.controller.isGameOver();
  	}
	@Override
	public void update(){
		mHandler.post(new Runnable(){
			public void run(){
				fillVariables();
				drawStuff();
			}});

	}
}
