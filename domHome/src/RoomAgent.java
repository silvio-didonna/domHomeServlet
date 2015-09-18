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



public class RoomAgent extends Agent {
	String temperatureReplyWith = "temperature";
	String lumenReplyWith = "lumen";
	private float temperature;
	private int lumens;
	private boolean door;
	private boolean motion;
	private boolean flame;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7504622688999058316L;

	protected void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("room-manager");
		sd.setName("JADE-room");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch(FIPAException fe) {
			fe.printStackTrace();
		}
		addBehaviour(new AskCurrentTemperature(this,5000));
		addBehaviour(new GetCurrentTemperature());

		addBehaviour(new AskCurrentLumen(this, 5000));
		addBehaviour(new GetCurrentLumen());
		
		addBehaviour(new RoomService());
	}

	private class RoomService extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6847439043781775939L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				String messageReply="";
				
				switch(messageContenut) {
				case("temperatura"): 
					messageReply=String.valueOf(temperature);
				break;
				case("lumen"):
					messageReply=String.valueOf(lumens);
				break;
				}

				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent(messageReply.toString());
				myAgent.send(reply);
			}
			else {
				block();
			}

		}

	}

	private class AskCurrentTemperature extends TickerBehaviour {

		public AskCurrentTemperature(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 9072626078728707911L;

		@Override
		public void onTick() {


			AID msgReceiver= new AID("Termometro",AID.ISLOCALNAME);

			ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
			serialAnswer.setReplyWith(temperatureReplyWith);
			serialAnswer.addReceiver(msgReceiver);
			//serialAnswer.setContent("therm1");
			myAgent.send(serialAnswer);


			//temperature = Float.parseFloat(currTemp);
			//System.out.println(currTemp);
		}
	}

	private class GetCurrentTemperature extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7188335783111581107L;

		@Override
		public void action() {
			MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			MessageTemplate mt2 = MessageTemplate.MatchInReplyTo(temperatureReplyWith);
			MessageTemplate mt = MessageTemplate.and(mt1, mt2);
			//System.out.println("Server behaviour 1 wait a message.");
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				temperature = Float.parseFloat(messageContenut);
				System.out.println("Room-Temp::::"+messageContenut);

			}
			else {
				block();
			}

		}

	}

	private class AskCurrentLumen extends TickerBehaviour {

		public AskCurrentLumen(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = -4558099421600874487L;

		@Override
		public void onTick() {


			AID msgReceiver= new AID("Sensore-Luci",AID.ISLOCALNAME);

			ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
			serialAnswer.setReplyWith(lumenReplyWith);
			serialAnswer.addReceiver(msgReceiver);
			//serialAnswer.setContent("lm1");
			myAgent.send(serialAnswer);


			//temperature = Float.parseFloat(currTemp);
			//System.out.println(currTemp);
		}
	}

	private class GetCurrentLumen extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3165583707741329448L;

		@Override
		public void action() {
			MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			MessageTemplate mt2 = MessageTemplate.MatchInReplyTo(lumenReplyWith);
			MessageTemplate mt = MessageTemplate.and(mt1, mt2);
			//System.out.println("Server behaviour 1 wait a message.");
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				lumens = Integer.parseInt(messageContenut);
				System.out.println("Room-Lumen::::"+messageContenut);

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
		System.out.println("RoomAgent "+getAID().getName()+" terminating.");
	}

}
