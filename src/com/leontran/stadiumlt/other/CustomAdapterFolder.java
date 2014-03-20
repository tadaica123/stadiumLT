package com.leontran.stadiumlt.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.model.Folder;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomAdapterFolder extends ArrayAdapter<Folder> {

	public int RESULT_OK = 0;
	/** The LayoutIngalater. */
	private LayoutInflater inflater;
	/** The list item. */
	private List<Folder> items;

	/** The list to check. */
	ArrayList<Folder> kiemtra;

	/** get Item */
	Folder getItems;
	String action;
	
	SDImageLoader loader;

	/** get Context */
	Activity ct;
	int ps = 0;
	TextView tv;
	CustomApplication apps; 

	public CustomAdapterFolder(Activity context, TextView filePath,
			List<Folder> rowFolder, String action, CustomApplication apps) {
		super(context, R.layout.row_folder, R.id.txt_name, rowFolder);
		// Cache the LayoutInflate to avoid asking for a new one each time.
		inflater = LayoutInflater.from(context);
		items = rowFolder;
		ct = context;
		tv = filePath;
		this.action = action;
		this.apps = apps;
	}

	public CustomAdapterFolder(Activity context, List<Folder> planetList,
			ArrayList<Folder> ar, String action) {
		super(context, R.layout.row_folder, R.id.txt_name, planetList);
		// Cache the LayoutInflate to avoid asking for a new one each time.
		inflater = LayoutInflater.from(context);
		items = planetList;
		kiemtra = ar;
		ct = context;
		this.action = action;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// element to display

		TextView text_name;
		TextView text_postion;
		TextView text_countItem;
		Button btn_show_image = null;
		ImageView image;
		ImageView img_check;
		ProgressBar progress_bar;
		RelativeLayout linearLayout1;

		Folder planet = this.getItem(position);
		// Create a new row view
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_folder, null);

			// Find the child views.
			text_name = (TextView) convertView.findViewById(R.id.txt_name);
			text_postion = (TextView) convertView
					.findViewById(R.id.txt_infomation);
			text_countItem = (TextView) convertView
					.findViewById(R.id.txt_count_item);
			btn_show_image = (Button) convertView
					.findViewById(R.id.btn_show_image);
			img_check = (ImageView) convertView.findViewById(R.id.img_check);
			linearLayout1 = (RelativeLayout) convertView
					.findViewById(R.id.linearLayout1);
			image = (ImageView) convertView.findViewById(R.id.iv_logo_type);
			progress_bar = (ProgressBar) convertView.findViewById(R.id.progress_loading);
			convertView.setTag(new MyFolderHolders(text_name, text_postion,
					text_countItem, image ,progress_bar, img_check, btn_show_image,
					linearLayout1));
			// If CheckBox is toggled, update the planet it is tagged with.
			// if (text_countItem.getText().toString().equals("")){

		}
		// Reuse existing row view
		else {
			// Because we use a ViewHolder, we avoid having to call
			// findViewById().
			MyFolderHolders viewHolder = (MyFolderHolders) convertView.getTag();
			image = viewHolder.getImageView();
			progress_bar = viewHolder.getProgress_bar();
			text_name = viewHolder.getText_name();
			text_postion = viewHolder.getText_positon();
			text_countItem = viewHolder.getText_countItem();
			btn_show_image = viewHolder.getBtn_show_image();
			linearLayout1 = viewHolder.getLinearLayout1();
			img_check = viewHolder.getCheckImage();
		}

		linearLayout1.setTag(planet);
		img_check.setTag(planet);
		progress_bar.setTag(planet);
		image.setTag(planet);
		text_name.setTag(planet);
		text_postion.setTag(planet);
		text_countItem.setTag(planet);
		btn_show_image.setTag(planet);
		text_name.setText(planet.getFile_name());
		if (planet.getDate_time().equals("")) {
			text_postion.setText(planet.getFile_location());
		} else {
			text_postion.setText(planet.getDate_time());
		}
		text_countItem.setText(planet.getCount_image());
		if (planet.getCount_image().equals("")) {
			text_countItem.setVisibility(View.GONE);
		} else {
			text_countItem.setVisibility(View.VISIBLE);

		}
		if (planet.isCheck()) {
			img_check.setVisibility(View.VISIBLE);
		} else {
			img_check.setVisibility(View.GONE);
		}

		if (!planet.getImage().equals("")) {
			if (action.equals("photo")) {
				loader = new SDImageLoader();
				loader.load(planet.getImage(), image ,progress_bar );
			} else if (action.equals("video")) {
				progress_bar.setVisibility(View.GONE);
				image.setVisibility(View.VISIBLE);
				image.setImageResource(R.drawable.media_lib);
			} else if (action.equals("document")) {
				progress_bar.setVisibility(View.GONE);
				image.setVisibility(View.VISIBLE);
				image.setImageResource(R.drawable.document_save);
			} else {
				image.setImageResource(R.drawable.file);
				int check = 0;
				Resources resources = ct.getResources();
				String[] imageTypes = null;	
				imageTypes = resources.getStringArray(R.array.image);
				for (final String type : imageTypes) {
					if (planet.getFile_name().toLowerCase(Locale.ENGLISH)
							.endsWith("." + type) && check == 0) {
						loader = new SDImageLoader();
						loader.load(planet.getImage(), image ,progress_bar );
						check = 1;
						break;
					}
				}
				if (check == 0) {
					imageTypes = resources.getStringArray(R.array.video);
					for (final String type : imageTypes) {
						if (planet.getFile_name().toLowerCase(Locale.ENGLISH)
								.endsWith("." + type)) {
							progress_bar.setVisibility(View.GONE);
							image.setVisibility(View.VISIBLE);
							image.setImageResource(R.drawable.media_lib);
							check = 1;
							break;
						}
					}
				}
				if (check == 0) {
					imageTypes = resources.getStringArray(R.array.document);
					for (final String type : imageTypes) {
						if (planet.getFile_name().toLowerCase(Locale.ENGLISH)
								.endsWith("." + type)) {
							progress_bar.setVisibility(View.GONE);
							image.setVisibility(View.VISIBLE);
							image.setImageResource(R.drawable.document_save);
							break;
						}
					}
				}
			}
		} else {
			progress_bar.setVisibility(View.GONE);
			image.setVisibility(View.VISIBLE);
			image.setImageResource(R.drawable.folder);
		}

		// Optimization: Tag the row with it's child views, so we don't have to
		// call findViewById() later when we reuse the row.

		return convertView;
	}

	@Override
	public Folder getItem(int position) {
		return items.get(position);
	}

}
