package social_task1;

import java.io.IOException;



import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<IntWritable,Text,NullWritable,IntWritable>{

	@Override
	protected void reduce(IntWritable key, Iterable<Text> values,Context context)
			throws IOException, InterruptedException {
		int counter=60;
		int agekey=Integer.parseInt(String.valueOf(key));
		int reqage=counter-agekey;
		
		
       int pension_amount=0;
		
		for(Text value:values)
		{
			
		 	int inval=Integer.parseInt(String.valueOf(value).split(":")[0]);
			if(inval==reqage)
			{
				pension_amount+=Integer.parseInt(String.valueOf(value).split(":")[1]);
			} 
			
		}
		context.write(null, new IntWritable(pension_amount));
		
		
		
		

	}

	


}
