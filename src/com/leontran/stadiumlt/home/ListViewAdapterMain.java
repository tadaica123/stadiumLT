package com.leontran.stadiumlt.home;

import java.util.ArrayList;
import java.util.List;

import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.model.StadiumDetailModel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


public class ListViewAdapterMain extends ArrayAdapter<StadiumDetailModel> implements Filterable{

	private LayoutInflater inflater;
	private List<StadiumDetailModel> allModelItemsArray;
	private List<StadiumDetailModel> filteredModelItemsArray;
	private List<StadiumDetailModel> temp;
	private ModelFilter filter;

	public ListViewAdapterMain(Activity context,
			List<StadiumDetailModel> positionList, CustomApplication apps) {
		super(context, R.layout.row_main_list_stadium, R.id.txt_title,
				positionList);
		this.allModelItemsArray = new ArrayList<StadiumDetailModel>();
		allModelItemsArray.addAll(positionList);
		this.filteredModelItemsArray = new ArrayList<StadiumDetailModel>();
		filteredModelItemsArray.addAll(allModelItemsArray);
		temp = new ArrayList<StadiumDetailModel>();
		temp.addAll(positionList);
		inflater = LayoutInflater.from(context);
		getFilter();
	}
	
	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new ModelFilter();
		}
		return filter;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		StadiumDetailModel pos = this.getItem(position);

		TextView txtName;
		TextView txtAddress;
		TextView txtDistrict;
		TextView txtPhone;
		ImageView imgLogo;

		// Create a new row view
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.row_main_list_stadium, null);

			txtName = (TextView) convertView.findViewById(R.id.txtName);
			txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
			txtDistrict = (TextView) convertView.findViewById(R.id.txtDistrict);
			txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
			imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);

			convertView.setTag(new PlanetViewHolder(txtName, txtAddress,
					txtDistrict, txtPhone, imgLogo));

		}
		// Reuse existing row view
		else {
			PlanetViewHolder viewHolder = (PlanetViewHolder) convertView
					.getTag();
			txtName = viewHolder.getTxtName();
			txtAddress = viewHolder.getTxtAddress();
			txtDistrict = viewHolder.getTxtDistrict();
			txtPhone = viewHolder.getTxtPhone();
			imgLogo = viewHolder.getImgLogo();
		}

		txtName.setTag(pos);
		txtAddress.setTag(pos);
		txtDistrict.setTag(pos);
		txtPhone.setTag(pos);
		imgLogo.setTag(pos);

		txtName.setText(pos.getName());
		txtAddress.setText(pos.getAddress());
		txtDistrict.setText(pos.getDistrict().getName());
		txtPhone.setText(pos.getPhone());

		return convertView;
	}

	private class ModelFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (allModelItemsArray.size() == 0){
				for (int i = 0, l = temp.size(); i < l; i++)
					allModelItemsArray.add(temp.get(i));
			}
			if (constraint != null && constraint.toString().length() > 0) {
				ArrayList<StadiumDetailModel> filteredItems = new ArrayList<StadiumDetailModel>();

				for (int i = 0, l = allModelItemsArray.size(); i < l; i++) {
					StadiumDetailModel m = allModelItemsArray.get(i);
					if (m.getDistrict().getId().toLowerCase().contains(constraint))
						filteredItems.add(m);
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			} else {
				synchronized (this) {
					result.values = allModelItemsArray;
					result.count = allModelItemsArray.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			filteredModelItemsArray = (ArrayList<StadiumDetailModel>) results.values;
			notifyDataSetChanged();
			clear();
			if (constraint.equals("")) {
				for (int i = 0, l = temp.size(); i < l; i++)
					add(temp.get(i));
			} else {
				for (int i = 0, l = filteredModelItemsArray.size(); i < l; i++)
					add(filteredModelItemsArray.get(i));
			}

			notifyDataSetInvalidated();
		}
	}

	/** Holds child views for one row. */
	private static class PlanetViewHolder {
		private TextView txtName;
		private TextView txtAddress;
		private TextView txtDistrict;
		private TextView txtPhone;
		private ImageView imgLogo;

		/**
		 * @param img_creator
		 * @param img_activities_type
		 * @param txt_creator
		 * @param txt_to_reciever
		 * @param eslapseTimes
		 * @param txt_main_message
		 * @param txt_start_day
		 * @param txt_end_day
		 * @param txt_status
		 * @param progress_creator
		 */
		public PlanetViewHolder(TextView txtName, TextView txtAddress,
				TextView txtDistrict, TextView txtPhone, ImageView imgLogo) {
			super();
			this.txtName = txtName;
			this.txtAddress = txtAddress;
			this.txtDistrict = txtDistrict;
			this.txtPhone = txtPhone;
			this.imgLogo = imgLogo;
		}

		/**
		 * @return the txtName
		 */
		public TextView getTxtName() {
			return txtName;
		}

		/**
		 * @return the txtAddress
		 */
		public TextView getTxtAddress() {
			return txtAddress;
		}

		/**
		 * @return the txtDistrict
		 */
		public TextView getTxtDistrict() {
			return txtDistrict;
		}

		/**
		 * @return the txtPhone
		 */
		public TextView getTxtPhone() {
			return txtPhone;
		}

		/**
		 * @return the imgLogo
		 */
		public ImageView getImgLogo() {
			return imgLogo;
		}

	}
}