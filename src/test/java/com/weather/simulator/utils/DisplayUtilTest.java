package com.weather.simulator.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.weather.simulator.dao.LatLongBean;


/**
 * Test Cases for testing Date Conversion
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class DisplayUtilTest {

	@Test
	public void printTest() throws Exception {
		/**
		 * Test Case - display the content in formated input.
		 */
		ArrayList<LatLongBean> latLongBeanList = new ArrayList<LatLongBean>();
		
		LatLongBean bean1 = new LatLongBean();
		bean1.setCountry("AU");
		bean1.setName("Westmead");
		latLongBeanList.add(bean1);
		
		LatLongBean bean2 = new LatLongBean();
		bean2.setCountry("AU");
		bean2.setName("Rooty Hill");
		latLongBeanList.add(bean2);
		
		LatLongBean bean3 = new LatLongBean();
		bean3.setCountry("testCountry1");
		bean3.setName("test Suburb1");
		latLongBeanList.add(bean3);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream console = System.out;
		System.setOut(ps);

		DisplayUtil.print(latLongBeanList);	
		
		System.out.flush();
		System.setOut(console);
		
		File outFile = new File("src/test/resources/displayUtil/printLatLongBeanOutput.txt");
		outFile.delete();
		String outputFileStr = outFile.getAbsolutePath();
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(outputFileStr, false));
			writer.write(baos.toString());
			writer.flush();
			writer.close();
		} finally {
			if(writer != null)
				writer.close();
		}

		File expectedOutputFile = new File("src/test/resources/displayUtil/printLatLongBeanActuals.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));

		
		
	}
	

}
