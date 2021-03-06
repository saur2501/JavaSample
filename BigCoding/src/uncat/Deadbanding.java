package uncat;

import java.util.Random;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Deadbanding {

	public static void main(String[] args) {
		// Get a DescriptiveStatistics instance
		DescriptiveStatistics stats = new DescriptiveStatistics();
		double mean, std, median, grainsOfInterest;
		int granularity = 25;
		int k = 0;
		stats.setWindowSize(300);

		int[] inputArray = new int[10000];
		int[] outputArray = new int[10000];
		Random random = new Random();
		inputArray[0] = 5000 + random.nextInt(1000);
		outputArray[k++] = inputArray[0];
		stats.addValue(inputArray[0]);
		for(int i = 1; i < 10000; i++) {
			inputArray[i] = 5000 + random.nextInt(1000);
	        stats.addValue(inputArray[i]);
	        mean = stats.getMean();
			std = stats.getStandardDeviation();
			grainsOfInterest = 3 * std / granularity;
			System.out.print("\t" + mean + "\t" + std + "\t" + grainsOfInterest + "\t" + inputArray[i] + "\t" + outputArray[k-1]);				//to ensure window- use .getSum() and .getPercentile for median
			if(Math.abs(inputArray[i] - outputArray[k-1]) >= grainsOfInterest) {		//outputArray[k] preferred over inputArray[i-1]
				outputArray[k++] = inputArray[i];
				System.out.println("\ttrue");
			} else System.out.println("\tfalse");
			//median = stats.getPercentile(50);
		}
		System.out.println(k);
	}

}
//jar- commons-math3-3.0.jar