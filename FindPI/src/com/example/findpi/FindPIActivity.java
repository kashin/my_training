package com.example.findpi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class FindPIActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pi);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_pi, menu);
		return true;
	}

	public void onCountButtonClicked(View view) {
		EditText editText = (EditText)findViewById(R.id.EnterNField);
		int iN = Integer.parseInt(editText.getText().toString());
		String PIStr = countPI(iN);
		EditText outputTextField = (EditText) findViewById(R.id.OutputField);
		outputTextField.setText(PIStr);
	}

	private String countPI(int iPILength) {
		StringBuilder result = new StringBuilder(iPILength);
		int size = 10* iPILength / 3;
		int massive[] = new int[size];
		// Initialise massive with 2.
		for (int i = 0; i < size; i++) {
			massive[i] = 2;
		}
		int n = 0;
		// counting Pi...
		while (n < iPILength) {
			int carriedOver = 0;
			int sum = 0;
			for (int i = size - 1; i >= 0; i--) {
				massive[i] *= 10;
				sum = massive[i] + carriedOver;
				int balance = sum / (2*i + 1);
				massive[i] = sum % (2*i + 1);
				carriedOver = balance * i;
			}
			massive[0] = sum % 10;
			int newPiDigit = sum / 10;
			int heldDigits = 0;
			switch (newPiDigit) {
			case 9:
			{
				heldDigits++;
				break;
			}
			case 10:
			{
				newPiDigit = 0;
				for (int i = 1; i <= heldDigits; i++) {
					int replaced = Integer.parseInt(result.substring(n - i, n - i + 1));
					if (replaced == 9) {
						replaced = 0;
					} else {
						replaced++;
					}
					result.deleteCharAt(n - i);
					result.insert(n - i, replaced);
				}
				heldDigits = 1;
				break;
			}
			default:
			{
				heldDigits = 1;
			}
			} // switch
			result.append(newPiDigit);
			n++;
		}
		// adding dot if we wanted to get more than 1 digit
		if (iPILength > 1) {
			result.insert(1, '.');
		}
		return result.toString();
	}
}
