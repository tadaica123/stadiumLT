package com.leontran.stadiumlt.other;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.CustomProgressDialog;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.model.Folder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectFileDialog extends Activity implements OnClickListener {
	private String SD_CARD_ROOT;
	private File mFile = Environment.getExternalStorageDirectory();
	private ListView list;
	private List<String> tFileList;
	private List<Folder> tFolderList;
	private CustomApplication apps;
	private TextView filePath;
	private Button btn_back;
	private Button btn_select;
	private Button btn_cancel;
	private Button btn_review;
	private String action;
	private String tylefile;
	private CustomProgressDialog progDailog;

	public static final String RESULT_PATH = "RESULT_PATH";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.screen_file_dialog);

		apps = (CustomApplication) getApplication();
		if (apps != null) {
			apps.setLinks("");
			action = getIntent().getStringExtra("type");
			if (action.equals("photo") || action.equals("photo_member")) {
				tylefile = " Photos";
			} else if (action.equals("video")) {
				tylefile = " Video";
			} else if (action.equals("document")) {
				tylefile = " Files";
			} else if (action.equals("file")) {
				tylefile = " Files";
			} else if (action.equals("image_document")) {
				tylefile = " Files";
			} else if (action.equals("media")){
				tylefile = " Files";
			}
			btn_select = (Button) findViewById(R.id.button_select);
			btn_cancel = (Button) findViewById(R.id.button_cancle);
			btn_back = (Button) findViewById(R.id.btn_back);
			btn_review = (Button) findViewById(R.id.button_preview);
			filePath = (TextView) findViewById(R.id.txt_path);
			filePath.setSelected(true);
			btn_cancel.setOnClickListener(this);
			btn_select.setOnClickListener(this);
			btn_back.setOnClickListener(this);
			btn_review.setOnClickListener(this);
			btn_review.setEnabled(false);
			if (mFile.exists()){
				SD_CARD_ROOT = mFile.toString();
				list = (ListView) findViewById(R.id.listView1);
				LoadFileFromSystem loadData = new LoadFileFromSystem();
				loadData.execute("");
			} else {
				Toast.makeText(SelectFileDialog.this, "Please insert SD Card", Toast.LENGTH_SHORT).show();
				finish();
				startActivity(getIntent());
			}
		
		} else {
			finish();
			startActivity(getIntent());
		}

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long itemId) {

				if (!tFolderList.get(position).getCount_image().toString()
						.equals("")) {
					apps.setLinks("");
					Intent intent = new Intent(SelectFileDialog.this,
							ScreenListItem.class);
					intent.putExtra("type", action);
					intent.putExtra("direct", tFolderList.get(position)
							.getFile_location().toString()
							+ tFolderList.get(position).getFile_name()
									.toString());
					startActivityForResult(intent, 10);
					overridePendingTransition(R.anim.slide_left,
							R.anim.slide_right_leave);
				} else {
					String locationFormat = tFolderList.get(position)
							.getFile_location().toString()
							+ tFolderList.get(position).getFile_name();
					filePath.setText(locationFormat);
					btn_review.setEnabled(true);
					apps.setLinks(locationFormat);
					for (int i = 0; i < list.getCount(); i++) {
						if (list.getChildAt(i) != null) {
							MyFolderHolders myRow = (MyFolderHolders) list
									.getChildAt(i).getTag();
							if (myRow != null) {
								myRow.getCheckImage().setVisibility(View.GONE);
							}
						}
						tFolderList.get(i).setCheck(false);
					}
					tFolderList.get(position).setCheck(true);
					list.invalidate();
					MyFolderHolders myRow = (MyFolderHolders) view.getTag();
					myRow.getCheckImage().setVisibility(View.VISIBLE);

				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		return true;
	}

	private List<Folder> FindFiles() {
		tFileList = new ArrayList<String>();
		tFolderList = new ArrayList<Folder>();
		Resources resources = getResources();
		// array of valid image file extensions
		String[] imageTypes = null;
		if (action.equals("photo") || action.equals("photo_member")) {
			imageTypes = resources.getStringArray(R.array.image);
		} else if (action.equals("video")) {
			imageTypes = resources.getStringArray(R.array.video);
		} else if (action.equals("document")) {
			imageTypes = resources.getStringArray(R.array.document);
		} else if (action.equals("file")) {
			imageTypes = resources.getStringArray(R.array.file);
		} else if (action.equals("image_document")) {
			imageTypes = resources.getStringArray(R.array.image_document);
		} else if (action.equals("media")){
			imageTypes = resources.getStringArray(R.array.image_video);
		}

		FilenameFilter[] filter = new FilenameFilter[imageTypes.length];

		int i = 0;
		for (final String type : imageTypes) {
			filter[i] = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase(Locale.ENGLISH)
							.endsWith("." + type);
				}
			};
			i++;
		}

		FileUtils fileUtils = new FileUtils();
		File[] allMatchingFiles = fileUtils.listFilesAsArray(new File(
				SD_CARD_ROOT), filter, -1);
		for (File f : allMatchingFiles) {
			String row_test = f.getAbsolutePath();
			tFileList.add(row_test);
			Date lastModDate = new Date(f.lastModified());
			String[] mang = row_test.split("/");
			if (mang.length > 0) {
				String folder_des = "";
				for (int k = 0; k < mang.length - 2; k++) {
					folder_des += mang[k] + "/";
				}

				String folder_name = mang[mang.length - 2];
				int check = 1;
				int count = 1;
				for (int j = 0; j < tFolderList.size(); j++) {
					if (tFolderList.get(j).getFile_name().equals(folder_name)
							&& tFolderList.get(j).getFile_location()
									.equals(folder_des)) {
						check = 0;
						String kt[] = tFolderList.get(j).getCount_image()
								.split(" ");
						int count_now = Integer.parseInt(kt[0]) + 1;
						tFolderList.get(j).setCount_image(count_now + tylefile);
					}
				}
				if ((check == 1)
						&& (!folder_name.equals("sdcard") && (!folder_name
								.substring(0, 1).equals(".") && (!folder_des
								.contains("/."))))) {
					Folder fd = new Folder();
					fd.setImage("");
					fd.setFile_location(folder_des);
					fd.setFile_name(folder_name);
					fd.setCount_image(count + tylefile);
					fd.setDate_time("");
					fd.setCheck(false);
					tFolderList.add(fd);
				} else if (folder_name.equals("sdcard")) {
					Folder fd = new Folder();
					fd.setImage(row_test);
					fd.setFile_location(folder_des + "sdcard/");
					fd.setDate_time(lastModDate.toString());
					fd.setFile_name(mang[mang.length - 1]);
					fd.setCount_image("");
					fd.setCheck(false);
					tFolderList.add(fd);
				}
			}
		}
		return tFolderList;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_cancle:
			finish();
			overridePendingTransition(R.anim.slide_right,
					R.anim.slide_left_leave);

			break;
		case R.id.button_select:
			if (apps.getLinks().equals("")) {
				Toast.makeText(getApplicationContext(),
						"Please select a file to upload !", Toast.LENGTH_SHORT)
						.show();
			} else {
				setResult(RESULT_OK, getIntent());
				finish();
				overridePendingTransition(R.anim.slide_right,
						R.anim.slide_left_leave);
			}

			break;
		case R.id.btn_back:
			finish();
			overridePendingTransition(R.anim.slide_right,
					R.anim.slide_left_leave);

			break;
		case R.id.button_preview:
			try {
				Log.d("links", apps.getLinks());
				String path = apps.getLinks();
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				File file = new File(path);

				MimeTypeMap mime = MimeTypeMap.getSingleton();
				String ext = file.getName().substring(
						file.getName().indexOf(".") + 1);
				String type = mime.getMimeTypeFromExtension(ext
						.toLowerCase(Locale.ENGLISH));
				intent.setDataAndType(Uri.fromFile(file), type);
				startActivity(intent);
			} catch (Exception ex) {
				Toast.makeText(SelectFileDialog.this,
						"Device not support to open this type file",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	@Override
	public synchronized void onActivityResult(final int requestCode,
			int resultCode, final Intent data) {
		if (resultCode == RESULT_OK && requestCode == 10) {
			setResult(RESULT_OK, getIntent());
			finish();
			overridePendingTransition(R.anim.slide_right,
					R.anim.slide_left_leave);

		} else if (resultCode == RESULT_CANCELED && requestCode == 10) {
			finish();
			overridePendingTransition(R.anim.slide_right,
					R.anim.slide_left_leave);

		} else if (resultCode == RESULT_FIRST_USER && requestCode == 10) {
			filePath.setText("/..");
			for (int i = 0; i < list.getCount(); i++) {
				if (list.getChildAt(i) != null) {
					MyFolderHolders myRow = (MyFolderHolders) list
							.getChildAt(i).getTag();
					if (myRow != null) {
						myRow.getCheckImage().setVisibility(View.GONE);
					}
				}
				tFolderList.get(i).setCheck(false);
			}
			list.invalidate();
		}

	}

	private class LoadFileFromSystem extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			FindFiles();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			list.setAdapter(new CustomAdapterFolder(SelectFileDialog.this,
					filePath, tFolderList, action, apps));
			list.setTextFilterEnabled(true);
			progDailog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progDailog = new CustomProgressDialog(SelectFileDialog.this);
			progDailog.show();
		}
	}

}