package social_task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<LongWritable,Text,IntWritable,Text>{

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String year=context.getConfiguration().get("year");
		int year1=Integer.parseInt(year);
		
		String records=value.toString();
		String recordparts[]=records.split(",");
		String age=recordparts[0];
		//int age1=Integer.parseInt(age);
		
		String income=recordparts[5];
		String invalue=age+","+income;
		
		Set<Salary_Range> rangekeys = CustDetails.keySet();
		Iterator rangeitr = rangekeys.iterator();
		int pension=0;
		while(rangeitr.hasNext()){
			Salary_Range sr = (Salary_Range)rangeitr.next();
			pension = sr.salary_in_range((int)Double.parseDouble(income)) ? sr.getPension():0;
			
		}
		
		context.write(new IntWritable(year1),new Text(age + ":" + pension));
	}

	private Map<Salary_Range, Integer> CustDetails = new HashMap<Salary_Range, Integer>();	
	@Override
	protected void setup(org.apache.hadoop.mapreduce.Mapper.Context context)
			throws IOException, InterruptedException {
		Path[] files = DistributedCache.getLocalCacheFiles(context.getConfiguration());		
		for (Path SinglePath : files) {
			if (SinglePath.getName().equals("part-m-00000")) 
			{
				BufferedReader reader = new BufferedReader(new FileReader(SinglePath.toString()));
				String line = reader.readLine();
				while(line != null) 
				{
					String[] lineParts = line.split(",");
					String pid=lineParts[0];
					int min_income = Integer.parseInt(lineParts[1]);
					int max_income=Integer.parseInt(lineParts[2]);
					int pension=Integer.parseInt(lineParts[3]);
					Salary_Range salary_range = new Salary_Range(min_income,max_income,pension);
//					String minmaxpension = lineParts[1]+","+lineParts[2]+","+lineParts[3];
					CustDetails.put(salary_range, pension);
					line = reader.readLine();
				}
				reader.close();
			}
		}
		if (CustDetails.isEmpty()) 
		{
			throw new IOException("Unable To Load Customer Data.");
		}
	}

}
