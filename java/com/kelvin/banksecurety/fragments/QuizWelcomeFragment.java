package com.kelvin.banksecurety.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kelvin.banksecurety.MainActivity;
import com.kelvin.banksecurety.R;
import com.kelvin.banksecurety.utils.QuizUtils;
import com.kelvin.banksecurety.views.SlideButton;
import com.nineoldandroids.view.ViewHelper;

public class QuizWelcomeFragment extends Fragment implements SlideButton.SlideButtonListener,
	SlideButton.OnSeekBarChangeListener
{
	private SlideButton _start;
	private TextView _btnTitle;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final View root = inflater.inflate(R.layout.fragment_quiz_welcome, container, false);
		final Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
		toolbar.setNavigationOnClickListener(
			new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					getActivity().onBackPressed();
				}
			}
		);

		_btnTitle = (TextView) root.findViewById(R.id.btn_title);
		_start = ((SlideButton) root.findViewById(R.id.unlockButton));
		_start.setSlideButtonListener(this);
		_start.setOnSeekBarChangeListener(this);

		return root;
	}

	@Override
	public void handleSlide()
	{
		QuizUtils.getInstance(getActivity()).shuffle();
		final Bundle arg = new Bundle(1);
		arg.putInt(QuizFragment.KEY_QUESTION_INDEX, 0);
		((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_QUIZ, arg);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	{
		if (progress > 0) {
			ViewHelper.setAlpha(_btnTitle, 0f);
		} else {
			ViewHelper.setAlpha(_btnTitle, 1f);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{

	}
}
