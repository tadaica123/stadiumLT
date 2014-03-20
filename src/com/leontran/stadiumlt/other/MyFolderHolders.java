package com.leontran.stadiumlt.other;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyFolderHolders {
    private TextView text_name ;  
    private TextView text_positon ;
    private TextView text_countItem ;
    private Button btn_show_image;
    private ImageView imageView;
    private ProgressBar progress_bar;
    private ImageView checkImage;
    private RelativeLayout linearLayout1;
    
	/**
	 * @return the linearLayout1
	 */
	public RelativeLayout getLinearLayout1() {
		return linearLayout1;
	}
	/**
	 * @param linearLayout1 the linearLayout1 to set
	 */
	public void setLinearLayout1(RelativeLayout linearLayout1) {
		this.linearLayout1 = linearLayout1;
	}
	public MyFolderHolders(TextView text_name, TextView text_positon,
			TextView text_countItem, ImageView imageView, ProgressBar progress_bar , ImageView checkImage, Button btn_show, RelativeLayout linearLayout1) {
		super();
		this.text_name = text_name;
		this.text_positon = text_positon;
		this.text_countItem = text_countItem;
		this.imageView = imageView;
		this.btn_show_image = btn_show;
		this.linearLayout1=linearLayout1;
		this.checkImage = checkImage;
		this.progress_bar = progress_bar;
	}
	/**
	 * @return the text_name
	 */
	public TextView getText_name() {
		return text_name;
	}
	/**
	 * @param text_name the text_name to set
	 */
	public void setText_name(TextView text_name) {
		this.text_name = text_name;
	}
	/**
	 * @return the text_positon
	 */
	public TextView getText_positon() {
		return text_positon;
	}
	/**
	 * @param text_positon the text_positon to set
	 */
	public void setText_positon(TextView text_positon) {
		this.text_positon = text_positon;
	}
	/**
	 * @return the text_countItem
	 */
	public TextView getText_countItem() {
		return text_countItem;
	}
	/**
	 * @param text_countItem the text_countItem to set
	 */
	public void setText_countItem(TextView text_countItem) {
		this.text_countItem = text_countItem;
	}
	/**
	 * @return the imageView
	 */
	public ImageView getImageView() {
		return imageView;
	}
	/**
	 * @param imageView the imageView to set
	 */
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	/**
	 * @return the btn_show_image
	 */
	public Button getBtn_show_image() {
		return btn_show_image;
	}
	/**
	 * @param btn_show_image the btn_show_image to set
	 */
	public void setBtn_show_image(Button btn_show_image) {
		this.btn_show_image = btn_show_image;
	}
	/**
	 * @return the checkImage
	 */
	public ImageView getCheckImage() {
		return checkImage;
	}
	/**
	 * @param checkImage the checkImage to set
	 */
	public void setCheckImage(ImageView checkImage) {
		this.checkImage = checkImage;
	}
	/**
	 * @return the progress_bar
	 */
	public ProgressBar getProgress_bar() {
		return progress_bar;
	}
	/**
	 * @param progress_bar the progress_bar to set
	 */
	public void setProgress_bar(ProgressBar progress_bar) {
		this.progress_bar = progress_bar;
	}
	
	

}
