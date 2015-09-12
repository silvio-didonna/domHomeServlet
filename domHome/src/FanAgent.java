import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class FanAgent extends Agent {
	Boolean fanStatus=false;

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
		addBehaviour(new checkFanStatus());
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
				
				AID msgReceiver= new AID("Gestore-Seriale",AID.ISLOCALNAME);
				ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
				serialAnswer.addReceiver(msgReceiver);
				serialAnswer.setContent("fan1\n");
				myAgent.send(serialAnswer);
				
				//checkFanStatus
				
				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.AGREE);
				reply.setContent(fanStatus.toString());
				myAgent.send(reply);

			}
			else {
				block();
			}

		}
	}

	private class checkFanStatus extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2794051229003161225L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				messageContenut=messageContenut.trim();
				System.out.println("AgenteVentilatore::::"+messageContenut);
				if (messageContenut!=null)
					fanStatus = Boolean.valueOf(messageContenut);
				System.out.println("AgenteVentilatore::::"+fanStatus);

			}
			else {
				block();
			}

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
