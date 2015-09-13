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

public class ThermometerAgent extends Agent {
	private Float currentTemperature;
	String temperatureReplyWith = "temperature";

	/**
	 * 
	 */
	private static final long serialVersionUID = -8355663085154105053L;

	protected void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("thermometer-manager");
		sd.setName("JADE-thermometer");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch(FIPAException fe) {
			fe.printStackTrace();
		}
		addBehaviour(new RequestCurrentTemperature(this, 3000));
		addBehaviour(new GetCurrentTemperature());
		addBehaviour(new TempService());
	}

	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		System.out.println("ThermometerAgent "+getAID().getName()+" terminating.");
	}

	private class TempService extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6607101087483781538L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			//System.out.println("Server behaviour 1 wait a message.");
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				if(currentTemperature!=null) {
					String messageContenut=msg.getContent();
					//System.out.println("AgenteTermometro::::"+messageContenut);
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent(currentTemperature.toString());
					myAgent.send(reply);
				}

			}
			else {
				block();
			}

		}

	}

	private class RequestCurrentTemperature extends TickerBehaviour {

		public RequestCurrentTemperature(Agent a, long period) {
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
			serialAnswer.setReplyWith(temperatureReplyWith);
			serialAnswer.addReceiver(msgReceiver);
			serialAnswer.setContent("therm1\n");

			myAgent.send(serialAnswer);

		}
	}

	private class GetCurrentTemperature extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2794051229003161225L;

		@Override
		public void action() {
			MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			MessageTemplate mt2 = MessageTemplate.MatchInReplyTo(temperatureReplyWith);
			MessageTemplate mt = MessageTemplate.and(mt1, mt2);
			//System.out.println("Server behaviour 1 wait a message.");
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				//System.out.println("AgenteTermometro::::"+messageContenut);
				if (messageContenut!=null)
					try {
						currentTemperature = Float.parseFloat(messageContenut);
					} catch (NumberFormatException e) {
						System.out.println("AgenteTermometro::::errore");
					}

			}
			else {
				block();
			}

		}

	}
}

