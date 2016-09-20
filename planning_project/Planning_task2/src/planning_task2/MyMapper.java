package planning_task2;

import java.io.IOException;



import org.apache.hadoop.io.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<LongWritable,Text,IntWritable,IntWritable>{

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String year=context.getConfiguration().get("year");
		int year1=Integer.parseInt(year);
		
		String records=value.toString();
		String recordparts[]=records.split(",");
		String age=recordparts[0];
		int age1=Integer.parseInt(age);
		
		context.write(new IntWritable(year1),new IntWritable(age1));
		
		
	}

}
