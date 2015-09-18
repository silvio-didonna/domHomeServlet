package light;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;


public class LightningAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 697611633768237195L;
	private AID[] serverAgents;
	//private static Map<AID, Float> currentLumens = new HashMap<>();
	List<CurrentLumenInRoom> currentLumens = new LinkedList<CurrentLumenInRoom>();


	protected void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("lightning-manager");
		sd.setName("JADE-lightning");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch(FIPAException fe) {
			fe.printStackTrace();
		}

		//ricerca agenti
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sdRoom = new ServiceDescription();
		sdRoom.setType("room-manager");
		template.addServices(sdRoom);
		try {
			DFAgentDescription[] result = DFService.search(this, template); 
			System.out.println("Found the following server agents:");
			serverAgents = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				serverAgents[i] = result[i].getName();
				System.out.println(serverAgents[i].getName());
				CurrentLumenInRoom currentLumenInRoom = new CurrentLumenInRoom(serverAgents[i]);
				currentLumens.add(currentLumenInRoom);

			}
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}



		addBehaviour(new RequestCurrentLumen(this,5000));
		addBehaviour(new GetCurrentLumen());
		addBehaviour(new SetLight(this,6000));
	}

	private class RequestCurrentLumen extends TickerBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4219050278752369718L;


		public RequestCurrentLumen(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}


		@Override
		protected void onTick() {

			ACLMessage requestLumenMessage = new ACLMessage(ACLMessage.REQUEST);

			for (int i = 0; i < serverAgents.length; ++i) {
				requestLumenMessage.addReceiver(serverAgents[i]);
			}
			requestLumenMessage.setContent("lumen");
			myAgent.send(requestLumenMessage);
		}
	}

	private class GetCurrentLumen extends CyclicBehaviour {


		/**
		 * 
		 */
		private static final long serialVersionUID = -3184000795409465535L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.not(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST)),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				System.out.println("AgenteGestore-Luce::::"+messageContenut);
				if (messageContenut!=null)
					try {
						int lumen = Integer.parseInt(messageContenut);
						//System.out.println("AgenteGestore-Temperaturafloat::::"+temp);
						//Float.parseFloat(messageContenut);

						Iterator <CurrentLumenInRoom> it = currentLumens.iterator();
						while(it.hasNext()) {

							CurrentLumenInRoom currentLumenInRoom = it.next();
							//System.out.println(currentTemperatureInRoom.getroomAgent().getName() + " " + msg.getSender().getName());
							if (currentLumenInRoom.getroomAgent().getName().equals(msg.getSender().getName())) {
								currentLumenInRoom.setCurrentLumen(lumen);
								//System.out.println(currentTemperatureInRoom.getCurrentTemperature());
							}
						}

					} catch (NumberFormatException e) {
						System.out.println("AgenteGestore-Luce::::errore");
					}

			}
			else {
				block();
			}

		}

	}

	private class SetLight extends TickerBehaviour {


		/**
		 * 
		 */
		private static final long serialVersionUID = -8454124241057674169L;
		private int nResponders;

		public SetLight(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onTick() {



			int lumenMinValue = 200;
			for(CurrentLumenInRoom currentLumenInRoom:currentLumens) { // per ogni stanza
				AID msgReceiver= new AID("Luce",AID.ISLOCALNAME);
				ACLMessage requestLightToggle = new ACLMessage(ACLMessage.REQUEST);
				requestLightToggle.addReceiver(msgReceiver);
				System.out.println("setLight:::: " +currentLumenInRoom.getCurrentLumen());
				requestLightToggle.setContent(""); // per far funzionare l'IF dopo
				if (!currentLumenInRoom.getlightOn()) {
					if ((currentLumenInRoom.getCurrentLumen()<lumenMinValue)) {

						requestLightToggle.setContent("true");

					}
				}
				else if((currentLumenInRoom.getCurrentLumen()>lumenMinValue)) {

					requestLightToggle.setContent("false");
				}

				if(requestLightToggle.getContent().equalsIgnoreCase("true") || requestLightToggle.getContent().equalsIgnoreCase("false")) {

					requestLightToggle.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
					// We want to receive a reply in 10 secs
					requestLightToggle.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
					//requestLightToggle.setContent("dummy-action");

					addBehaviour(new AchieveREInitiator(myAgent, requestLightToggle) {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1457890379172110455L;
						protected void handleInform(ACLMessage inform) {
							System.out.println("Agent "+inform.getSender().getName()+" send"+inform.getContent());
							currentLumenInRoom.setlightOn(Boolean.valueOf(inform.getContent()));
						}

						protected void handleAgree(ACLMessage agree) {
							System.out.println("Agent "+agree.getSender().getName()+" agreed");
						}
						protected void handleRefuse(ACLMessage refuse) {
							System.out.println("Agent "+refuse.getSender().getName()+" refused to perform the requested action");
							nResponders--;
						}
						protected void handleFailure(ACLMessage failure) {
							if (failure.getSender().equals(myAgent.getAMS())) {
								// FAILURE notification from the JADE runtime: the receiver
								// does not exist
								System.out.println("Responder does not exist");
							}
							else {
								System.out.println("Agent "+failure.getSender().getName()+" failed to perform the requested action");
							}
						}
						protected void handleAllResultNotifications(Vector notifications) {
							if (notifications.size() < nResponders) {
								// Some responder didn't reply within the specified timeout
								System.out.println("Timeout expired: missing "+(nResponders - notifications.size())+" responses");
							}
						}
					} );
				}
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
		System.out.println("LightningAgent "+getAID().getName()+" terminating.");
	}

	private class CurrentLumenInRoom {
		private int currentLumen;
		private AID roomAgent;
		private Boolean lightOn;

		public CurrentLumenInRoom() {
			currentLumen=0;
			roomAgent=null;
			lightOn=false;
		}

		public CurrentLumenInRoom(AID roomAgent) {
			setCurrentLumen(0);
			setroomAgent(roomAgent);
			lightOn=false;
		}

		public CurrentLumenInRoom(AID roomAgent,int currentLumen) {
			setCurrentLumen(currentLumen);
			setroomAgent(roomAgent);
			lightOn=false;
		}

		public int getCurrentLumen() {
			return currentLumen;
		}

		public void setCurrentLumen(int currentLumen) {
			this.currentLumen=currentLumen;
		}

		public AID getroomAgent() {
			return roomAgent;
		}

		public void setroomAgent(AID roomAgent) {
			this.roomAgent=roomAgent;
		}

		public Boolean getlightOn() {
			return lightOn;
		}

		public void setlightOn(Boolean lightOn) {
			this.lightOn=lightOn;
		}
	}
}
