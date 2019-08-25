package org.intranet.timeperiod;

/**
* Inspired by:
* <ul>
*   <li><a href="https://www.codeproject.com/Articles/168662/Time-Period-Library-for-NET">Time Period Library for .NET</a></li>
*   <li><a href="https://www.threeten.org/threeten-extra/apidocs/org.threeten.extra/org/threeten/extra/Interval.html">Three Ten - Interval</a></li>
*   <li><a href="http://jaret.de/timebars/index.html">Jaret Timebars</a></li>
* </ul>
* @param <T> The time type.
* @param <DUR> The duration type (time span).
*/
public interface TimePeriod<T extends Comparable<T>, DUR>
{
	T getStart();
	boolean hasStart();

	T getEnd();
	boolean hasEnd();

	/**
	* @return {@code end} minus {@code start}
	*/
	DUR getDuration();

	T add(T base, DUR duration);
	T subtract(T base, DUR duration);

	TimePeriod<T, DUR> createPeriod(T start, T end);

	// ------------------------------------------------------------------------

	default TimePeriod<T, DUR> createPeriod(T start, DUR duration)
	{ return createPeriod(start, add(start, duration)); }
	default TimePeriod<T, DUR> createPeriod(DUR duration, T end)
	{ return createPeriod(subtract(end, duration), end); }

	default TimePeriod<T, DUR> withStart(T start)
	{ return createPeriod(start, getEnd()); }
	default TimePeriod<T, DUR> withEnd(T end)
	{ return createPeriod(getStart(), end); }
	default TimePeriod<T, DUR> withDuration(DUR duration)
	{ return createPeriod(getStart(), add(getStart(), duration)); }

	default TimePeriod<T, DUR> move(DUR duration)
	{ return createPeriod(add(getStart(), duration), add(getEnd(), duration)); }

	default TimePeriod<T, DUR> expandBeginning(DUR duration)
	{ return createPeriod(subtract(getStart(), duration), getEnd()); }
	default TimePeriod<T, DUR> expandEnd(DUR duration)
	{ return createPeriod(getStart(), add(getEnd(), duration)); }
	default TimePeriod<T, DUR> expand(DUR startDuration, DUR endDuration)
	{ return createPeriod(subtract(getStart(), startDuration), add(getEnd(), endDuration)); }

	/** A point in time.
	* @return {@code true} if {@code start.compareTo(end) == 0}.
	*/
	default boolean isMoment() { return hasStart() && getStart().compareTo(getEnd()) == 0; }

	/**
	* @return {@code true} if neither {@code start} nor {@code end} times are
	* defined.
	*/
	default boolean isAnytime() { return !hasStart() && !hasEnd(); }

	default boolean samePeriod(TimePeriod<T, DUR> other)
	{
		return getStart().compareTo(other.getStart()) == 0 &&
			getEnd().compareTo(other.getEnd()) == 0;
	}

	default boolean hasInside(TimePeriod<T, DUR> other)
	{
		return getStart().compareTo(other.getStart()) <= 0 &&
			getEnd().compareTo(other.getEnd()) >= 0;
	}

	default boolean overlapsWith(TimePeriod<T, DUR> other)
	{
		return getEnd().compareTo(other.getStart()) > 0 &&
			other.getEnd().compareTo(getStart()) > 0;
	}

	default boolean intersectsWith(TimePeriod<T, DUR> other)
	{
		return getEnd().compareTo(other.getStart()) >= 0 &&
			other.getEnd().compareTo(getStart()) >= 0;
	}
}
