package com.learnjava.parallelstreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.learnjava.util.LoggerUtil;

public class ParallelStreamResultOrder {

	public static List<Integer> listOrder(List<Integer> inputList) {
		return inputList
			.parallelStream()
			.map(integer -> integer * 2)
			.collect(Collectors.toList());
	}

	public static Set<Integer> setOrder(List<Integer> inputList) {
		return inputList
			.parallelStream()
			.map(integer -> integer * 2)
			.collect(Collectors.toSet());
	}

	public static void main(String[] args) {
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);
		List<Integer> result = listOrder(list);
		Set<Integer> result2 = setOrder(list);
		LoggerUtil.log("Result "+ result);
		LoggerUtil.log("Result2 "+ result2);
	}
}
