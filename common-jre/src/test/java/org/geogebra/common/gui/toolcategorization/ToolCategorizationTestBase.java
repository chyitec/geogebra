package org.geogebra.common.gui.toolcategorization;

import org.geogebra.common.BaseUnitTest;
import org.geogebra.common.euclidian.EuclidianConstants;

import java.util.Arrays;
import java.util.List;

/**
 * Base class for tool categorization tests.
 */
public class ToolCategorizationTestBase extends BaseUnitTest {

    List<Integer> notAllowedToolsGraphingCalc = Arrays.asList(
        EuclidianConstants.MODE_SEGMENT,
        EuclidianConstants.MODE_IMAGE,
        EuclidianConstants.MODE_ANGLE,
        EuclidianConstants.MODE_ANGLE_FIXED,
        EuclidianConstants.MODE_SLOPE,
        EuclidianConstants.MODE_MIDPOINT,
        EuclidianConstants.MODE_LINE_BISECTOR,
        EuclidianConstants.MODE_PARALLEL,
        EuclidianConstants.MODE_ANGULAR_BISECTOR,
        EuclidianConstants.MODE_TANGENTS,
        EuclidianConstants.MODE_LOCUS,
        EuclidianConstants.MODE_SEGMENT_FIXED,
        EuclidianConstants.MODE_POLAR_DIAMETER,
        EuclidianConstants.MODE_POLYLINE,
        EuclidianConstants.MODE_POLYGON,
        EuclidianConstants.MODE_REGULAR_POLYGON,
        EuclidianConstants.MODE_VECTOR_POLYGON,
        EuclidianConstants.MODE_RIGID_POLYGON,
        EuclidianConstants.MODE_SHAPE_CIRCLE,
        EuclidianConstants.MODE_COMPASSES,
        EuclidianConstants.MODE_SEMICIRCLE,
        EuclidianConstants.MODE_CIRCLE_POINT_RADIUS_DIRECTION,
        EuclidianConstants.MODE_CIRCLE_POINT_RADIUS,
        EuclidianConstants.MODE_CIRCLE_ARC_THREE_POINTS,
        EuclidianConstants.MODE_CIRCLE_THREE_POINTS,
        EuclidianConstants.MODE_CIRCLE_SECTOR_THREE_POINTS,
        EuclidianConstants.MODE_CIRCLE_TWO_POINTS,
        EuclidianConstants.MODE_CIRCLE_AXIS_POINT,
        EuclidianConstants.MODE_CIRCUMCIRCLE_ARC_THREE_POINTS,
        EuclidianConstants.MODE_CIRCUMCIRCLE_SECTOR_THREE_POINTS,
        EuclidianConstants.MODE_SHAPE_ELLIPSE,
        EuclidianConstants.MODE_CONIC_FIVE_POINTS,
        EuclidianConstants.MODE_PARABOLA,
        EuclidianConstants.MODE_HYPERBOLA_THREE_POINTS,
        EuclidianConstants.MODE_MIRROR_AT_LINE,
        EuclidianConstants.MODE_MIRROR_AT_POINT,
        EuclidianConstants.MODE_MOVE_ROTATE,
        EuclidianConstants.MODE_TRANSLATE_BY_VECTOR,
        EuclidianConstants.MODE_DILATE_FROM_POINT,
        EuclidianConstants.MODE_MIRROR_AT_CIRCLE,
        EuclidianConstants.MODE_RELATION);

    protected boolean isInGraphingToolSet(int tool) {
        return notAllowedToolsGraphingCalc.contains(tool);
    }
}