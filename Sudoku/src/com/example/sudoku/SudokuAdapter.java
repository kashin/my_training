package com.example.sudoku;
import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Sergey Kashin
 * @brief ArrayAdapter that contains Sudoku's
 * field values  
 *
 */
public class SudokuAdapter extends BaseAdapter
{
	
	static String TAG = "SudokuAdapter";
	private Context mContext;

	public SudokuAdapter(Context context)
	{
		regenerateSudokuArray();
		mContext = context;
		mTextViews = new TextView[9][9];
	}
	
	@Override
	public int getCount()
	{
		return 81;
	}

	@Override
	public Object getItem(int position)
	{
		if (position >= mFieldValues.length)
		{
			android.util.Log.e(TAG, "getItem: position " + position +" is out of range");
			return null;
		}
		return mFieldValues[position % 9][position / 9];
	}

	@Override
	public long getItemId(int position)
	{
		if (position >= 81)
		{
			android.util.Log.e(TAG, "getItem: position " + position +" is out of range");
			return 0;
		}
		if (mTextViews == null ||
		    mTextViews[position % 9][position / 9] == null)
		{
			return 0;
		}
		return mTextViews[position % 9][position / 9].getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView newView;
    	int column = position / 9;
    	int row = position % 9;
        if (convertView == null)
        {  // if it's not recycled, initialise some attributes
        	newView = new TextView(mContext);
        	newView.setText(mFieldValues[row][column].toString());
        }
        else
        {
            newView = (TextView) convertView;
        }
        mTextViews[row][column] = newView;
		return newView;
	}
	
	/**
	 * @brief regenerates Sudoku field == regenerates Field's and User's Integer arrays.
	 */
	public void regenerateSudokuArray()
	{
		// TODO: implement field generation :)
		mFieldValues = new Integer[9][9];
		mUserValues = new Integer[9][9];
		Random random = new Random();
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				mUserValues[i][j] = 0;
			}
		}
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				int newValue = random.nextInt(9) + 1;
				ArrayList<Integer> excludedValues = new ArrayList<Integer>();
				int regionsColumn = i / 3 + 1;
				int regionsRow = j / 3 + 1;
				while (hasSameValueInColumn(newValue, j) ||
					   hasSameValueInRow(newValue, i) ||
					   hasSameValueInRegion(newValue, regionsColumn, regionsRow))
				{
					excludedValues.add(newValue);
					newValue = random.nextInt(9) + 1;
					boolean needToRegenerate = false;
					while (excludedValues.contains(newValue))
					{
						newValue++;
						if (newValue > 9)
						{
							if (!needToRegenerate)
							{
								needToRegenerate = true;
							}
							else
							{
								regenerateSudokuArray();
								return;
							}
							newValue = 1;
						}
					}
				}
				mFieldValues[i][j] = newValue;
				mUserValues[i][j] = newValue;
			}
		}
	}
	
	public boolean hasSameValueInRow(Integer value, int row) {
		boolean res = false;
		for (int i = 0; i < 9; i++)
		{
			if (value.intValue() == mUserValues[row][i])
			{
				return true;
			}
		}
		return res;
	}
	
	public boolean hasSameValueInColumn(Integer value, int column)
	{
		boolean res = false;
		for (int i = 0; i < 9; i++)
		{
			if (value.intValue() == mUserValues[i][column])
			{
				return true;
			}
		}
		return res;
	}
	
	public boolean hasSameValueInRegion(Integer value, int regionsColumn, int regionsRow)
	{
		boolean res = false;
		int iEnd = regionsColumn * 3 - 4;
		int jEnd = regionsRow * 3 - 4;
		for (int i = regionsColumn * 3 - 1; i > iEnd; i--)
		{
			for (int j = regionsRow * 3 - 1; j > jEnd; j-- )
			{
				if (value.intValue() == mUserValues[i][j])
				{
					return true;
				}
			}
		}

		return res;
	}

	public boolean isUserWin()
	{
		if (mFieldValues.length != mUserValues.length)
		{
			return false;
		}
		for (int i = 0; i < 9 ; i++)
		{
			for (int j = 0; j < 9 ; j++)
			{
				if (mFieldValues[i][j] != mUserValues[i][j])
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public Integer[][] getUserValues()
	{
		return mUserValues;
	}
	
	public void setUserValues(Integer[][] userValues)
	{
		mUserValues = userValues;
	}
	
	private Integer[][] mFieldValues;
	private Integer[][] mUserValues;
	private TextView[][] mTextViews;

}
