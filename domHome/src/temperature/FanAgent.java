package temperature;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class FanAgent extends Agent {
	Boolean fanStatus=true; // per non far spegnere il ventilatore dopo il primo ciclo
	AID fromAgent;

	/**
	 * 
	 */
	private static final long serialVersionUID = 6998352636636720971L;

	protected void setup() {


		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("fan-manager");
		sd.setName("JADE-fan");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch(FIPAException fe) {
			fe.printStackTrace();
		}
		addBehaviour(new toggleFan());
		//addBehaviour(new checkFanStatus());
		//addBehaviour(new fanService());

	}

	private class toggleFan extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 9072626078728707911L;

		@Override
		public void action() {

			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			//System.out.println("Server behaviour 1 wait a message.");
			ACLMessage msg = myAgent.receive(mt);

			if (msg!=null) {
				fromAgent = new AID(msg.getSender().getLocalName(),AID.ISLOCALNAME);
				AID msgReceiver= new AID("Gestore-Seriale",AID.ISLOCALNAME);
				ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
				serialAnswer.addReceiver(msgReceiver);
				serialAnswer.setContent("fan1\n");
				myAgent.send(serialAnswer);

				addBehaviour(new checkFanStatus());
				addBehaviour(new replyWithStatus());


			}
			else
				block();

		}
	}

	private class checkFanStatus extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2794051229003161225L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage msgFromSerial = myAgent.blockingReceive(mt); // ATTENZIONE, è una BLOCKING
			if (msgFromSerial!=null) {

				String messageContenut=msgFromSerial.getContent();
				messageContenut=messageContenut.trim();
				//System.out.println("AgenteVentilatore::::"+messageContenut);
				if (messageContenut!=null)
					fanStatus = Boolean.valueOf(messageContenut);
				System.out.println("AgenteVentilatore::::"+fanStatus);
			}
			//else
			//block();

		}

	}


	private class replyWithStatus extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7851399022936675519L;

		@Override
		public void action() {

			//ACLMessage reply = msg.createReply();
			ACLMessage reply = new ACLMessage(ACLMessage.AGREE);
			reply.addReceiver(fromAgent);
			//reply.setPerformative(ACLMessage.AGREE);
			reply.setContent(fanStatus.toString());
			myAgent.send(reply);
		}

	}

	private class fanService extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7077823714595289649L;

		@Override
		public void action() {
			/*
		SequentialBehaviour sq =new SequentialBehaviour() { 
			  public int onEnd() { 
				    reset(); 
				    myAgent.addBehaviour(this); 
				    return super.onEnd(); 
				  } 
				}; 
		sq.addSubBehaviour(new toggleFan());
		sq.addSubBehaviour(new checkFanStatus());
		sq.addSubBehaviour(new replyWithStatus());
		addBehaviour(sq);
			 */



			//addBehaviour(fsm);

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
		System.out.println("FanAgent "+getAID().getName()+" terminating.");
	}
}
