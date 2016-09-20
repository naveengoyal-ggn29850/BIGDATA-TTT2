package planning_task2;

import java.io.IOException;



import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<IntWritable,IntWritable,Text,IntWritable>{

	@Override
	protected void reduce(IntWritable key, Iterable<IntWritable> values,Context context)
			throws IOException, InterruptedException {
		int counter=60;
		int agekey=Integer.parseInt(String.valueOf(key));
		int reqage=counter-agekey;
		
		
       int count=0;
		
		for(IntWritable value:values)
		{
			int inval=Integer.parseInt(String.valueOf(value));
			if(inval==reqage)
			{
				count++;
			}
			
		}
		
		
		context.write(new Text("Expected Sr.Citizen : "), new IntWritable(count));
		
		

	}

	


}
