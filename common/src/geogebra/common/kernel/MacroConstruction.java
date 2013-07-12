/* 
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org

This file is part of GeoGebra.

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by 
the Free Software Foundation.

*/
package geogebra.common.kernel;


import geogebra.common.kernel.geos.GeoElement;
import geogebra.common.util.StringUtil;

import java.util.HashSet;

/**
 * Construction for macros.
  */
public class MacroConstruction extends Construction {
	
	private Construction parentCons;
	private HashSet<String> reservedLabels;
	private boolean globalVariableLookup = false;
	
	/**
	 * Creates new macro construction
	 * @param kernel Kernel
	 */
	public MacroConstruction(MacroKernel kernel) {
		super(kernel, kernel.getParentKernel().getConstruction());
		parentCons = kernel.getParentKernel().getConstruction();
		reservedLabels = new HashSet<String>();
		//allow using reserved function names in marco constructions
		super.setFileLoading(true);
	}		   
	
	/**
	 * Set construction via XML string.	 
	 * @param xmlString XML string of the construction
	 * @throws Exception if there is a problem while reading XML
	 */
	public void loadXML(String xmlString) throws Exception {
		if (undoManager == null)
			undoManager = kernel.getApplication().getUndoManager(this);
		this.setFileLoading(true);
		undoManager.processXML(xmlString);
		this.setFileLoading(false);
	}
	
	/**
	 * Adds label to the list of reserved labels. Such labels
	 * will not be looked up in the parent construction in lookup();
	 * @param label reserved label
	 */
	public void addReservedLabel(String label) {
		if (label != null) {			
			reservedLabels.add(label);						
		}
	}
	
    /**
     * Returns a GeoElement for the given label. Note: 
     * construction index is ignored here. If no geo is found for
     * the specified label a lookup is made in the parent construction.
     * @return may return null
     */      	    	   
    @Override
	public final GeoElement lookupLabel(String label, boolean autoCreate) {//package private
    	if (label == null) return null;

		String label1 = label;

    	// local var handling
		if (localVariableTable != null) {        	
        	GeoElement localGeo = localVariableTable.get(label1);        
            if (localGeo != null) return localGeo;
        }
    	    	       
        // global var handling        
        GeoElement geo = geoTableVarLookup(label1);

		// STANDARD CASE: variable name found
		if (geo != null) {
			return geo;
		}

		label1 = Kernel.removeCASVariablePrefix(label1);
		geo = geoTableVarLookup(label1);
		if (geo != null) {
			// geo found for name that starts with TMP_VARIABLE_PREFIX or
			// GGBCAS_VARIABLE_PREFIX
			return geo;
		}

		/*
		 * SPREADSHEET $ HANDLING In the spreadsheet we may have variable names
		 * like "A$1" for the "A1" to deal with absolute references. Let's
		 * remove all "$" signs from label and try again.
		 */
		if (label1.indexOf('$') > -1) {
			StringBuilder labelWithout$ = new StringBuilder(label1.length() - 1);
			for (int i = 0; i < label1.length(); i++) {
				char ch = label1.charAt(i);
				if (ch != '$') {
					labelWithout$.append(ch);
				}
			}
			String labelString = labelWithout$.toString();
			// allow automatic creation of elements
			geo = lookupLabel(labelString, autoCreate);
			if (geo != null) {
				// geo found for name that includes $ signs
				return geo;
			}
			if(labelString.charAt(0)>='0' && labelString.charAt(0)<='9'){
				int cell = 0;
				try{
					cell = Integer.parseInt(labelWithout$.toString());
					}
				catch(Exception e){
					e.printStackTrace();
				}
				if(cell>0){
					return this.getCasCell(cell-1);
				}
			}
		}

		// try upper case version for spreadsheet label like a1
		if (autoCreate) {
			if (StringUtil.isLetter(label1.charAt(0)) // starts with letter
					&& StringUtil.isDigit(label1.charAt(label1.length() - 1))) // ends
																				// with
																				// digit
			{
				String upperCaseLabel = label1.toUpperCase();
				geo = geoTableVarLookup(upperCaseLabel);
				if (geo != null) {
					return geo;
				}
			}
		}

        if (geo == null && globalVariableLookup && !isReservedLabel(label1)) {
        	// try parent construction        	
        	 geo =  parentCons.lookupLabel(label1, autoCreate); 
        }
        return geo;                   
    }
    
    private boolean isReservedLabel(String label) {
    	return reservedLabels.contains(label);        	
    }

    /**
     * Returns true if geos of parent costruction can be referenced
     * @return true if geos of parent costruction can be referenced
     */
	public boolean isGlobalVariableLookup() {
		return globalVariableLookup;
	}

	 /**
     * Set to true if geos of parent costruction should be referenced
     * @param globalVariableLookup true if geos of parent costruction should be referenced
     */
	public void setGlobalVariableLookup(boolean globalVariableLookup) {
		this.globalVariableLookup = globalVariableLookup;
	}
}
