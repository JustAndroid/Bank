package com.kelvin.banksecurety.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class SlideButton extends SeekBar
{

	private Drawable thumb;
	private SlideButtonListener _slideButtonListener;

	public SlideButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public void setThumb(Drawable thumb)
	{
		super.setThumb(thumb);
		this.thumb = thumb;
	}

	@Override
	public boolean onTouchEvent(@NonNull MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (thumb.getBounds().contains((int) event.getX(), (int) event.getY())) {
				super.onTouchEvent(event);
			} else {
				return false;
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getProgress() > 70) {
				handleSlide();
			}

			setProgress(0);
		} else {
			super.onTouchEvent(event);
		}

		return true;
	}

	public void setSlideButtonListener(SlideButtonListener listener)
	{
		this._slideButtonListener = listener;
	}

	private void handleSlide()
	{
		if (_slideButtonListener != null) {
			_slideButtonListener.handleSlide();
		}
	}

	public interface SlideButtonListener
	{
		void handleSlide();
	}
}
