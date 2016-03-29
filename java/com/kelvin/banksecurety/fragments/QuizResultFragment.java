package com.kelvin.banksecurety.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelvin.banksecurety.MainActivity;
import com.kelvin.banksecurety.R;
import com.kelvin.banksecurety.utils.QuizUtils;
import com.svdroid.scoreview.ScoreView;

public class QuizResultFragment extends Fragment implements View.OnClickListener
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final Bundle arg = getArguments();

		if (arg == null || !arg.containsKey(QuizFragment.KEY_SUM_RIGHT_QUESTION)) {
			return null;
		}

		final View root = inflater.inflate(R.layout.fragment_quiz_result, container, false);
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
		final int userScore = arg.getInt(QuizFragment.KEY_SUM_RIGHT_QUESTION);
		final TextView result = (TextView) root.findViewById(R.id.result);
		result.setText("You score:  " + userScore + "/" + QuizUtils.Quiz.QUIZ_COUNT);

		final int scoreAngel = 180 * userScore / QuizUtils.Quiz.QUIZ_COUNT;

		final ScoreView scoreBoard = (ScoreView) root.findViewById(R.id.score_board);
		scoreBoard.startAnimation(scoreAngel < 90 ? scoreAngel - 90 : scoreAngel - 90);

		root.findViewById(R.id.btn_try_again).setOnClickListener(this);

		return root;
	}

	@Override
	public void onClick(View v)
	{
		final Bundle arg = new Bundle(1);
		arg.putInt(QuizFragment.KEY_QUESTION_INDEX, 0);
		((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_QUIZ, arg);
	}
}
