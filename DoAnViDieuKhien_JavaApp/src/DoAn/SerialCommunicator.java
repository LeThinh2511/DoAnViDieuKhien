package DoAn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;

import javax.swing.JOptionPane;


public class SerialCommunicator implements SerialPortEventListener {
	
	
	SerialPort serialPort;
	int numOfP1 = 0;
	int numOfP2 = 0;
	Boolean isConnected = false;
	Boolean shouldCheckBoard = true;
	
    // Khai bao cac cong thuong su dung
	private static final String PORT_NAMES[] = {
		"/dev/cu.usbmodem1411",
		"/dev/cu.usbmodem76",
		"/dev/cu.usbmodem1431",
		"/dev/cu.usbmodem83",
		"/dev/cu.usbmodem84",
		//"/dev/cu.Bluetooth-Incoming-Port",
		"/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyUSB0", // Linux
        "COM35", // Windows
	};
	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;

	public void initialize() {
	    CommPortIdentifier portId = null;
	    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
	
	    //Tim cac cong serial dua vao ten da khai bao
	    while (portEnum.hasMoreElements()) {
	        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
	        for (String portName : PORT_NAMES) {
	            if (currPortId.getName().equals(portName)) {
	                portId = currPortId;
	                System.out.println(portId.getName());
	                isConnected = true;
	                shouldCheckBoard = false;
	                break;
	            }
	        }
	    }
	    if (portId == null) {
	        System.out.println("Could not find COM port.");
	        return;
	    }

	    try {
	        serialPort = (SerialPort) portId.open(this.getClass().getName(),
	                TIME_OUT);
	        serialPort.setSerialPortParams(DATA_RATE,
	                SerialPort.DATABITS_8,
	                SerialPort.STOPBITS_1,
	                SerialPort.PARITY_NONE);
	
	        // Cho phep doc ghi vao cong
	        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
	        output = serialPort.getOutputStream();
	
	        serialPort.addEventListener(this);
	        serialPort.notifyOnDataAvailable(true);
	    } catch (Exception e) {
	        System.err.println(e.toString());
	    }
	}


	public synchronized void close() {
	    if (serialPort != null) {
	        serialPort.removeEventListener();
	        serialPort.close();
	    }
	}

	// ham doc du lieu tu serial port
	public synchronized void serialEvent(SerialPortEvent oEvent) {
	    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
	        try {
	            String inputLine = null;
	            if (input.ready()) {
	                inputLine = input.readLine();
	                int sensor = 0;
	                try
	                {
	                	sensor = Integer.valueOf(inputLine);
		                if (sensor == 1)
		                {
		                	this.numOfP1++;
		                	//System.out.println("tang san pham 1");
		                }
		                else if (sensor == 2)
		                {
		                	this.numOfP2++;
		                	//System.out.println("tang san pham 2");
		                }
	                }
	                catch (Exception e1)
	                {
	                	System.out.println("loi doc du lieu");
	                }
	            }
	
	        } catch (Exception e) {
	            System.err.println(e.toString());
	        }
	    }
	    // Ignore all the other eventTypes, but you should consider the other ones.
	}
	
	
	// Ham xuat gia mot gia tri kieu int vao cong
	public synchronized int writeMessage(int message)
	{
		try {
			output.write(message);
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return 0;
			//e.printStackTrace();
		}
	}

}