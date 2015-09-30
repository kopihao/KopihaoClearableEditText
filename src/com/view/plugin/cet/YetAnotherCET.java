package com.view.plugin.cet;

import android.content.Context;
import android.util.AttributeSet;

public class YetAnotherCET extends ClearableEditText {

	public YetAnotherCET(final Context context) {
		super(context);
	}

	public YetAnotherCET(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public YetAnotherCET(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void initView(Context context) {
		super.initView(context);
		setClearIcon(android.R.drawable.btn_star_big_on);
	}

}
