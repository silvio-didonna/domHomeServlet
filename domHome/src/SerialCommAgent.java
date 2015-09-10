import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.fazecast.jSerialComm.SerialPort;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SerialCommAgent extends Agent {
	//SerialComm arduino;
	SerialPort serialPort;
	String port;
	int baud;

	/**
	 * 
	 */
	private static final long serialVersionUID = 138736042772986486L;

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

		serialPort = SerialPort.getCommPort(port);
		serialPort.setComPortParameters(this.baud, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

		System.out.println("Porta: " + serialPort.getDescriptivePortName() + " baud: " + baud);

		// Apre porta seriale
		serialPort.openPort();

		// Questa istruzione è necessaria perchè Arduino si riavvia dopo aver aperto la seriale
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
					try {
						//serialPort.getOutputStream().write((msgSender + '#' + msgContent).getBytes());
						serialPort.getOutputStream().write((msgContent).getBytes());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					String msgArd;

					byte[] readBuffer=null;
					try {
						while (serialPort.bytesAvailable() == 0)
							Thread.sleep(20);
							//int dim = serialPort.getInputStream().read();
							//while (serialPort.bytesAvailable() != dim)
								//Thread.sleep(20);
							readBuffer = new byte[serialPort.bytesAvailable()];
							serialPort.getInputStream().read(readBuffer);
							//System.out.println("Read " + numRead + " bytes.");
							msgArd = new String(readBuffer); // conversione in String (provare con UTF-8)
							//System.out.println("Messaggio: " + msgArd);

							//AID msgReceiver= new AID("Termometro",AID.ISLOCALNAME);
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.INFORM);

							//ACLMessage serialAnswer = new ACLMessage(ACLMessage.INFORM);
							//serialAnswer.addReceiver(msgReceiver);
							reply.setContent(msgArd);

							myAgent.send(reply);
						
					} catch (Exception e) { e.printStackTrace(); }
					
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
		serialPort.closePort();
		System.out.println("SerialCommAgent "+getAID().getName()+" terminating.");
	}
}
