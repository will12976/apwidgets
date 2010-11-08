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
import android.widget.*;
import android.view.View;
import android.view.WindowManager;

public class PWidgetContainer{
	public static void enableGUI(PApplet argPApplet){
		argPApplet.runOnUiThread(new EnableGUITask(argPApplet));
	}
	private RelativeLayout layout;
	private PApplet pApplet;
	
	public PWidgetContainer(PApplet pApplet) {
		this.pApplet = pApplet;
		
		pApplet.runOnUiThread(new Runnable() 
		{
			public void run(){

			/*	activity.getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
				activity.getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
*/
				ScrollView scrollView = new ScrollView(getPApplet());
				
								
				getPApplet().getWindow().addContentView(
						scrollView,
						new ScrollView.LayoutParams(
								ScrollView.LayoutParams.FILL_PARENT,
								ScrollView.LayoutParams.FILL_PARENT));
				
				
				
				layout = new RelativeLayout(getPApplet());
				
				scrollView.addView(layout, new ScrollView.LayoutParams(
						ScrollView.LayoutParams.FILL_PARENT,
						ScrollView.LayoutParams.FILL_PARENT));
				
			/*	getPApplet().getWindow().addContentView(
						layout,
						new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.FILL_PARENT,
								RelativeLayout.LayoutParams.FILL_PARENT));
				
			*/
				//Didn´t work
				/*layout.setOnTouchListener(new OnTouchListener(){
					public boolean onTouch(View view, MotionEvent event){
						return activity.onTouchEvent(event);
					}
				}
				);*/
			}
		});
	}

	public void addWidget(PWidget pWidget) {
		pApplet.runOnUiThread(new AddWidgetTask(pWidget));
	}
	
	public void hide(){
		pApplet.runOnUiThread(new Runnable()
		{
			public void run(){
				layout.setVisibility(View.GONE);
				for(int i = 0;i<layout.getChildCount();i++){
					layout.getChildAt(i).setVisibility(View.GONE);
				}
			}
		}); 
	}
	public void show(){
		pApplet.runOnUiThread(new Runnable()
		{
			public void run(){
				layout.setVisibility(View.VISIBLE);
				for(int i = 0;i<layout.getChildCount();i++){
					layout.getChildAt(i).setVisibility(View.VISIBLE);
				}
			}
		});
	}

	class AddWidgetTask implements Runnable{
		private PWidget pWidget;
		public AddWidgetTask(PWidget pWidget){
			super();
			this.pWidget = pWidget;
		}
		public void run(){
			pWidget.init(pApplet);
			
			RelativeLayout.LayoutParams relLayout = new RelativeLayout.LayoutParams(pWidget.getWidth(),
					pWidget.getHeight());
			relLayout.setMargins(pWidget.getX(), pWidget.getY(), 0, 0);
			relLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			relLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			
			layout.addView(pWidget.getView(), relLayout);

		}
	}
	static class EnableGUITask implements Runnable{
		PApplet enableGUITaskPApplet;
		public EnableGUITask(PApplet pApplet){
			enableGUITaskPApplet = pApplet;
		}
		public void run(){
			enableGUITaskPApplet.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			enableGUITaskPApplet.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		}
	}
	public PApplet getPApplet(){
		return pApplet;
	}
}