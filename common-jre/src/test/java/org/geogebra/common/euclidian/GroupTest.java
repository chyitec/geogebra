package org.geogebra.common.euclidian;

import java.util.ArrayList;
import java.util.List;

import org.geogebra.common.factories.AwtFactoryCommon;
import org.geogebra.common.jre.headless.AppCommon;
import org.geogebra.common.jre.headless.LocalizationCommon;
import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoPolygon;
import org.geogebra.common.kernel.geos.groups.Group;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GroupTest {
	private Construction construction;

	@Before
	public void setUp() {
		AwtFactoryCommon factoryCommon = new AwtFactoryCommon();
		AppCommon app = new AppCommon(new LocalizationCommon(2), factoryCommon);
		construction = app.getKernel().getConstruction();
	}

	@Test
	public void testGeosNotGrupped() {
		Assert.assertFalse(isGeosInSameGroup(withGivenNumberOfGeos(2)));
	}

	private ArrayList<GeoElement> withGivenNumberOfGeos(int count) {
		ArrayList<GeoElement> geos = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			GeoPolygon polygon = new GeoPolygon(construction);
			polygon.setLabel("label" + i);
			geos.add(polygon);
		}
		return geos;
	}

	@Test
	public void testCreateGroup() {
		ArrayList<GeoElement> geos = withGivenNumberOfGeos(3);
		construction.createGroup(geos);
		Assert.assertTrue(isGeosInSameGroup(geos));
	}

	@Test
	public void testCreateTwoDifferentGroups() {
		ArrayList<GeoElement> geos1 = withGivenNumberOfGeos(3);
		ArrayList<GeoElement> geos2 = withGivenNumberOfGeos(5);
		construction.createGroup(geos1);
		construction.createGroup(geos2);
		geos1.addAll(geos2);
		Assert.assertFalse(isGeosInSameGroup(geos1));
	}

	private boolean isGeosInSameGroup(ArrayList<GeoElement> geos) {
		if (geos.size() == 0 || !geos.get(0).hasGroup()) {
			return false;
		}
		Group group = geos.get(0).getParentGroup();
		for (int i = 1; i < geos.size(); i++) {
			if (geos.get(i).getParentGroup() != group) {
				return false;
			}
		}
		return true;
	}

	@Test
	public void testGrouppedGeos() {
		ArrayList<GeoElement> geos = withGivenNumberOfGeos(5);
		Group group = new Group(geos);
		List<GeoElement> result = group.getGroupedGeos();
		Assert.assertArrayEquals(geos.toArray(), result.toArray());
	}
}
