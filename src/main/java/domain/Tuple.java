package domain;

public class Tuple {

	private final int firstValue;
	private final int secondValue;

	private Integer score = null;
	private int amountOfBonusAdded = 0;

	public Tuple(int firstValue, int secondValue) {
		this.firstValue = firstValue;
		this.secondValue = secondValue;
		this.score = firstValue + secondValue;
	}

	public int getSum() {
		return firstValue + secondValue;
	}

	public int getFirstValue() {
		return firstValue;
	}

	public int getSecondValue() {
		return secondValue;
	}

	public int getAmountOfBonusAdded() {
		return amountOfBonusAdded;
	}

	public Integer getScore() {
		if (score == null) {
			return this.getSum();
		}
		return score;
	}

	public void addScoreBonus(int scoreBonus) {
		if (amountOfBonusAdded++ < 2) {
			this.score += scoreBonus;
		}
	}
}
