package services;

import java.util.List;

import domain.Tuple;
import exceptions.InvalidTupleValueException;

public class TupleService {

	public void addTupleToTupleArray(List<Tuple> tuples, Tuple tuple) throws InvalidTupleValueException {
		if (tuple.getFirstValue() < 0 || tuple.getSecondValue() < 0) {
			throw new InvalidTupleValueException("Tuple cannot take negative value");
		}

		if (tuple.getSum() > 10) {
			throw new InvalidTupleValueException("Tuple sum cannot be above ten");
		}

		if (tuples.size() < 1) {

		} else {
			Tuple previousTuple = tuples.get(tuples.size() - 2);

			if (previousTuple.getFirstValue() == 10) {
				previousTuple.addScoreBonus(tuple.getSum());
			} else if (previousTuple.getSum() == 10) {
				previousTuple.addScoreBonus(tuple.getFirstValue());
			}
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
