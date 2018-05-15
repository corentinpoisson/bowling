
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.Tuple;
import exceptions.InvalidTupleValueException;
import exceptions.TupleArrayOutOfBoundException;
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
			throws InvalidTupleValueException, TupleArrayOutOfBoundException {
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

			if (i > 0) {
				Tuple previousTuple = tuples.get(i - 1);

				if (previousTuple.getSum() == 10) {
					expectedScore += tuple.getFirstValue();
				}
			}
		}

		score = tupleService.getScore(tuples);

		// 3. Asserts
		assertEquals(expectedScore, score);
	}

	@Test
	public void tuplesArrayFirstTupleWithFirstValueEqualTen_Should_ReceiveNextTupleTwoValues()
			throws InvalidTupleValueException, TupleArrayOutOfBoundException {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		tupleService.addTupleToTupleArray(tuples, new Tuple(10, 0));
		tupleService.addTupleToTupleArray(tuples, new Tuple(5, 4));

		int expectedScore = 0;
		int score;

		// 2. Action
		for (int i = 0; i < tuples.size(); ++i) {
			Tuple tuple = tuples.get(i);
			expectedScore += tuple.getSum();

			if (i > 0) {
				Tuple previousTuple = tuples.get(i - 1);

				if (previousTuple.getFirstValue() == 10) {
					expectedScore += tuple.getSum();
				}
			}
		}

		score = tupleService.getScore(tuples);

		// 3. Asserts
		assertEquals(expectedScore, score);
	}

	@Test
	public void tuplesArrayWithTupleSumEqualTen_And_TupleFirstValueEqualTen_Should_GiveCorrectBonus()
			throws InvalidTupleValueException, TupleArrayOutOfBoundException {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		tupleService.addTupleToTupleArray(tuples, new Tuple(10, 0));
		tupleService.addTupleToTupleArray(tuples, new Tuple(5, 4));
		tupleService.addTupleToTupleArray(tuples, new Tuple(1, 9));
		tupleService.addTupleToTupleArray(tuples, new Tuple(10, 0));
		tupleService.addTupleToTupleArray(tuples, new Tuple(0, 10));
		tupleService.addTupleToTupleArray(tuples, new Tuple(0, 10));
		tupleService.addTupleToTupleArray(tuples, new Tuple(9, 1));
		tupleService.addTupleToTupleArray(tuples, new Tuple(4, 1));

		int expectedScore = 0;
		int score;

		// 2. Action
		for (int i = 0; i < tuples.size(); ++i) {
			Tuple tuple = tuples.get(i);
			expectedScore += tuple.getSum();

			if (i > 0) {
				Tuple previousTuple = tuples.get(i - 1);

				if (previousTuple.getFirstValue() == 10) {
					expectedScore += tuple.getSum();
				} else if (previousTuple.getSum() == 10) {
					expectedScore += tuple.getFirstValue();
				}

				if (i > 1) {
					Tuple secondPreviousTuple = tuples.get(i - 2);

					if (secondPreviousTuple.getFirstValue() == 10 && previousTuple.getFirstValue() == 10) {
						expectedScore += tuple.getFirstValue();
					}
				}
			}
		}

		score = tupleService.getScore(tuples);

		// 3. Asserts
		assertEquals(expectedScore, score);
	}

	@Test
	public void tuplesArrayScore_Should_BeEqualSumOfAllTuple_If_NoTupleSumEqualTen()
			throws InvalidTupleValueException, TupleArrayOutOfBoundException {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();

		for (int i = 0; i < 1 + Math.random() * 9; ++i) {
			tupleService.addTupleToTupleArray(tuples, new Tuple(i / 2, i / 2));
		}

		int expectedScore = 0;
		int sum;
		int score;

		// 2. Action
		for (Tuple tuple : tuples) {
			expectedScore += tuple.getFirstValue() + tuple.getSecondValue();
		}

		sum = tupleService.getTuplesSum(tuples);
		score = tupleService.getScore(tuples);

		// 3. Asserts
		assertEquals(expectedScore, sum);
		assertEquals(expectedScore, score);
		assertEquals(sum, score);
	}

	@Test
	public void tuplesArray_Should_BeAbleToContainTenTuples()
			throws InvalidTupleValueException, TupleArrayOutOfBoundException {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();

		// 2. Action
		for (int i = 0; i < 10; ++i) {
			tupleService.addTupleToTupleArray(tuples, new Tuple(0, 0));
		}
	}

	@Test
	public void tuplesArray_Should_ThrowException_When_AddingWayMoreThanTenTuples() {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		String expectedExceptionMessage = "Cannot add more than ten values in tuple array";

		// 3. Asserts
		assertThrows(TupleArrayOutOfBoundException.class, () -> {
			for (int i = 0; i < 20; ++i) {
				tupleService.addTupleToTupleArray(tuples, new Tuple(0, 0));
			}
		}, expectedExceptionMessage);
	}

	@Test
	public void tuplesArray_Should_ThrowException_When_AddingElevenTuples() {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		String expectedExceptionMessage = "Cannot add more than ten values in tuple array";

		// 3. Asserts
		assertThrows(TupleArrayOutOfBoundException.class, () -> {
			for (int i = 0; i < 11; ++i) {
				tupleService.addTupleToTupleArray(tuples, new Tuple(0, 0));
			}
		}, expectedExceptionMessage);
	}

	@Test
	public void tuplesWithSpare_Should_HaveScoreGreaterThanSum()
			throws InvalidTupleValueException, TupleArrayOutOfBoundException {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		for (int i = 0; i < 10; ++i) {
			tupleService.addTupleToTupleArray(tuples, new Tuple(5, 5));
		}

		int sum;
		int score;

		// 2. Action
		sum = tupleService.getTuplesSum(tuples);
		score = tupleService.getScore(tuples);

		// 3. Asserts
		assertTrue(score > sum);
	}

	@Test
	public void tuplesWithTenStrike_Should_HaveAScoreOfThreeHundred()
			throws InvalidTupleValueException, TupleArrayOutOfBoundException {
		// 1. Actors
		List<Tuple> tuples = new ArrayList<Tuple>();
		for (int i = 0; i < 12; ++i) {
			tupleService.addTupleToTupleArray(tuples, new Tuple(10, 0));
		}

		int sum;
		int score;

		// 2. Action
		sum = tupleService.getTuplesSum(tuples);
		score = tupleService.getScore(tuples);

		// 3. Asserts
		assertTrue(score > sum);
		assertEquals(300, score);
	}

	@Test
	public void cannotAddMoreThanTwoBonusToATuple() {
		// 1. Actors
		Tuple tuple = new Tuple(10, 0);
		int score;

		// 2. Action
		tuple.addScoreBonus(10);
		tuple.addScoreBonus(10);
		tuple.addScoreBonus(10);
		score = tuple.getScore();

		// 3. Asserts
		assertNotEquals(40, score);
		assertEquals(30, score);
	}
}
