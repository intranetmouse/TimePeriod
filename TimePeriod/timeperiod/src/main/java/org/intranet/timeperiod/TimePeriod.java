package org.intranet.timeperiod;

/**
*
* @param <T> The time type.
* @param <TS> The duration type (time span).
*/
public interface TimePeriod<T extends Comparable<T>, TS>
{
	T getStart();
	boolean hasStart();

	T getEnd();
	boolean hasEnd();

	/**
	* @return {@code end} minus {@code start}
	*/
	TS getDuration();

	T add(T base, TS duration);
	T subtract(T base, TS duration);

	TimePeriod<T, TS> createPeriod(T start, T end);

	// ------------------------------------------------------------------------

	default TimePeriod<T, TS> createPeriod(T start, TS duration)
	{ return createPeriod(start, add(start, duration)); }
	default TimePeriod<T, TS> createPeriod(TS duration, T end)
	{ return createPeriod(subtract(end, duration), end); }

	default TimePeriod<T, TS> changeStart(T start)
	{ return createPeriod(start, getEnd()); }
	default TimePeriod<T, TS> changeEnd(T end)
	{ return createPeriod(getStart(), end); }
	default TimePeriod<T, TS> changeDuration(TS duration)
	{ return createPeriod(getStart(), add(getStart(), duration)); }

	/** A point in time.
	* @return {@code true} if {@code start.compareTo(end) == 0}.
	*/
	default boolean isMoment() { return !isAnytime() && getStart().compareTo(getEnd()) == 0; }

	/**
	* @return {@code true} if neither {@code start} nor {@code end} times are
	* defined.
	*/
	default boolean isAnytime() { return !hasStart() && !hasEnd(); }

	default boolean samePeriod(TimePeriod<T, TS> other)
	{
		return getStart().compareTo(other.getStart()) == 0 &&
			getEnd().compareTo(other.getEnd()) == 0;
	}

	default boolean hasInside(TimePeriod<T, TS> other)
	{
		return getStart().compareTo(other.getStart()) <= 0 &&
			getEnd().compareTo(other.getEnd()) >= 0;
	}

	default boolean overlapsWith(TimePeriod<T, TS> other)
	{
		return getEnd().compareTo(other.getStart()) > 0 &&
			other.getEnd().compareTo(getStart()) > 0;
	}

	default boolean intersectsWith(TimePeriod<T, TS> other)
	{
		return getEnd().compareTo(other.getStart()) >= 0 &&
			other.getEnd().compareTo(getStart()) >= 0;
	}
}
