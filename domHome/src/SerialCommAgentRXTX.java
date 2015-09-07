import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Enumeration;

public class SerialCommAgentRXTX extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 138736042772986486L;
	//SerialComm arduino;
	//SerialPort serialPort;
	SerialClass obj;
	String port;
	int baud;

	public static BufferedReader input;
	public static OutputStream output;

	public static synchronized void writeData(String data) {
		//System.out.println("Sent: " + data);
		try {
			output.write(data.getBytes());
		} catch (Exception e) {
			System.out.println("could not write to port");
		}
	}



	protected void setup() {
		port="COM7";
		baud=9600;

		//Object[] argList=this.getArguments(); //ottiene la porta seriale
		//arduino = (SerialComm) argList[0];

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("serialcomm-manager");
		sd.setName("JADE-serialcomm");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch(FIPAException fe) {
			fe.printStackTrace();
		}

		obj = new SerialClass();
		obj.initialize();
		input = SerialClass.input;
		output = SerialClass.output;

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addBehaviour(new SendSerialServiceBehaviour());
		//addBehaviour(new ReceiveSerialServiceBehaviour());
	}

	private class SendSerialServiceBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			//System.out.println("SendSerialServiceBehaviour wait a message.");
			ACLMessage msg = myAgent.receive();
			//System.out.println(msg.getContent());
			if (msg!=null) {
				//System.out.println(msg.getContent());
				String msgSender = msg.getSender().getLocalName();
				String msgContent = msg.getContent();
				if (!msgContent.isEmpty()) {
					System.out.println(msgSender + '#' + msgContent + '\n');
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					writeData(msgSender + '#' + msgContent + '\n');
					//writeData("Termometro#therm1\n");
					String msgArd=null;
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					try {
						msgArd=input.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println(msgArd);

					

					AID msgReceiver= new AID("Termometro",AID.ISLOCALNAME);

					ACLMessage serialAnswer = new ACLMessage(ACLMessage.INFORM);
					serialAnswer.addReceiver(msgReceiver);
					serialAnswer.setContent(msgArd);

					myAgent.send(serialAnswer);

				}
			}
			else {
				block();
			}



		}

	}

	private class ReceiveSerialServiceBehaviour extends CyclicBehaviour {

		@Override
		public void action() {

			//MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			//System.out.println("Server behaviour 1 wait a message.");
			// Send the cfp to all sellers



			/*
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {
				String msgSender = msg.getSender().getLocalName();
				String msgContent = msg.getContent();
				if (!msgContent.isEmpty()) {
					try {
						arduino.send(msgSender + "#" + msgContent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else {
				block();
			 */



		}

	}

	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		//serialPort.closePort();
		obj.close();
		System.out.println("SerialCommAgent "+getAID().getName()+" terminating.");
	}
}
