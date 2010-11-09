package apwidgets;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import processing.core.PApplet;

public class PMediaPlayer {
	private PApplet pApplet;
	
/*	public PApplet getPApplet(){
		return pApplet;
	}
	*/
	
	private MediaPlayer mediaPlayer;
	public PMediaPlayer(PApplet pApplet){
		this.pApplet = pApplet;
	//	pApplet.runOnUiThread(new Runnable() {
	//		public void run() {
				mediaPlayer = new MediaPlayer();
	//		}
	//	});
	}
	public void setMediaFile(String file){
		AssetFileDescriptor afd = null;
		try {
			afd = pApplet.getAssets().openFd(file);
			mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			mediaPlayer.prepare();
		}catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start(){
		mediaPlayer.start();
	}
}
