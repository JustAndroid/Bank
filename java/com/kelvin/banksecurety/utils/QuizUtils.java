package com.kelvin.banksecurety.utils;

import android.content.Context;
import android.text.TextUtils;

import com.kelvin.banksecurety.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizUtils
{
	private static int[] IMAGE_RES_ID = {
		R.drawable.q_1,
		R.drawable.q_2,
		R.drawable.q_3,
		R.drawable.q_4,
		R.drawable.q_5,
		R.drawable.q_6,
		R.drawable.q_7,
		R.drawable.q_8,
		R.drawable.q_9,
		R.drawable.q_10,
		R.drawable.q_11,
		R.drawable.q_12,
		R.drawable.q_13,
		R.drawable.q_14,
		R.drawable.q_15,
		R.drawable.q_16,
		R.drawable.q_17,
	};

	private static List<Quiz> QUIZ_LIST = new ArrayList<>(IMAGE_RES_ID.length);

	private static QuizUtils _instance;

	private QuizUtils(final Context context)
	{
		final String[] QUESTIONS = context.getResources().getStringArray(R.array.questions);
		final String[] ANSWERS = context.getResources().getStringArray(R.array.answers);
		final String[] HINTS = context.getResources().getStringArray(R.array.hints);

		for (int i = 0; i < IMAGE_RES_ID.length; i++){
			System.out.println("Начинаем цикл");
			QUIZ_LIST.add(_getQuizByIndex(i, QUESTIONS, HINTS, ANSWERS));
		}
	}

	public static QuizUtils getInstance(Context context)
	{
		if (_instance == null) {
			_instance = new QuizUtils(context);
		}

		return _instance;
	}

	public void shuffle() {
		Collections.shuffle(QUIZ_LIST, new Random());
	}

	public Quiz getQuizByIndex(int index) {
		return QUIZ_LIST.get(index);
	}

	private Quiz _getQuizByIndex(final int index, String[] QUESTIONS, String[] HINTS, String[] ANSWERS)
	{
		final int resImageId = IMAGE_RES_ID[index];
		final String question = QUESTIONS[index];
		final String[] answers;
		final String hint = HINTS[index];
		int indexRightAnswer = -1;

		answers = ANSWERS[index].split(";");

		for (int i = 0; i < answers.length; i++) {
			if (answers[i].contains("~")) {
				indexRightAnswer = i + 1;
				answers[i] = answers[i].replace("~", "");
				break;
			}
		}

		if (TextUtils.isEmpty(question) || answers.length <= 0 || TextUtils.isEmpty(hint) || indexRightAnswer == -1) {
			throw new IllegalArgumentException("Res string answer not valid.");
		}

		return new Quiz(resImageId, question, answers, hint, indexRightAnswer);
	}


	public static class Quiz
	{
		public static final int QUIZ_COUNT = IMAGE_RES_ID.length;

		public final int resImageId;
		public final String question;
		public final String[] answers;
		public final String hint;
		public final int indexRightAnswer;

		public Quiz(int resImageId, String question, String[] answers, String hint, int indexRightAnswer)
		{
			this.resImageId = resImageId;
			this.question = question;
			this.answers = answers;
			this.hint = hint;
			this.indexRightAnswer = indexRightAnswer;
		}
	}
}