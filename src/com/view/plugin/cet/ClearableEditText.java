package com.view.plugin.cet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class ClearableEditText extends EditText
		implements View.OnTouchListener, TextWatcher, View.OnFocusChangeListener {

	private Drawable clearIcon;
	private boolean hasFocus;

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

	// --------------$$$$$$$$$$$$$$$4---------------

	@SuppressLint("ClickableViewAccessibility")
	private void initView(final Context context) {
		clearIcon = ResourcesCompat.getDrawable(getResources(), android.R.drawable.presence_offline, null);
		clearIcon.setBounds(0, 0, clearIcon.getIntrinsicWidth(), clearIcon.getIntrinsicHeight());
		this.setOnTouchListener(this);
		this.addTextChangedListener(this);
		this.setOnFocusChangeListener(this);
		this.setSingleLine(true);
		this.setEllipsize(TruncateAt.END);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onFocusChange(final View v, final boolean hasFocus) {
		this.hasFocus = hasFocus;

		// Only show clearing icon if the view is focussed
		if (hasFocus) {
			this.setCompoundDrawables(null, null, this.getText().toString().isEmpty() ? null : clearIcon, null);
		} else {
			this.setCompoundDrawables(null, null, null, null);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
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
		}

		this.setEnabled(true);
		this.setSelection(this.getText().length());
		this.requestFocus();
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
		if (!this.hasFocus) {
			return;
		}

		this.setCompoundDrawables(null, null, this.getText().toString().isEmpty() ? null : clearIcon, null);
	}

	@Override
	public void afterTextChanged(final Editable s) {
		// Nothing to do
	}
}