package com.leontran.stadiumlt.home;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.model.SlideMenuItem;

public class ListViewAdapterMainMenu extends ArrayAdapter<SlideMenuItem> {

	private LayoutInflater inflator;

	public ListViewAdapterMainMenu(Activity context, List<SlideMenuItem> list) {
		super(context, R.layout.row, R.id.row_title, list);
		inflator = context.getLayoutInflater();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// View view = null;

		TextView districtName;
		ImageView districtLogo = null;
		SlideMenuItem pos = this.getItem(position);
		// ViewHolder viewHolder = null;
		if (convertView == null) {

			convertView = inflator.inflate(R.layout.row_main_menu, null);
			districtName = (TextView) convertView.findViewById(R.id.row_title);
			districtLogo = (ImageView) convertView.findViewById(R.id.row_icon);
			convertView.setTag(new ViewHolder(districtName, districtLogo));
		}

		else {
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();

			districtName = viewHolder.getTxtDistrictName();
			districtLogo = viewHolder.getImgDistrictLogo();
		}
		districtName.setTag(pos);
		districtLogo.setTag(pos);
		districtName.setText(pos.label);
		districtLogo.setImageDrawable(pos.icon);
		return convertView;
	}

	static class ViewHolder {
		TextView txtDistrictName;
		ImageView imgDistrictLogo;

		/**
		 * @return the txtDistrictName
		 */
		public TextView getTxtDistrictName() {
			return txtDistrictName;
		}

		/**
		 * @param txtDistrictName
		 *            the txtDistrictName to set
		 */
		public void setTxtDistrictName(TextView txtDistrictName) {
			this.txtDistrictName = txtDistrictName;
		}

		/**
		 * @return the imgDistrictLogo
		 */
		public ImageView getImgDistrictLogo() {
			return imgDistrictLogo;
		}

		/**
		 * @param imgDistrictLogo
		 *            the imgDistrictLogo to set
		 */
		public void setImgDistrictLogo(ImageView imgDistrictLogo) {
			this.imgDistrictLogo = imgDistrictLogo;
		}

		public ViewHolder(TextView txtDistrictName, ImageView imgDistrictLogo) {
			super();
			this.txtDistrictName = txtDistrictName;
			this.imgDistrictLogo = imgDistrictLogo;
		}

	}

}
