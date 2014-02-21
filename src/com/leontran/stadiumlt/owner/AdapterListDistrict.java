package com.leontran.stadiumlt.owner;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.model.DistrictModel;

public class AdapterListDistrict extends ArrayAdapter<DistrictModel> {

	/** The context. */
	private final Activity context;

	/** The members. */
	private DistrictModel rowData;


	/**
	 * Instantiates a new list view adapter.
	 * 
	 * @param context
	 *            the context
	 * @param items
	 *            the items
	 */
	public AdapterListDistrict(Activity context, ArrayList<DistrictModel> items) {
		super(context, R.layout.row_list_district, items);
		this.context = context;
	}

	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder {

		/** The text view. */
		public TextView txt_text;

	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// ViewHolder will buffer the assess to the individual fields of the row
		// layout

		final ViewHolder holder;

		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		rowData = this.getItem(position);

		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_list_district, null, true);
			holder = new ViewHolder();
			holder.txt_text = (TextView) rowView
					.findViewById(R.id.row_title);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		if (rowData != null) {
			holder.txt_text.setText(rowData.getName());
		}
		return rowView;
	}

}
