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
	boolean fanStatus;

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
		addBehaviour(new RequestFanAction(this,5000));
		addBehaviour(new checkFanStatus());
	}
	
	private class RequestFanAction extends TickerBehaviour {

		public RequestFanAction(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 9072626078728707911L;

		@Override
		public void onTick() {

			AID msgReceiver= new AID("Gestore-Seriale",AID.ISLOCALNAME);

			ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
			serialAnswer.addReceiver(msgReceiver);
			serialAnswer.setContent("fan1\n");

			myAgent.send(serialAnswer);

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
			//MessageTemplate mt2 = MessageTemplate.MatchInReplyTo(temperatureReplyWith);
			//MessageTemplate mt = MessageTemplate.and(mt1, mt2);
			//System.out.println("Server behaviour 1 wait a message.");
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				System.out.println("AgenteVentilatore::::"+messageContenut);
				if (messageContenut!=null)
					//try {
						fanStatus = Boolean.getBoolean(messageContenut);
					//} catch (NumberFormatException e) {
					//	System.out.println("AgenteTermometro::::errore");
					//}

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
