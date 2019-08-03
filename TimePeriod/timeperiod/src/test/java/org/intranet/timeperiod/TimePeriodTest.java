package org.intranet.timeperiod;

import org.junit.Assert;
import org.junit.Test;

public class TimePeriodTest
{
	LongTimePeriod baseTp = new LongTimePeriod(10L, 20L);

	/**
	* From cases shown on <a href="https://www.codeproject.com/Articles/168662/Time-Period-Library-for-NET">TimePeriod for .NET</a>.
	*/
	@Test
	public void testPeriodRelations()
	{

		testPeriod(baseTp,  3,  8, false, false, false, false, "after");
		testPeriod(baseTp,  3, 10, false, false, false, true , "start touching");
		testPeriod(baseTp,  3, 12, false, false, true , true , "start inside");
		testPeriod(baseTp, 10, 25, false, false, true , true , "inside start touching");
		testPeriod(baseTp, 10, 18, false, true , true , true , "enclosing start touching");
		testPeriod(baseTp, 13, 18, false, true , true , true , "enclosing");
		testPeriod(baseTp, 13, 20, false, true , true , true , "enclosing end touching");
		testPeriod(baseTp, 10, 20, true , true , true , true , "exact match");
		testPeriod(baseTp,  3, 25, false, false, true , true , "inside");
		testPeriod(baseTp,  3, 20, false, false, true , true , "inside end touching");
		testPeriod(baseTp, 15, 28, false, false, true , true , "end inside");
		testPeriod(baseTp, 20, 28, false, false, false, true , "end touching");
		testPeriod(baseTp, 23, 38, false, false, false, false, "before");
	}

	private void testPeriod(LongTimePeriod baseTp, long start, long end,
		boolean samePeriod, boolean hasInside, boolean overlapsWith,
		boolean intersectsWith, String type)
	{
		TimePeriod<Long, Long> testTp = new LongTimePeriod(start, end);

		Assert.assertEquals("Same Period " + type    , samePeriod, baseTp.samePeriod(testTp));
		Assert.assertEquals("Has Inside " + type     , hasInside, baseTp.hasInside(testTp));
		Assert.assertEquals("Overlaps With " + type  , overlapsWith, baseTp.overlapsWith(testTp));
		Assert.assertEquals("Intersects With " + type, intersectsWith, baseTp.intersectsWith(testTp));
	}

	@Test
	public void testChangeMethods()
	{
		assertTimePeriodsEqual(new LongTimePeriod(5L, 20L),
			baseTp.changeStart(5L));

		assertTimePeriodsEqual(new LongTimePeriod(10L, 29L),
			baseTp.changeEnd(29L));

		assertTimePeriodsEqual(new LongTimePeriod(10L, 29L),
			baseTp.changeEnd(29L));
	}

	private static <T extends Comparable<T>, TS> void assertTimePeriodsEqual(
		TimePeriod<T, TS> expectedChangeStart,
		TimePeriod<T, TS> actualChangeStart)
	{
		Assert.assertEquals(expectedChangeStart.getStart(),
			actualChangeStart.getStart());
		Assert.assertEquals(expectedChangeStart.getEnd(),
			actualChangeStart.getEnd());
	}
}
