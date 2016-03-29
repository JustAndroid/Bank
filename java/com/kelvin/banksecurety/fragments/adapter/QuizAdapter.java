package com.kelvin.banksecurety.fragments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelvin.banksecurety.R;
import com.kelvin.banksecurety.utils.QuizUtils;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QViewHolder>
{
	private Context _context;
	private QuizUtils.Quiz _quiz;
	private final EventListener _eventListener;
	private boolean _showHint;
	private int _indexChooseAnswer = -1;

	public QuizAdapter(Context context, QuizUtils.Quiz quiz, EventListener eventListener)
	{
		_context = context;
		_quiz = quiz;
		_eventListener = eventListener;
	}

	@Override
	public QViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new QViewHolder(LayoutInflater.from(_context).inflate(R.layout.quiz_item, parent, false));
	}

	@Override
	public void onBindViewHolder(QViewHolder holder, int position)
	{
		holder.set(position);
	}

	@Override
	public int getItemCount()
	{
		int size = _showHint ? 2 : 1;
		return _quiz == null ? 0 : _quiz.answers.length + size;
	}

	protected class QViewHolder extends RecyclerView.ViewHolder
	{
		private final TextView _nameItem;
		private final TextView _textItem;

		public QViewHolder(View itemView)
		{
			super(itemView);
			_nameItem = (TextView) itemView.findViewById(R.id.item_name);
			_textItem = (TextView) itemView.findViewById(R.id.item_text);
		}

		public void set(final int position)
		{
			if (position == 0) {
				_nameItem.setBackgroundResource(R.drawable.question_icon_background);
				_nameItem.setText("?");
				_textItem.setText(_quiz.question);
			} else if (_showHint && position == getItemCount() - 1) {
				_nameItem.setBackgroundResource(R.drawable.question_icon_background);
				_nameItem.setText("!");
				_textItem.setText(_quiz.hint);
			} else {
				_nameItem.setBackgroundResource(R.color.main_color);
				_nameItem.setText("" + ((char) (64 + position)));
				_textItem.setText(_quiz.answers[position - 1]);
				itemView.setBackgroundResource(
					_indexChooseAnswer == position ? R.color.color_primary_dark : R.drawable.menu_item_background
				);
			}

			if (position > 0 && !_showHint) {
				itemView.setOnClickListener(
					new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							if (_eventListener != null) {
								_eventListener.onNextClick(position == _quiz.indexRightAnswer);
							}

							if (position != _quiz.indexRightAnswer) {
								_showHint = true;
								_indexChooseAnswer = position;
								notifyDataSetChanged();
							}
						}
					}
				);
			} else {
				itemView.setOnClickListener(null);
			}
		}
	}

	public interface EventListener
	{
		void onNextClick(boolean isRight);
	}
}
