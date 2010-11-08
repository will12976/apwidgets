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
import android.widget.EditText;
/**
 * An editable text field. Add instances to {@link apwidgets.PWidgetContainer}. 
 * 
 * @author Rikard Lundstedt
 *
 */
public class PEditText extends PTextView {

	/**
	 * Creates a new editable text field. 
	 * @param x The text field's x position.
	 * @param y The text field's y position.
	 * @param width The text field's width.
	 * @param height The text field's height.
	 */
	public PEditText(int x, int y, int width, int height) {
		super(x, y, width, height, "");
	}
	/**
	 * Initializes the text field. Is called by {@link PWidgetContainer} 
	 * as it is added to it.
	 * 
	 */
	public void init(PApplet pApplet) {
		this.pApplet = pApplet;

		if (view == null) {
			view = new EditText(pApplet);
		}

		super.init(pApplet);
	}
}