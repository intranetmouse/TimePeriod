package org.intranet.timeperiod;

public class LongTimePeriod
	implements TimePeriod<Long, Long>
{
	private final Long start;
	public Long getStart() { return start; }
	public boolean hasStart() { return start != null; }

	private final Long end;
	public Long getEnd() { return end; }
	public boolean hasEnd() { return end != null; }

	private final Long duration;
	public Long getDuration() { return duration; }

	public LongTimePeriod(Long start, Long end)
	{
		if (start != null && end != null)
		{
			if (start > end)
				throw new IllegalArgumentException();
			duration = end - start;
		}
		else
			duration = null;
		this.start = start;
		this.end = end;
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof LongTimePeriod))
			return false;
		LongTimePeriod otherPeriod = (LongTimePeriod)other;
		return (start == otherPeriod.start || start.compareTo(otherPeriod.start) == 0)
			&& (end == otherPeriod.end || end.compareTo(otherPeriod.end) == 0);
	}

	@Override
	public TimePeriod<Long, Long> createPeriod(Long start, Long end)
	{ return new LongTimePeriod(start, end); }

	@Override
	public Long add(Long base, Long duration) { return base + duration; }

	@Override
	public Long subtract(Long base, Long duration) { return base - duration; }
}
