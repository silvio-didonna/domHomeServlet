//import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;


public class SerialComm {
	String port;
	int baud;

	SerialPort serialPort;

	SerialComm() throws Exception {
		port="COM7";
		baud=9600;

		//Scanner input = new Scanner (System.in);
		//this.port=port;
		//this.baud=baud;

		/*
		SerialPort serialPortArray[] = SerialPort.getCommPorts();
		for(int i=0;i<serialPortArray.length;i++)
			System.out.println(i + ": " + serialPortArray[i].getDescriptivePortName());
		System.out.println("Inserire il nome della porta seriale: ");

		if (input.hasNextLine()) {
			port = input.nextLine();
		}

		System.out.println("inserire il baudrate (d per default): ");
		if (input.hasNextInt()) {
			baud = input.nextInt();
		}


		input.close();
		 */

		serialPort = SerialPort.getCommPort(port);
		serialPort.setComPortParameters(this.baud, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

		System.out.println("Porta: " + serialPort.getDescriptivePortName() + " baud: " + baud);

		// Apre porta seriale
		serialPort.openPort();

		// Questa istruzione è necessaria perchè Arduino si riavvia dopo aver aperto la seriale
		Thread.sleep(2000);
	}

	public void finalize() {
		serialPort.closePort();
	}

	public  void send(String str) throws Exception {

		// Invia comandi alla seriale
		serialPort.getOutputStream().write((str).getBytes());

	}

	public String receive() throws Exception {
		byte[] readBuffer=null;
		try {
			while (serialPort.bytesAvailable() == 0)
				Thread.sleep(20);

			readBuffer = new byte[serialPort.bytesAvailable()];
			int numRead = serialPort.readBytes(readBuffer, readBuffer.length);
			System.out.println("Read " + numRead + " bytes.");

		} catch (Exception e) { e.printStackTrace(); }
		
		String str = new String(readBuffer); // conversione in String (provare con UTF-8)
		System.out.println(str);
		return str;

	}
}