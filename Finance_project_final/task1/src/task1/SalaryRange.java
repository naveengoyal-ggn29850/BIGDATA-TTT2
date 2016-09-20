package task1;

public class SalaryRange implements Comparable{
	public double getMin_income() {
		return min_income;
	}
	public void setMin_income(double min_income) {
		this.min_income = min_income;
	}
	public double getMax_income() {
		return max_income;
	}
	public void setMax_income(double max_income) {
		this.max_income = max_income;
	}
	public SalaryRange(double min_income, double max_income) {
		super();
		this.min_income = min_income;
		this.max_income = max_income;
	}
	double min_income;
	public SalaryRange() {
		super();
	}
	double max_income;
	
	public boolean isIncomeInRange(double income)
	{
		if(income >=min_income && income>=max_income)
			return true;
		return false;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
