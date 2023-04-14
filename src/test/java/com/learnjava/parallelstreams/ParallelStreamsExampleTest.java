package com.learnjava.parallelstreams;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.learnjava.util.DataSet;

import static com.learnjava.util.CommonUtil.startTimer;

class ParallelStreamsExampleTest {

	private ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	void stringTransform(boolean isParallel) {

		//given
		List<String> inputList = DataSet.namesList();

		// when
		stopWatch.reset();
		startTimer();
		List<String> resultList = parallelStreamsExample.stringTransform(inputList, isParallel);
		timeTaken();

		// then
		assertEquals(resultList.size(), 4);
		resultList.forEach(s -> assertTrue(s.contains("-")));
	}
}
