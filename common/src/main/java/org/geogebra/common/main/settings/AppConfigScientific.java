package org.geogebra.common.main.settings;

import org.geogebra.common.GeoGebraConstants;
import org.geogebra.common.io.layout.Perspective;
import org.geogebra.common.kernel.arithmetic.filter.OperationArgumentFilter;
import org.geogebra.common.kernel.commands.filter.CommandArgumentFilter;
import org.geogebra.common.kernel.commands.selector.CommandFilter;
import org.geogebra.common.kernel.commands.selector.CommandFilterFactory;
import org.geogebra.common.kernel.parser.function.ParserFunctions;
import org.geogebra.common.kernel.parser.function.ParserFunctionsFactory;
import org.geogebra.common.kernel.geos.properties.FillType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Config for Scientific Calculator app
 */
public class AppConfigScientific extends AppConfigGraphing {

	@Override
	public String getAppTitle() {
		return "ScientificCalculator";
	}

	@Override
	public String getAppName() {
		return "GeoGebraScientificCalculator";
	}

	@Override
	public String getAppNameShort() {
		return "ScientificCalculator.short";
	}

	@Override
	public String getTutorialKey() {
		return "TutorialScientific";
	}

	@Override
	public boolean allowsSuggestions() {
		return false;
	}

	@Override
	public boolean isGreekAngleLabels() {
		return false;
	}

	@Override
	public String getForcedPerspective() {
		return Perspective.SCIENTIFIC + "";
	}

	@Override
	public boolean hasScientificKeyboard() {
		return true;
	}

	@Override
	public boolean isEnableStructures() {
		return false;
	}

	@Override
	public boolean hasSlidersInAV() {
		return false;
	}

	@Override
	public boolean hasAutomaticLabels() {
		return false;
	}

	@Override
	public boolean hasAutomaticSliders() {
		return false;
	}

	@Override
	public CommandFilter getCommandFilter() {
		return CommandFilterFactory.createSciCalcCommandFilter();
	}

	@Override
	public CommandArgumentFilter getCommandArgumentFilter() {
		return null;
	}

	@Override
	public String getAppCode() {
		return "scientific";
	}

	@Override
	public GeoGebraConstants.Version getVersion() {
		return GeoGebraConstants.Version.SCIENTIFIC;
	}

	@Override
	public boolean hasExam() {
		return false;
	}

	@Override
	public String getExamMenuItemText() {
		return "";
	}

	@Override
	public Set<FillType> getAvailableFillTypes() {
		return new HashSet<>(Arrays.asList(FillType.values()));
	}

	@Override
	public boolean isObjectDraggingRestricted() {
		return false;
	}

	@Override
	public int getEnforcedLineEquationForm() {
		return -1;
	}

	@Override
	public int getEnforcedConicEquationForm() {
		return -1;
	}

	@Override
	public OperationArgumentFilter createOperationArgumentFilter() {
		return null;
	}

	@Override
	public ParserFunctions createParserFunctions() {
		return ParserFunctionsFactory.createParserFunctions();
	}

	@Override
	public boolean shouldHideEquations() {
		return false;
	}
}
