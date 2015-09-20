package light;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

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

	private class LightSensorService extends OneShotBehaviour {


		/**
		 * 
		 */
		private static final long serialVersionUID = 6894891571072136375L;

		@Override
		public void action() {

			MessageTemplate template = MessageTemplate.and(
					MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST) );

			addBehaviour(new AchieveREResponder(myAgent, template) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 5601819153323381998L;

				protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
					if (request.getContent().equalsIgnoreCase("lumen") && currentLumen>=0) {
						// We agree to perform the action.
						ACLMessage agree = request.createReply();
						agree.setPerformative(ACLMessage.AGREE);
						return agree;
					}
					else {
						// We refuse to perform the action
						throw new RefuseException("Message content not supported or corrupted value");
					}
				}

				protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
					ACLMessage inform = request.createReply();
					inform.setPerformative(ACLMessage.INFORM);

					inform.setContent(Integer.toString(currentLumen));

					return inform;

				}
			} );

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
		System.out.println("LightSensorAgent "+getAID().getName()+" terminating.");
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

			AID msgReceiver= new AID("Gestore-Seriale",AID.ISLOCALNAME);

			ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
			serialAnswer.setReplyWith(lumenReplyWith);
			serialAnswer.addReceiver(msgReceiver);
			serialAnswer.setContent("lm1\n");

			myAgent.send(serialAnswer);

		}
	}

	private class GetCurrentLumen extends CyclicBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 774837946376158617L;

		@Override
		public void action() {
			MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			MessageTemplate mt2 = MessageTemplate.MatchInReplyTo(lumenReplyWith);
			MessageTemplate mt = MessageTemplate.and(mt1, mt2);
			//System.out.println("Server behaviour 1 wait a message.");
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				//System.out.println("AgenteLightSensor::::"+messageContenut);
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

