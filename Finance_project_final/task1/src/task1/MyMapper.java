package task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;



import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<LongWritable ,Text,IntWritable,CustomValue> {

	public TreeMap<SalaryRange,DoubleWritable> TaxGroups=new TreeMap<SalaryRange,DoubleWritable>();

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		Path[] files = DistributedCache.getLocalCacheFiles(context.getConfiguration());	

		for (Path SinglePath : files) {
			if (SinglePath.getName().equals("part-m-00000")) 
			{
		
		BufferedReader br = new BufferedReader(new FileReader(SinglePath.toString()));
	 
		String line = null;
		while ((line= br.readLine()) != null) {
			 
			String[]parts = line.split(",");
			double min_income = Double.parseDouble(parts[0]);
			double max_income = Double.parseDouble(parts[1]);
			String taxper = parts[2];
			SalaryRange salary_range = new SalaryRange(min_income,max_income);
			TaxGroups.put(salary_range, new DoubleWritable(Double.parseDouble(taxper)));
			}
			br.close();	
			}
		}
	}

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String recordsparts[]=value.toString().split(",");
		String gender=recordsparts[3];
		String taxfiler=recordsparts[4];
		double income=Double.parseDouble(recordsparts[5]);
		Set<SalaryRange> taxvalues = TaxGroups.keySet();
		Iterator<SalaryRange> taxitr = taxvalues.iterator();
		double taxper = 0;
		while(taxitr.hasNext()){
			SalaryRange salrange = (SalaryRange)taxitr.next();
			if(salrange.isIncomeInRange(income))
				 taxper = TaxGroups.get(salrange).get();
				
			}
		
		CustomValue cv = new CustomValue(new DoubleWritable(income),new DoubleWritable(taxper),new Text(gender),new Text(taxfiler));
		
		context.write(new IntWritable(1),cv)	;
	}
	
	
	
	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		 
		
	 
	
	
	}

}
