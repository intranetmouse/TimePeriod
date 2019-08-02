package org.intranet.timeperiod;

/**
*
* @param <T> The time type.
* @param <D> The duration type.
*/
public interface TimePeriod<T extends Comparable<T>, D>
{
	T getStart();
	boolean hasStart();

	T getEnd();
	boolean hasEnd();

	/** A point in time.
	* @return {@code true} if {@code start.compareTo(end) == 0}.
	*/
	default boolean isMoment() { return !isAnytime() && getStart().compareTo(getEnd()) == 0; }

	/**
	* @return {@code true} if neither {@code start} nor {@code end} times are
	* defined.
	*/
	default boolean isAnytime() { return !hasStart() && !hasEnd(); }

	/**
	* @return {@code end} minus {@code start}
	*/
	D getDuration();

	default boolean samePeriod(TimePeriod<T, D> other)
	{
		return getStart().compareTo(other.getStart()) == 0 &&
			getEnd().compareTo(other.getEnd()) == 0;
	}

	default boolean hasInside(TimePeriod<T, D> other)
	{
		return getStart().compareTo(other.getStart()) <= 0 &&
			getEnd().compareTo(other.getEnd()) >= 0;
	}

	default boolean overlapsWith(TimePeriod<T, D> other)
	{
		return getEnd().compareTo(other.getStart()) > 0 &&
			other.getEnd().compareTo(getStart()) > 0;
	}

	default boolean intersectsWith(TimePeriod<T, D> other)
	{
		return getEnd().compareTo(other.getStart()) >= 0 &&
			other.getEnd().compareTo(getStart()) >= 0;
	}
}
