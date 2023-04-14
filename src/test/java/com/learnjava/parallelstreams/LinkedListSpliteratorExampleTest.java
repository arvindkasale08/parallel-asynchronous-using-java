package com.learnjava.parallelstreams;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.learnjava.util.CommonUtil;
import com.learnjava.util.DataSet;

class LinkedListSpliteratorExampleTest {

	private LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

	@RepeatedTest(5)
	void multipleEachValue() {
		System.out.println("Running Sequentially test");
		// given
		int size = 1_000_000;
		CommonUtil.stopWatch.reset();
		LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

		//when
		List<Integer> result = linkedListSpliteratorExample.multipleEachValue(inputList, 2, false);

		//then
		assertEquals(result.size(), 1_000_000);
	}

	@RepeatedTest(5)
	void multipleEachValue_Parallel() {
		System.out.println("Running Parallel test");
		// given
		int size = 1_000_000;
		CommonUtil.stopWatch.reset();
		LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

		//when
		List<Integer> result = linkedListSpliteratorExample.multipleEachValue(inputList, 2, true);

		//then
		assertEquals(result.size(), 1_000_000);
	}
}
