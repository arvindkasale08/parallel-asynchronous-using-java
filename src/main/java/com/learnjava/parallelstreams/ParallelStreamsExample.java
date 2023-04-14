package com.learnjava.parallelstreams;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.learnjava.util.DataSet;
import com.learnjava.util.LoggerUtil;

public class ParallelStreamsExample {

	public List<String> stringTransform(List<String> namesList, boolean isParallel) {

		Stream<String> namesStream = namesList.stream();

		if (isParallel) {
			namesStream.parallel();
		}

		return namesStream
			.map(this::addNameLengthTransform)
			.collect(Collectors.toList());
	}

	public static void main(String[] args) {
		List<String> namesList = DataSet.namesList();
		ParallelStreamsExample example = new ParallelStreamsExample();
		startTimer();
		List<String> resultList = example.stringTransform(namesList, false);
		LoggerUtil.log("resultList "+ resultList);
		timeTaken();
	}

	private String addNameLengthTransform(String name) {
		delay(500);
		return name.length()+" - "+name ;
	}
}
