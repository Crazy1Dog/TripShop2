package com.chiyu.shopapp.bean;

import java.io.Serializable;

import android.widget.ImageView;

public class PhotoToLoad implements Serializable {

	/**
	 * // Task for the queue
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private ImageView imageView;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public PhotoToLoad(String u, ImageView i) {
		url = u;
		imageView = i;
	}
}
