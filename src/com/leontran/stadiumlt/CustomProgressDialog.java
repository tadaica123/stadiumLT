package com.leontran.stadiumlt;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

public class CustomProgressDialog extends Dialog {
	
	Dialog dialog;
	int imageResource;
	ImageView imgeView;
	ImageView progress;
	
	/**
	 * Instantiates a new custom dialog show message.
	 * 
	 * @param context
	 *            the context type 0 (Loading) or 1 (Sending)
	 */
	public CustomProgressDialog(Context context) {
		super(context);
		// instantiate the dialog with the custom Them
		dialog = new Dialog(context,
				android.R.style.Theme_Translucent_NoTitleBar);
		dialog.setContentView(R.layout.custom_progress_dialog);
		dialog.setCancelable(true); 
	}

 
	/**
	 * Helper class for creating custom Dialog.
	 * 
	 * @author Tuanka
	 */
	@Override
	public void show() {
		dialog.show();
	}

	@Override
	public void dismiss() {
		dialog.dismiss();
	}
}