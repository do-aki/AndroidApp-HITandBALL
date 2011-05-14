package net.do_aki.android.HandB;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity {

    Game game = new Game();
    ArrayAdapter<String> result_list;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        result_list = new ArrayAdapter<String>(this, R.layout.list_text);
        
        Button button_judge = (Button)findViewById(R.id.button_judge);
        button_judge.setOnClickListener(onButtonClick);

        ListView listView1 = (ListView)findViewById(R.id.listView1); 
        listView1.setAdapter(result_list);
        listView1.setFocusable(false);
        listView1.setFocusableInTouchMode(false);
        
        TextView text_message = (TextView)findViewById(R.id.text_message);
        text_message.setText(R.string.hello);
        
        EditText editText1 = ((EditText)findViewById(R.id.editText1));
        editText1.setText("");
        editText1.setOnEditorActionListener(onEditTextEditorAction);
        editText1.setOnKeyListener(onEditTextKey);
        editText1.requestFocusFromTouch();
        
        newGame();
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.menu_exit:
            finish();
            return true;
        case R.id.menu_new_game:
            newGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void newGame() {
        game.initialize();
        result_list.clear();
        ((TextView)findViewById(R.id.text_hit)).setText("0");
        ((TextView)findViewById(R.id.text_ball)).setText("0");
        ((TextView)findViewById(R.id.text_message)).setText("go");
    }
    
    private void judge() {
        EditText input = (EditText)findViewById(R.id.editText1);
        String input_string = input.getText().toString();

        GameResult gr = game.judge(input_string);

        switch (gr.getStatus()){
        case GameResult.STATUS_INVALID:
            ((TextView)findViewById(R.id.text_message)).setText("oops...");
            input.requestFocus();
            return;

        case GameResult.STATUS_FINISH:
            ((TextView)findViewById(R.id.text_message)).setText("finish!");
            Toast.makeText(this, R.string.notice_congratulation, Toast.LENGTH_SHORT).show();
            break;
        case GameResult.STATUS_CONTINUE:
            ((TextView)findViewById(R.id.text_message)).setText("next");
            break;
        }

        ((TextView)findViewById(R.id.text_hit)).setText(gr.getHit().toString());
        ((TextView)findViewById(R.id.text_ball)).setText(gr.getBall().toString());

        result_list.insert(String.format("%s : %d hit,%d ball", input_string, gr.getHit(), gr.getBall()), 0);

        input.setText("");
        input.requestFocus();
    }
    
    private OnKeyListener onEditTextKey = new OnKeyListener() {
        
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                Log.d("EditText", "KEYCODE_ENTER");
                judge();
            }
            return false;
        }
    };
    
    private OnEditorActionListener onEditTextEditorAction = new OnEditorActionListener() {
        
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            // IME で Enter された場合
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                Log.d("EditText", "IME_ACTION_SEND");
                judge();
                return true;
            }
            return false;
        }
    };
    
    private OnClickListener onButtonClick = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            judge();
       }
    };
}