package geogebra.touch.gui.elements.header;

import geogebra.common.move.ggtapi.models.Material;
import geogebra.html5.main.AppWeb;
import geogebra.touch.FileManagerM;
import geogebra.touch.TouchEntryPoint;
import geogebra.touch.gui.TabletGUI;
import geogebra.touch.gui.WorksheetGUI;
import geogebra.touch.gui.elements.StandardImageButton;
import geogebra.touch.gui.laf.DefaultResources;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class WorksheetHeaderPanel extends AuxiliaryHeaderPanel {
	Material material;
	
	private static DefaultResources LafIcons = TouchEntryPoint.getLookAndFeel().getIcons();
	private StandardImageButton editButton = new StandardImageButton(LafIcons.document_edit());
	WorksheetGUI worksheetGUI;

	public WorksheetHeaderPanel(final AppWeb app, final FileManagerM fm, final WorksheetGUI worksheetGUI, final TabletGUI tabletGUI)
	{
		super("", app.getLocalization());
		this.worksheetGUI = worksheetGUI;
		
		super.backPanel.addDomHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				tabletGUI.addEuclidian(WorksheetHeaderPanel.this.worksheetGUI.getContentLocal());
				TouchEntryPoint.goBack();
			}
		}, ClickEvent.getType());		
		
		this.rightPanel.add(this.editButton);
		this.editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event)
			{
				event.stopPropagation();
				if (WorksheetHeaderPanel.this.material != null)
				{
					tabletGUI.addEuclidian(worksheetGUI.getContentLocal());
					TouchEntryPoint.allowEditing(true);
					fm.getMaterial(WorksheetHeaderPanel.this.material, app);
					TouchEntryPoint.showTabletGUI();
				}
			}
		});
	}

	public void setMaterial(Material m)
	{
		setText(m.getTitle());
		this.material = m;
	}

	@Override
  public void setLabels()
	{
		super.setLabels();
	}
}
