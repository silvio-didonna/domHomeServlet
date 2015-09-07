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
	SerialComm arduino;

	/**
	 * 
	 */
	private static final long serialVersionUID = 138736042772986486L;

	protected void setup() {
		Object[] argList=this.getArguments(); //ottiene la porta seriale
		arduino = (SerialComm) argList[0];

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
		addBehaviour(new SendSerialServiceBehaviour());
		addBehaviour(new ReceiveSerialServiceBehaviour());
	}

	private class SendSerialServiceBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			//System.out.println("Server behaviour 1 wait a message.");
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
			}



		}

	}
	
	private class ReceiveSerialServiceBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			//MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			//System.out.println("Server behaviour 1 wait a message.");
			// Send the cfp to all sellers
			AID msgReceiver= new AID("Termometro",AID.ISLOCALNAME);
			
			ACLMessage serialAnswer = new ACLMessage(ACLMessage.INFORM);
			serialAnswer.addReceiver(msgReceiver);
			serialAnswer.setContent("answer");
			//cfp.setConversationId("mex1");
			//cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
			myAgent.send(serialAnswer);
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
		System.out.println("SerialCommAgent "+getAID().getName()+" terminating.");
	}
}
