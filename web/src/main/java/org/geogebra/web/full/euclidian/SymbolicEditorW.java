package org.geogebra.web.full.euclidian;

import com.google.gwt.core.client.Scheduler;
import org.geogebra.common.awt.GPoint;
import org.geogebra.common.awt.GRectangle;
import org.geogebra.common.euclidian.SymbolicEditor;
import org.geogebra.common.euclidian.draw.DrawInputBox;
import org.geogebra.common.euclidian.draw.LaTeXTextRenderer;
import org.geogebra.common.kernel.geos.GeoInputBox;
import org.geogebra.common.main.App;
import org.geogebra.web.full.gui.components.MathFieldEditor;
import org.geogebra.web.html5.euclidian.EuclidianViewW;
import org.geogebra.web.html5.euclidian.HasMathKeyboardListener;
import org.geogebra.web.html5.gui.util.MathKeyboardListener;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.himamis.retex.editor.share.editor.MathFieldInternal;
import com.himamis.retex.editor.share.model.MathFormula;
import com.himamis.retex.editor.share.serializer.TeXSerializer;

/**
 * MathField-capable editor for EV, Web implementation.
 *
 * @author Laszlo
 */
public class SymbolicEditorW extends SymbolicEditor implements HasMathKeyboardListener,
		BlurHandler, ChangeHandler {

	private final App app;
	private final EuclidianViewW view;

	private GeoInputBox geoInputBox;
	private DrawInputBox drawInputBox;

	private GRectangle bounds;
	private MathFieldEditor editor;
	private final SymbolicEditorDecorator decorator;
	private TeXSerializer serializer;

	/**
	 * Constructor
	 *
	 * @param app
	 *            The application.
	 */
	public SymbolicEditorW(App app, EuclidianViewW view) {
		this.app = app;
		this.view = view;
		editor = new MathFieldEditor(app, this);
		editor.addBlurHandler(this);
		editor.getMathField().setChangeListener(this);
		editor.getMathField().setFixMargin(LaTeXTextRenderer.MARGIN);
		editor.getMathField().setMinHeight(DrawInputBox.MIN_HEIGHT);
		int baseFontSize = app.getSettings()
				.getFontSettings().getAppFontSize() + 3;

		decorator = new SymbolicEditorDecorator(editor, baseFontSize);
		serializer = new TeXSerializer();
	}

	@Override
	public void attach(GeoInputBox geoInputBox, GRectangle bounds) {
		if (this.geoInputBox != null && this.geoInputBox != geoInputBox) {
			applyChanges();
			this.drawInputBox.setEditing(false);
		}

		this.geoInputBox = geoInputBox;
		this.drawInputBox = (DrawInputBox) view.getDrawableFor(geoInputBox);

		this.bounds = bounds;
		resetChanges();
		editor.attach(view.getAbsolutePanel());
	}

	@Override
	public MathKeyboardListener getKeyboardListener() {
		return editor.getKeyboardListener();
	}

	private void resetChanges() {
		this.drawInputBox.setEditing(true);
		editor.setVisible(true);
		decorator.update(bounds, geoInputBox);

		editor.setText(geoInputBox.getTextForEditor());
		editor.setLabel(geoInputBox.getAuralText());
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				editor.requestFocus();
			}
		});
	}

	@Override
	public boolean isClicked(GPoint point) {
		return drawInputBox.isEditing() && bounds.contains(point.getX(), point.getY());
	}

	@Override
	public void hide() {
		if (!drawInputBox.isEditing()) {
			return;
		}

		applyChanges();
		drawInputBox.setEditing(false);
		editor.setVisible(false);

		AnimationScheduler.get()
				.requestAnimationFrame(new AnimationScheduler.AnimationCallback() {
			@Override
			public void execute(double timestamp) {
				view.doRepaint2();
			}
		});
	}

	@Override
	public void onEnter() {
		applyChanges();
	}

	private void applyChanges() {
		setTempUserDisplayInput();
		String editedText = editor.getText();
		geoInputBox.updateLinkedGeo(editedText);
	}

	private void setTempUserDisplayInput() {
		MathFieldInternal mathFieldInternal = editor.getMathField().getInternal();
		MathFormula formula = mathFieldInternal.getFormula();
		String latex = serializer.serialize(formula);
		geoInputBox.setTempUserDisplayInput(latex);
	}

	@Override
	public void onKeyTyped() {
		decorator.update();
		geoInputBox.update();
		editor.scrollHorizontally();
		editor.updateAriaLabel();
	}

	@Override
	public boolean onEscape() {
		resetChanges();
		return true;
	}

	@Override
	public void onTab(boolean shiftDown) {
		applyChanges();
		hide();
		app.getGlobalKeyDispatcher().handleTab(false, shiftDown);
		app.getSelectionManager().nextFromInputBox();
	}

	@Override
	public void onBlur(BlurEvent event) {
		hide();
	}

	@Override
	public void onChange(ChangeEvent event) {
		decorator.update();
	}
}
