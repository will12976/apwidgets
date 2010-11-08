/**
 * Copyright 2010 Rikard Lundstedt
 * 
 * This file is part of APWidgets.
 * 
 * APWidgets is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * APWidgets is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with APWidgets. If not, see <http://www.gnu.org/licenses/>.
 */

package apwidgets;

import processing.core.PApplet;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
/**
 * 
 * 
 * @author Rikard Lundstedt
 *
 */
public class PVideoView extends PWidget {

	private boolean hasMediaController = false;
	private String videoPath = null;

	public PVideoView() {
		this(0, 0, ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
	}

	public PVideoView(int x, int y, int width, int height,
			boolean argHasMediaController) {
		super(x, y, width, height);

		hasMediaController = argHasMediaController;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
		if (initialized) {
			pApplet.runOnUiThread(new Runnable() {
				public void run() {
					((VideoView) view).setVideoPath(getVideoPath());
				}
			});
		}
	}

	public void start() {
		if (initialized) {
			pApplet.runOnUiThread(new Runnable() {
				public void run() {
					((VideoView) view).start();
				}
			});
		}
	}

	public void stopPlayBack() {
		if (initialized) {
			pApplet.runOnUiThread(new Runnable() {
				public void run() {
					((VideoView) view).stopPlayback();
				}
			});
		}
	}

	public void pause() {
		if (initialized) {
			pApplet.runOnUiThread(new Runnable() {
				public void run() {
					((VideoView) view).pause();
				}
			});
		}
	}

	/*
	 * void resume(){ //wait til level 8 ((VideoView)view).resume(); }
	 */

	public void seekTo(int msec) {
		if (initialized) {
			pApplet.runOnUiThread(new SeekToTask(msec));
		}
	}

	class SeekToTask implements Runnable {
		int msec;

		public SeekToTask(int argmsec) {
			msec = argmsec;
		}

		public void run() {
			((VideoView) view).seekTo(msec);
		}
	}
	/**
	 * Initializes the video view. Is called by {@link PWidgetContainer} 
	 * as it is added to it.
	 */
	public void init(PApplet pApplet) {
		this.pApplet = pApplet;

		if (view == null) {
			view = new VideoView(pApplet);
		}
		((VideoView) view).setZOrderMediaOverlay(true);

		if (hasMediaController) {
			MediaController mediaController = new MediaController(pApplet);
			mediaController.setAnchorView(((VideoView) view));

			((VideoView) view).setMediaController(mediaController);
		}
		if (videoPath != null) {
			((VideoView) view).setVideoPath(videoPath);
		}

		super.init(pApplet);
	}
	public String getVideoPath(){
		return videoPath;
	}
}
