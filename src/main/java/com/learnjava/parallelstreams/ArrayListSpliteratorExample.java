package com.learnjava.parallelstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.learnjava.util.CommonUtil;

public class ArrayListSpliteratorExample {

	public List<Integer> multipleEachValue(ArrayList<Integer> inputList, int multiple, boolean isParallel) {
		CommonUtil.startTimer();
		Stream<Integer> integerStream = inputList.stream(); // sequential

		if (isParallel) {
			integerStream.parallel();
		}

		List<Integer> result = integerStream
			.map(integer -> integer * multiple)
			.collect(Collectors.toList());
		CommonUtil.timeTaken();
		return result;
	}
}
