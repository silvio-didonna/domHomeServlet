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

public class LightSensorAgent extends Agent {
	private int currentLumen;
	String lumenReplyWith = "lumen";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7925627535445537735L;

	protected void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("light-sensor-manager");
		sd.setName("JADE-light-sensor");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch(FIPAException fe) {
			fe.printStackTrace();
		}
		addBehaviour(new RequestCurrentLumen(this, 3000));
		addBehaviour(new GetCurrentLumen());
		addBehaviour(new LightSensorService());
	}

	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		System.out.println("LightSensorAgent "+getAID().getName()+" terminating.");
	}

	private class LightSensorService extends CyclicBehaviour {

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				if(currentLumen>=0) {
					String messageContenut = msg.getContent();
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent(Integer.toString(currentLumen));
					myAgent.send(reply);
				}

			}
			else {
				block();
			}

		}

	}

	private class RequestCurrentLumen extends TickerBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public RequestCurrentLumen(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick() {

			String currLumen=null;

			AID msgReceiver= new AID("Gestore-Seriale",AID.ISLOCALNAME);

			ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
			serialAnswer.setReplyWith(lumenReplyWith);
			serialAnswer.addReceiver(msgReceiver);
			serialAnswer.setContent("lm1\n");

			myAgent.send(serialAnswer);

		}
	}

	private class GetCurrentLumen extends CyclicBehaviour {

		@Override
		public void action() {
			MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			MessageTemplate mt2 = MessageTemplate.MatchInReplyTo(lumenReplyWith);
			MessageTemplate mt = MessageTemplate.and(mt1, mt2);
			//System.out.println("Server behaviour 1 wait a message.");
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				System.out.println("AgenteLightSensor::::"+messageContenut);
				if (messageContenut!=null) {
					messageContenut=messageContenut.trim();
					try {
						currentLumen = Integer.parseInt(messageContenut);
					}catch (NumberFormatException e) {
						System.out.println("AgenteLightSensor::::errore");
					}
				}
			}
			else {
				block();
			}

		}

	}
}

