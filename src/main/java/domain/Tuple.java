package domain;

public class Tuple {

	private final int firstValue;
	private final int secondValue;

	private Integer score = null;

	public Tuple(int firstValue, int secondValue) {
		this.firstValue = firstValue;
		this.secondValue = secondValue;
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

	public Integer getScore() {
		return score;
	}

	public void addScoreBonus(int scoreBonus) {
		this.score = getSum() + scoreBonus;
	}
}
