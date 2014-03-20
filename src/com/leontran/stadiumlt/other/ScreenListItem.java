package com.leontran.stadiumlt.other;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.model.Folder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener; 
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ScreenListItem extends Activity implements OnClickListener {

	private String SD_CARD_ROOT;
	File mFile;
	ListView list;
	String direction;
	List<String> tFileList;
	List<Folder> tFolderList;
	TextView filePath;
	Button btn_select;
	Button btn_cancel;
	Button btn_back;
	Button btn_review;
	CustomApplication apps;
	String action;
	String todo;
	public static final String RESULT_PATH = "RESULT_PATH";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.screen_file_dialog);
		action = getIntent().getStringExtra("type");
		todo = getIntent().getStringExtra("todo");
		apps = (CustomApplication) getApplication();
		btn_select = (Button) findViewById(R.id.button_select);
		btn_cancel = (Button) findViewById(R.id.button_cancle);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_review = (Button) findViewById(R.id.button_preview);
		filePath = (TextView) findViewById(R.id.txt_path);
		filePath.setSelected(true);
		btn_cancel.setOnClickListener(this);
		btn_select.setOnClickListener(this);
		btn_review.setOnClickListener(this);
		btn_review.setEnabled(false);
		if (todo == null) {
			btn_back.setOnClickListener(this);
			direction = getIntent().getStringExtra("direct");
			mFile = new File(direction);
			if (mFile.exists()){
				SD_CARD_ROOT = direction;
			} else {
				Toast.makeText(ScreenListItem.this, "Please insert SD Card", Toast.LENGTH_SHORT).show();
				finish();
				startActivity(getIntent());
			}
		} else {
			SD_CARD_ROOT = todo;
			btn_back.setEnabled(false);
			btn_back.setVisibility(View.INVISIBLE);
			btn_back.setText("Cancel");
			btn_cancel.setText("Back");
			btn_select.setEnabled(false);
			btn_select.setVisibility(View.INVISIBLE);
		}

		list = (ListView) findViewById(R.id.listView1);

		list.setAdapter(new CustomAdapterFolder(this, filePath, FindFiles(),
				action, apps));
		list.setTextFilterEnabled(true);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long itemId) {

				if (!tFolderList.get(position).getCount_image().toString()
						.equals("")) {
					Intent intent = new Intent(ScreenListItem.this,
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
					apps.setLinks(locationFormat);
					btn_review.setEnabled(true);
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
		if (action.equals("photo")) {
			imageTypes = resources.getStringArray(R.array.image);
		} else if (action.equals("video")) {
			imageTypes = resources.getStringArray(R.array.video);
		} else if (action.equals("document")) {
			imageTypes = resources.getStringArray(R.array.document);
		} else if (action.equals("file")) {
			imageTypes = resources.getStringArray(R.array.file);
		} else if (action.equals("image_document")){
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
				for (int k = 0; k < mang.length - 1; k++) {
					folder_des += mang[k] + "/";
				}
				if (folder_des.equals(SD_CARD_ROOT + "/")) {
					String folder_name = mang[mang.length - 1];
					Folder fd = new Folder();
					fd.setImage(row_test);
					fd.setFile_location(folder_des);
					/**
					 * if (todo == null) { fd.setFile_location(folder_des);
					 * Log.d("folder_des", folder_des + ""); } else {
					 * fd.setFile_location(todo); Log.d("todo", todo + ""); }
					 */
					fd.setDate_time(lastModDate.toString());
					fd.setFile_name(folder_name);
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
			setResult(RESULT_CANCELED, getIntent());
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
			apps.setLinks("");
			setResult(RESULT_FIRST_USER, getIntent());
			finish();
			overridePendingTransition(R.anim.slide_right,
					R.anim.slide_left_leave);
			break;
		case R.id.button_preview:
			try {
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
				Toast.makeText(ScreenListItem.this,
						"Device not support to open this type file",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
