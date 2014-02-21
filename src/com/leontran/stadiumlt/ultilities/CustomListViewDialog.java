package com.leontran.stadiumlt.ultilities;

import java.util.ArrayList;

import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.model.DistrictModel;
import com.leontran.stadiumlt.owner.AdapterListDistrict;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class CustomListViewDialog extends Dialog {
	View layout;
	Context context;
	Dialog dialog;
	ListView listDistrict;
	ArrayList<DistrictModel> arrayData; 
	AdapterListDistrict adapter;

	/**
	 * Instantiates a new custom dialog show message.
	 * 
	 * @param context
	 *            the context
	 */
	public CustomListViewDialog(Activity context , final TextView textView , final CustomApplication app) {
		super(context);
		// instantiate the dialog with the custom Them
		this.context = context;
		dialog = new Dialog(context,
				android.R.style.Theme_Translucent_NoTitleBar);

		dialog.setContentView(R.layout.custom_show_list_dialog);
		dialog.setCancelable(true);
		initData();
		listDistrict = (ListView) dialog.findViewById(R.id.list_district);
		adapter = new AdapterListDistrict(context, arrayData);
		listDistrict.setAdapter(adapter);
		listDistrict.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				textView.setText(arrayData.get(position).getName());
				dialog.dismiss();
				app.setReturnDistrict(new DistrictModel(arrayData.get(position).getId() , arrayData.get(position).getName()));
			}
		});
	}

	/**
	 * Helper class for creating custom Dialog.
	 * 
	 * @author leon
	 */
	@Override
	public void show() {
		dialog.show();
	}

	@Override
	public void dismiss() {
		dialog.dismiss();
	}
	
	public void initData(){
		 arrayData = new ArrayList<DistrictModel>();
		 DistrictModel dataAdd = new DistrictModel();
		 dataAdd.setId("q_1");
		 dataAdd.setName(context.getResources().getString(R.string.quan1));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_2");
		 dataAdd.setName(context.getResources().getString(R.string.quan2));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_3");
		 dataAdd.setName(context.getResources().getString(R.string.quan3));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_4");
		 dataAdd.setName(context.getResources().getString(R.string.quan4));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_5");
		 dataAdd.setName(context.getResources().getString(R.string.quan5));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_6");
		 dataAdd.setName(context.getResources().getString(R.string.quan6));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_7");
		 dataAdd.setName(context.getResources().getString(R.string.quan7));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_8");
		 dataAdd.setName(context.getResources().getString(R.string.quan8));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_9");
		 dataAdd.setName(context.getResources().getString(R.string.quan9));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_10");
		 dataAdd.setName(context.getResources().getString(R.string.quan10));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_11");
		 dataAdd.setName(context.getResources().getString(R.string.quan11));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_12");
		 dataAdd.setName(context.getResources().getString(R.string.quan12));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_qv");
		 dataAdd.setName(context.getResources().getString(R.string.quan_govap));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_tb");
		 dataAdd.setName(context.getResources().getString(R.string.quan_tanbinh));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_tp");
		 dataAdd.setName(context.getResources().getString(R.string.quan_tanphu));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_bth");
		 dataAdd.setName(context.getResources().getString(R.string.quan_binhthanh));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_pn");
		 dataAdd.setName(context.getResources().getString(R.string.quan_phunhuan));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_td");
		 dataAdd.setName(context.getResources().getString(R.string.quan_thuduc));
		 arrayData.add(dataAdd);
		 dataAdd = new DistrictModel();
		 dataAdd.setId("q_bta");
		 dataAdd.setName(context.getResources().getString(R.string.quan_binhtan));
		 arrayData.add(dataAdd);

	}
}

