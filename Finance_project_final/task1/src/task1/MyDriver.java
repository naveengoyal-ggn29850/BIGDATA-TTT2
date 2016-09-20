package task1;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
 
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyDriver {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws InterruptedException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
		Configuration conf = new Configuration();
		Job job = new Job(conf,"Census  Job");
		job.setJarByClass(MyDriver.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		job.setNumReduceTasks(1);
		DistributedCache.addCacheFile(new URI("/user/cloudera/taxper_mapping_data/part-m-00000"),job.getConfiguration());
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(CustomValue.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true)?0:1);

	}

}
