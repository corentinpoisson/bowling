
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.Tuple;
import exceptions.InvalidTupleValueException;
import services.TupleService;

class TupleTest {

	private TupleService tupleService = new TupleService();

	@Test
	public void sumTupleValues_Should_BeEqualManualSum() {
		// 1. Actors
		int firstValue = 3;
		int secondValue = 5;

		Tuple tuple = new Tuple(firstValue, secondValue);
		int sumTuple;

		// 2. Action
		sumTuple = tuple.getSum();

		// 3. Asserts
		assertEquals(firstValue + secondValue, sumTuple);
	}

	@Test
	public void tuplesSums_Should_BeEqualToTuplesValuesSums() {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();

		for (int i = 0; i < 10; ++i) {
			int firstValue = (int) (Math.random() * 5);
			int secondValue = (int) (Math.random() * 5);

			Tuple tuple = new Tuple(firstValue, secondValue);
			tuples.add(tuple);
		}

		int expectedSum = 0;
		int sum;

		// 2. Action
		for (Tuple tuple : tuples) {
			expectedSum += tuple.getSum();
		}

		sum = tupleService.getTuplesSum(tuples);

		// 3. Asserts
		assertEquals(expectedSum, sum);
	}

	@Test
	public void negativeTupleValue_Should_ThrowException() {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		Tuple tuple = new Tuple(-1, 0);
		String expectedExceptionMessage = "Tuple cannot take negative value";

		// 3. Asserts
		assertThrows(InvalidTupleValueException.class, () -> {
			tupleService.addTupleToTupleArray(tuples, tuple);
		}, expectedExceptionMessage);
	}

	@Test
	public void tupleWithSumAboveTen_Should_ThrowException() {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		Tuple tuple = new Tuple(5, 7);
		String expectedExceptionMessage = "Tuple sum cannot be above ten";

		// 3. Asserts
		assertThrows(InvalidTupleValueException.class, () -> {
			tupleService.addTupleToTupleArray(tuples, tuple);
		}, expectedExceptionMessage);
	}

	@Test
	public void tuplesArrayFirstTupleWithSumEqualTen_Should_ReceiveNextTupleFirstValue()
			throws InvalidTupleValueException {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		tupleService.addTupleToTupleArray(tuples, new Tuple(5, 5));
		tupleService.addTupleToTupleArray(tuples, new Tuple(5, 4));
		
		int expectedScore = 0;
		int score;

		// 2. Action
		for (int i = 0; i < tuples.size(); ++i) {
			Tuple tuple = tuples.get(i);
			expectedScore += tuple.getSum();

			if (tuple.getSum() == 10 && i > 0) {
				expectedScore += tuples.get(i - 1).getFirstValue();
			}
		}

		score = tupleService.getScore(tuples);

		// 3. Asserts
		assertEquals(expectedScore, score);
	}

	@Test
	public void tuplesArrayFirstTupleWithFirstValueEqualTen_Should_ReceiveNextTupleTwoValues()
			throws InvalidTupleValueException {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		tupleService.addTupleToTupleArray(tuples, new Tuple(10, 0));
		tupleService.addTupleToTupleArray(tuples, new Tuple(5, 4));
		tupleService.addTupleToTupleArray(tuples, new Tuple(0, 10));
		tupleService.addTupleToTupleArray(tuples, new Tuple(5, 4));

		int expectedScore = 0;
		int score;

		// 2. Action
		for (int i = 0; i < tuples.size(); ++i) {
			Tuple tuple = tuples.get(i);
			expectedScore += tuple.getSum();

			if (tuple.getFirstValue() == 10 && i > 0) {
				expectedScore += tuples.get(i - 1).getSum();
			}
		}

		score = tupleService.getScore(tuples);

		// 3. Asserts
		assertEquals(expectedScore, score);
	}

}
