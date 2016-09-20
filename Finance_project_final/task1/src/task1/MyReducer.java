package task1;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<IntWritable, CustomValue, NullWritable, Text> {

	@Override
	protected void reduce(IntWritable arg0, Iterable<CustomValue> arg1,Context context)
			throws IOException, InterruptedException {
		double total_income=0;
		double total_income_male = 0;
		double total_income_female = 0;
		double total_tax_collectable = 0;
		double count_filers = 0;
		for(CustomValue cv : arg1){
			DoubleWritable income = cv.getIncome();
			DoubleWritable taxper = cv.getTaxper();
			Text gender = cv.getGender();
			total_income+=income.get();
			total_tax_collectable += cv.getIncome().get()* taxper.get()/100;
			if(gender.toString().trim().equals("Male"))
			{
				total_income_male+=cv.getIncome().get()* taxper.get()/100;
			}
			else
			{
				total_income_female+=cv.getIncome().get()* taxper.get()/100;
			}
			if(cv.getTaxfiler().toString().trim().toUpperCase().equals("NONFILER")==false)
			{
				count_filers++;
			}
		 
			
		}
		context.write(null, new Text("Total Income : " + total_income + " Total Tax Collected : " + total_tax_collectable + " Total Tax from Males : " + total_income_male + " Total Tax from Females : " + total_income_female + " No. of Non Tax Filers : " + count_filers ) );
	}
	

}
