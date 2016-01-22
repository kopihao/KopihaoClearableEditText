package com.view.plugin.cet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ClearableEditText extends EditText implements View.OnTouchListener, TextWatcher {

	private Drawable clearIcon;
	private boolean cetFocus;

	public ClearableEditText(final Context context) {
		super(context);
		initView(context);
	}

	public ClearableEditText(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ClearableEditText(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public void setClearIcon(Drawable clearIcon) {
		this.invalidateDrawable(clearIcon);
		this.clearIcon = clearIcon;
		clearIcon.setBounds(0, 0, clearIcon.getIntrinsicWidth(), clearIcon.getIntrinsicHeight());
		this.refreshDrawableState();
		this.invalidate();
		this.requestLayout();

	}

	public void setClearIcon(int id) {
		this.invalidateDrawable(clearIcon);
		this.clearIcon = ResourcesCompat.getDrawable(getResources(), id, null);
		clearIcon.setBounds(0, 0, clearIcon.getIntrinsicWidth(), clearIcon.getIntrinsicHeight());
		this.refreshDrawableState();
		this.invalidate();
		this.requestLayout();
	}

	@SuppressLint("ClickableViewAccessibility")
	protected void initView(final Context context) {
		clearIcon = ResourcesCompat.getDrawable(getResources(), android.R.drawable.presence_offline, null);
		clearIcon.setBounds(0, 0, clearIcon.getIntrinsicWidth(), clearIcon.getIntrinsicHeight());
		this.setOnTouchListener(this);
		this.addTextChangedListener(this);
		this.setOnFocusChangeListener(null);
	}

	@Override
	public void setOnFocusChangeListener(final OnFocusChangeListener l) {
		super.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				cetFocus = hasFocus;
				// Only show clearing icon if the view is focused
				if (hasFocus) {
					setCompoundDrawables(null, null, (getText().toString().length() == 0) ? null : clearIcon, null);
				} else {
					setCompoundDrawables(null, null, null, null);
				}
				if (l != null) {
					l.onFocusChange(v, hasFocus);
				}
			}
		});
	}

	@Override
	public boolean onTouch(final View v, final MotionEvent event) {
		if (this.getCompoundDrawables()[2] == null) {
			return false;
		}
		if (event.getAction() != MotionEvent.ACTION_UP) {
			return false;
		}
		if (event.getX() > this.getWidth() - this.getPaddingRight() - clearIcon.getIntrinsicWidth()) {
			this.setText("");
			this.setCompoundDrawables(null, null, null, null);
			InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
			this.clearFocus();
		}
		return false;
	}

	@Override
	public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
		// Nothing to do
	}

	@Override
	public void onTextChanged(final CharSequence text, final int start, final int lengthBefore, final int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);

		// If it is not in focus, the text was added programmatically and we
		// should not display the clear icon
		if (!this.cetFocus) {
			return;
		}

		this.setCompoundDrawables(null, null, this.getText().toString().isEmpty() ? null : clearIcon, null);
	}

	@Override
	public void afterTextChanged(final Editable s) {
		// Nothing to do
	}
}