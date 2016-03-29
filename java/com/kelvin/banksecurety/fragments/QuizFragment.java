package com.kelvin.banksecurety.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.kelvin.banksecurety.MainActivity;
import com.kelvin.banksecurety.R;
import com.kelvin.banksecurety.fragments.adapter.QuizAdapter;
import com.kelvin.banksecurety.utils.QuizUtils;

public class QuizFragment extends Fragment implements QuizAdapter.EventListener, View.OnClickListener
{
	private static final String TAG = "com.kelvin.banksecurety.fragments.QuizFragment";
	private static final String LOG_TAG = "QuizFragment";

	public static final String KEY_QUESTION_INDEX = TAG + ".KEY_QUESTION_INDEX";
	public static final String KEY_SUM_RIGHT_QUESTION = TAG + ".KEY_SUM_RIGHT_QUESTION";

	private Button _next;
	private CoordinatorLayout _coordinatorContainer;
	private AppBarLayout _appBarContainer;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final Bundle arg = getArguments();

		if (arg == null || !arg.containsKey(KEY_QUESTION_INDEX)) {
			return null;
		}

		final int quizIndex = arg.getInt(KEY_QUESTION_INDEX);
		QuizUtils.Quiz quizStructure;

		try {
			quizStructure = QuizUtils.getInstance(getActivity()).getQuizByIndex(quizIndex);
		} catch (IllegalArgumentException e) {
			Log.e(LOG_TAG, e.getMessage());
			return null;
		}

		final View root = inflater.inflate(R.layout.fragment_quiz, container, false);
		_coordinatorContainer = (CoordinatorLayout) root.findViewById(R.id.coordinator_container);
		_appBarContainer = (AppBarLayout) root.findViewById(R.id.app_bar_container);
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

		final ImageView quizImage = (ImageView) root.findViewById(R.id.quiz_image);
		quizImage.setImageResource(quizStructure.resImageId);

		final LinearLayoutManager layoutManager = new LinearLayoutManager(
			getActivity(),
			LinearLayoutManager.VERTICAL,
			false
		);
		final QuizAdapter adapter = new QuizAdapter(getActivity(), quizStructure, this);
		RecyclerView quiz = (RecyclerView) root.findViewById(R.id.quiz);
		quiz.setLayoutManager(layoutManager);
		quiz.setAdapter(adapter);

		_next = (Button) root.findViewById(R.id.btn_next);
		_next.setOnClickListener(this);

		return root;
	}

	@Override
	public void onNextClick(boolean isRight)
	{
		Bundle arg = getArguments();

		if (arg == null) {
			arg = new Bundle(2);
		}

		final int result;
		final int index = arg.containsKey(KEY_QUESTION_INDEX) ? arg.getInt(KEY_QUESTION_INDEX) + 1 : 1;

		arg.putInt(KEY_QUESTION_INDEX, index);

		if (arg.containsKey(KEY_SUM_RIGHT_QUESTION)) {
			result = isRight ? arg.getInt(KEY_SUM_RIGHT_QUESTION) + 1 : arg.getInt(KEY_SUM_RIGHT_QUESTION);
		} else {
			result = isRight ? 1 : 0;
		}
		arg.putInt(KEY_SUM_RIGHT_QUESTION, result);


		if (isRight) {
			_nextClick(index);
		} else {
			_next.setEnabled(true);
			_next.setVisibility(View.VISIBLE);
			_collapseToolbar();


		}
	}

	@Override
	public void onClick(View v)
	{
		final Bundle arg = getArguments();

		_nextClick(arg.getInt(KEY_QUESTION_INDEX));
	}

	private void _nextClick(final int index)
	{
		if (index < QuizUtils.Quiz.QUIZ_COUNT) {
			((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_QUIZ, getArguments());
		} else {
			((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_QUIZ_RESULT, getArguments());
		}
	}

	private void _collapseToolbar()
	{
		final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) _appBarContainer.getLayoutParams();
		final AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
		if (behavior != null) {
			behavior.onNestedFling(_coordinatorContainer, _appBarContainer, null, 0, 10000, true);
		}
	}
}