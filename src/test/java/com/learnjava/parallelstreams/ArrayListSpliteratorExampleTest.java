package com.learnjava.parallelstreams;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.learnjava.util.CommonUtil;
import com.learnjava.util.DataSet;

class ArrayListSpliteratorExampleTest {

	private ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

	@RepeatedTest(5)
	void multipleEachValue() {
		// given
		int size = 1_000_000;
		CommonUtil.stopWatch.reset();
		ArrayList<Integer> inputList = DataSet.generateArrayList(size);

		//when
		List<Integer> result = arrayListSpliteratorExample.multipleEachValue(inputList, 2, false);

		//then
		assertEquals(result.size(), 1_000_000);
	}

	@RepeatedTest(5)
	void multipleEachValue_Parallel() {
		// given
		int size = 1_000_000;
		CommonUtil.stopWatch.reset();
		ArrayList<Integer> inputList = DataSet.generateArrayList(size);

		//when
		List<Integer> result = arrayListSpliteratorExample.multipleEachValue(inputList, 2, true);

		//then
		assertEquals(result.size(), 1_000_000);
	}
}
