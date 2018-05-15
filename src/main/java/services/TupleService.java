package services;

import java.util.List;

import domain.Tuple;
import exceptions.InvalidTupleValueException;
import exceptions.TupleArrayOutOfBoundException;

public class TupleService {

	public void addTupleToTupleArray(List<Tuple> tuples, Tuple tuple)
			throws InvalidTupleValueException, TupleArrayOutOfBoundException {

		if (tuples.size() > 9) {
			Tuple lastTuple = tuples.get(tuples.size() - 1);

			// TODO: Check if the last tuple is a strike or a spare
			// If it's a strike, we can add up to two more values to the score
			if (lastTuple.getFirstValue() == 10) {
				if (tuple.getAmountOfBonusAdded() < 2) {
					if (tuple.getFirstValue() == 10) {
						lastTuple.addScoreBonus(tuple.getFirstValue());
						tuples.get(tuples.size()-2).addScoreBonus(tuple.getFirstValue());
					} else {
						lastTuple.addScoreBonus(tuple.getFirstValue());
						lastTuple.addScoreBonus(tuple.getSecondValue());
					}
					return;
				}
			} else if (lastTuple.getSum() == 10) {
				if (tuple.getAmountOfBonusAdded() < 1) {
					lastTuple.addScoreBonus(tuple.getFirstValue());
					return;
				}
			}
			throw new TupleArrayOutOfBoundException("Cannot add more than ten values in tuple array");
		}

		if (tuple.getFirstValue() < 0 || tuple.getSecondValue() < 0) {
			throw new InvalidTupleValueException("Tuple cannot take negative value");
		}

		if (tuple.getSum() > 10) {
			throw new InvalidTupleValueException("Tuple sum cannot be above ten");
		}

		if (tuples.size() > 0) {
			Tuple previousTuple = tuples.get(tuples.size() - 1);

			strikeAndSpareBonus(tuple, previousTuple);

			if (tuples.size() > 1) {
				Tuple secondPreviousTuple = tuples.get(tuples.size() - 2);

				if (secondPreviousTuple.getFirstValue() == 10 && previousTuple.getFirstValue() == 10) {
					secondPreviousTuple.addScoreBonus(tuple.getFirstValue());
				}
			}
		}

		tuples.add(tuple);
	}

	private void strikeAndSpareBonus(Tuple tuple, Tuple previousTuple) {
		if (previousTuple.getFirstValue() == 10) {
			previousTuple.addScoreBonus(tuple.getSum());
		} else if (previousTuple.getSum() == 10) {
			previousTuple.addScoreBonus(tuple.getFirstValue());
		}
	}

	public int getTuplesSum(List<Tuple> tuples) {
		int sums = 0;

		for (Tuple tuple : tuples) {
			sums += tuple.getSum();
		}

		return sums;
	}

	public int getScore(List<Tuple> tuples) {
		int sumScore = 0;

		for (Tuple tuple : tuples) {
			sumScore += tuple.getScore();
		}

		return sumScore;
	}

}
