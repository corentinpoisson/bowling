package services;

import java.util.List;

import domain.Tuple;
import exceptions.InvalidTupleValueException;
import exceptions.TupleArrayOutOfBoundException;

public class TupleService {

	public void addTupleToTupleArray(List<Tuple> tuples, Tuple tuple)
			throws InvalidTupleValueException, TupleArrayOutOfBoundException {
		if (tuples.size() > 9) {
			// TODO: Check if the last tuple is a strike or a spare
			// If it's a strike, we can add up to two more values to the score
			// Either the next two strikes, or the values of the next Tuple
			// If it's a spare, just take the first value of the next Tuple
			
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

			if (previousTuple.getFirstValue() == 10) {
				previousTuple.addScoreBonus(tuple.getSum());
			} else if (previousTuple.getSum() == 10) {
				previousTuple.addScoreBonus(tuple.getFirstValue());
			}

			if (tuples.size() > 1) {
				Tuple secondPreviousTuple = tuples.get(tuples.size() - 2);

				if (secondPreviousTuple.getFirstValue() == 10 && previousTuple.getFirstValue() == 10) {
					secondPreviousTuple.addScoreBonus(tuple.getFirstValue());
				}
			}
		}

		tuples.add(tuple);
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
