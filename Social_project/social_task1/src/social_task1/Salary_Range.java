package social_task1;

public class Salary_Range implements Comparable<Object> {

	 public Salary_Range(int min_income, int max_income, int pension) {
		this.min_income = min_income;
		this.max_income = max_income;
		this.pension = pension;
	}

	int min_income,max_income,pension;

	public Salary_Range() {
		super();
	}

	public int getMin_income() {
		return min_income;
	}

	public void setMin_income(int min_income) {
		this.min_income = min_income;
	}

	public int getMax_income() {
		return max_income;
	}

	public void setMax_income(int max_income) {
		this.max_income = max_income;
	}

	public int getPension() {
		return pension;
	}

	public void setPension(int pension) {
		this.pension = pension;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean salary_in_range(int income)
	{
		if(income>=min_income && income<=max_income)
			return true;
		return false;
	}
}
