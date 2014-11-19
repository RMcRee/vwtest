package com.shop.vwtest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.io.IOException;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class Test {
    public static final String BRIDGE_LIBRARY_NAME = "libvw_simple_jna.so";
    public static final String VW_LIBRARY_NAME = "libvw.so";
    private static Test vwc =null;
    /* private constructor */
    private Test() { }

    public static Test getInstance(int index) throws IOException {

        try {
            // Set JNA library path. This path should contain libvw.so and libvw_simple_jna.so
            System.setProperty("jna.library.path", ".");            
            NativeLibrary.getInstance(VW_LIBRARY_NAME);
            Native.register(BRIDGE_LIBRARY_NAME);
        } catch (Throwable t) {
        	t.printStackTrace();
        	throw new IOException("Failed to initialize VW bridge ", t);
        }       
        return new Test(); // just a marker
    }
    public native void initialize(int index, String command);
    public native double getPrediction(int index, String example);
    public native void closeInstance(int index);

    private static void testBridge() throws IOException {
    	int index = 0;
        Test vwc = Test.getInstance(index);
        vwc.initialize(index, " -t -i predictl2.mod");

	File inp = new File("vwtest.txt"); // new File("ngramtestdata.vw"); // 
	FileInputStream fstream = new FileInputStream(inp);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        int count = 0, ndcount=0;
        while ((strLine = br.readLine()) != null)   {
        	count++;
        	double pred1 = vwc.getPrediction(index, strLine);
        	System.out.println(pred1+" "+strLine);
        	
        }
	vwc.closeInstance(index);
    }

    public static void main(String[] args) throws Exception {
	System.out.println("Starting vw test");
	testBridge();
    }

}
