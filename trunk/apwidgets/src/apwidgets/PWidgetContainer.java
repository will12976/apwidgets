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
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class PWidgetContainer{
	public static void enableGUI(PApplet argPApplet){
		argPApplet.runOnUiThread(new EnableGUITask(argPApplet));
	}
	public PWidgetContainer getThis(){return this;}
	private int index;
	private static ViewFlipper viewFlipper;
	private static void createViewFlipper(PApplet pApplet){
		viewFlipper = new ViewFlipper(pApplet);
		
		pApplet.getWindow().addContentView(
				viewFlipper,
				new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT,
						ViewGroup.LayoutParams.FILL_PARENT));
	}
	private RelativeLayout layout;
	private PApplet pApplet;
	
	public PWidgetContainer(PApplet pApplet) {
		this.pApplet = pApplet;
		enableGUI(pApplet);
		pApplet.runOnUiThread(new Runnable() 
		{
			public void run(){
				if(viewFlipper==null){
					createViewFlipper(getPApplet());
				}
				index = viewFlipper.getChildCount();
			/*	activity.getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
				activity.getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
*/
				MyScrollView scrollView = new MyScrollView(getPApplet());
								
				viewFlipper.addView(scrollView,
						new ViewGroup.LayoutParams(
								ViewGroup.LayoutParams.FILL_PARENT,
								ViewGroup.LayoutParams.FILL_PARENT));
				
				layout = new RelativeLayout(getPApplet());
				
			/*	getPApplet().getWindow().addContentView(
						layout,
						new ViewGroup.LayoutParams(
								ViewGroup.LayoutParams.WRAP_CONTENT,
								ViewGroup.LayoutParams.WRAP_CONTENT));*/

				
				scrollView.addView(layout, new ScrollView.LayoutParams(
						ScrollView.LayoutParams.FILL_PARENT,
						ScrollView.LayoutParams.FILL_PARENT));
				
				scrollView.setFillViewport(true);
				
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
			//	layout.setVisibility(View.GONE);
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
				viewFlipper.setDisplayedChild(index);
			//	layout.setVisibility(View.VISIBLE);
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
	
	class MyScrollView extends ScrollView{

		public MyScrollView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		//Should only be passed if Android ver 2.2, cause then the calls 
		//are obscured by the overlying ViewGroup and never reach the
		//processing surfaceTouchEvent and the mousePressed etc is
		//never called
		public boolean onTouchEvent(MotionEvent evt){
			pApplet.surfaceTouchEvent(evt);//pass on to processing
			return super.onTouchEvent(evt);//calling super makes the scrolling work
			
		}
		
	}
}