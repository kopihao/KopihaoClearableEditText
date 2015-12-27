package com.kopihao.cet.demo;

import com.view.plugin.cet.ClearableEditText;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * This project demo an EditText which able to clear text with its icon<br>
 * This UI component is absolutely inherited from native EditText.<br>
 * This UI component extend capabilities and source code from :<br>
 * [KopihaoActiconEditText](https://github.com/kopihao/KopihaoActiconEditText)
 * // * <u>Why use this</u><br>
 * Custom UI component as a View rather than a View Group<br>
 * Inherit from EditText<br>
 * Native EditText attributes are usable directly in xml<br>
 * Provide method to change Drawable on the fly<br>
 * </p>
 *
 * <p>
 * <u>Things to take note:</u><br>
 * Drawable has to be resized (in xhdpi, hdpi...) wisely.<br>
 * Drawable might stretch EditText bigger.<br>
 * </p>
 *
 * @author Jasper
 *
 */
public class Activity_Main extends Activity {

	private ClearableEditText cetInput;
	private ImageView ivIcon1;
	private ImageView ivIcon2;
	private ImageView ivIcon3;
	private ImageView ivIcon4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_main);
		cetInput = (ClearableEditText) findViewById(R.id.cetInput);
		ivIcon1 = (ImageView) findViewById(R.id.ivIcon1);
		ivIcon2 = (ImageView) findViewById(R.id.ivIcon2);
		ivIcon3 = (ImageView) findViewById(R.id.ivIcon3);
		ivIcon4 = (ImageView) findViewById(R.id.ivIcon4);

		ivIcon1.setOnClickListener(oic_ivIcon);
		ivIcon2.setOnClickListener(oic_ivIcon);
		ivIcon3.setOnClickListener(oic_ivIcon);
		ivIcon4.setOnClickListener(oic_ivIcon);

		String text = "1st, 2nd, 3rd button will change CET icon on main layout.\n";
		text += "4th Button will pop alert dialog which contain CET.";
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();

	}

	private AlertDialog CETDialog(int resid) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final ClearableEditText input;
		builder.setTitle("Alert Dialog Showcase");

		if (resid == 0) {
			builder.setMessage("Type something:");
			input = new ClearableEditText(this);
			builder.setView(input);

		} else {
			LayoutInflater inflater = getLayoutInflater();
			View dgView = inflater.inflate(resid, null);
			builder.setView(dgView);
			input = (ClearableEditText) dgView.findViewById(R.id.cetDgInput);
		}
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				Log.wtf("Jasper", "ClearableEditText,input::" + value);
				return;
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		return builder.create();
	}

	OnClickListener oic_ivIcon = new OnClickListener() {
		@Override
		public void onClick(View v) {
			cetInput.clearFocus();
			v.requestFocus();
			switch (v.getId()) {

			case R.id.ivIcon1:
				cetInput.setClearIcon(android.R.drawable.ic_delete);
				break;
			case R.id.ivIcon2:
				cetInput.setClearIcon(android.R.drawable.ic_btn_speak_now);

				break;
			case R.id.ivIcon3:
				cetInput.setClearIcon(android.R.drawable.ic_input_add);

				break;
			case R.id.ivIcon4:
				// cetInput.setClearIcon(android.R.drawable.ic_input_get);
				// break;
				// CETDialog(0).show();
				CETDialog(R.layout.dg_cet).show();
				return;

			default:
				return;
			}
			Toast.makeText(Activity_Main.this, "CET.Icon changed.", Toast.LENGTH_SHORT).show();
			cetInput.requestFocus();

		}
	};
}
