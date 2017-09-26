package com.example.banner.pojo;

import java.util.Date;
import java.util.Optional;
import lombok.Value;

@Value
public class DateRange {
	private final Optional<Date> lowerBound;
	private final Optional<Date> upperBound;
}
