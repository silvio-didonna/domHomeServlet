package temperature;
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

public class TemperatureAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 138736042772986486L;
	private AID[] serverAgents;
	//private static Map<AID, Float> currentTemperatures = new HashMap<>();
	List<CurrentTemperatureInRoom> currentTemperatures = new LinkedList<CurrentTemperatureInRoom>();


	protected void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("temperature-manager");
		sd.setName("JADE-temperature");
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
			System.out.println("Found the following seller agents:");
			serverAgents = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				serverAgents[i] = result[i].getName();
				System.out.println(serverAgents[i].getName());
				CurrentTemperatureInRoom currentTemperatureInRoom = new CurrentTemperatureInRoom(serverAgents[i]);
				currentTemperatures.add(currentTemperatureInRoom);

			}
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new RequestCurrentTemperature(this,5000));
		addBehaviour(new SetFan(this,6000));
		addBehaviour(new SetWindow(this,6000));

	}
	
	private class RequestCurrentTemperature extends TickerBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4219050278752369718L;
		private int nResponders;

		public RequestCurrentTemperature(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}


		@Override
		protected void onTick() {

			ACLMessage requestTemperatureMessage = new ACLMessage(ACLMessage.REQUEST);

			for (int i = 0; i < serverAgents.length; ++i) {
				requestTemperatureMessage.addReceiver(serverAgents[i]);
			}
			

			requestTemperatureMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			// We want to receive a reply in 10 secs
			requestTemperatureMessage.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			requestTemperatureMessage.setContent("temperatura");
			
			addBehaviour(new AchieveREInitiator(myAgent, requestTemperatureMessage) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 4875568271828534008L;
				protected void handleInform(ACLMessage inform) {
					//System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
					String messageContenut=inform.getContent();
					System.out.println("AgenteGestore-Temperatura::::"+messageContenut);
					if (messageContenut!=null) {
						try {
							Float temp=new Float(messageContenut);
							//System.out.println("AgenteGestore-Temperaturafloat::::"+temp);
							//Float.parseFloat(messageContenut);

							Iterator <CurrentTemperatureInRoom> it = currentTemperatures.iterator();
							while(it.hasNext()) {

								CurrentTemperatureInRoom currentTemperatureInRoom = it.next();
								//System.out.println(currentTemperatureInRoom.getroomAgent().getName() + " " + msg.getSender().getName());
								if (currentTemperatureInRoom.getroomAgent().getName().equals(inform.getSender().getName())) {
									currentTemperatureInRoom.setCurrentTemperature(temp);
									//System.out.println(currentTemperatureInRoom.getCurrentTemperature());
								}
							}

						} catch (NumberFormatException e) {
							System.out.println("AgenteGestore-Temperatura::::errore");
						}
					}
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
	
	private class SetFan extends TickerBehaviour {


		/**
		 * 
		 */
		private static final long serialVersionUID = -8454124241057674169L;
		private int nResponders;

		public SetFan(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onTick() {


			Float tempMaxValue = new Float(30);
			for(CurrentTemperatureInRoom currentTemperatureInRoom:currentTemperatures) { // per ogni stanza
				AID msgReceiver= new AID("Ventilatore",AID.ISLOCALNAME);
				ACLMessage requestLightToggle = new ACLMessage(ACLMessage.REQUEST);
				requestLightToggle.addReceiver(msgReceiver);
				System.out.println("setFan:::: " +currentTemperatureInRoom.getCurrentTemperature());
				requestLightToggle.setContent(""); // per far funzionare l'IF dopo
				if (!currentTemperatureInRoom.getfanOn()) {
					if ((currentTemperatureInRoom.getCurrentTemperature() != null) && ((currentTemperatureInRoom.getCurrentTemperature().compareTo(tempMaxValue) > 0))) {


						requestLightToggle.setContent("true"); //accendi

					}
				}
				else if((currentTemperatureInRoom.getCurrentTemperature() != null) && ((currentTemperatureInRoom.getCurrentTemperature().compareTo(tempMaxValue) < 0))) {

					requestLightToggle.setContent("false"); //spegni
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
							currentTemperatureInRoom.setfanOn(Boolean.valueOf(inform.getContent()));
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
	
	
	
	private class SetWindow extends TickerBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2793118778911471269L;
		private int nResponders;

		public SetWindow(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onTick() {


			Float tempMaxValue = new Float(30);
			for(CurrentTemperatureInRoom currentTemperatureInRoom:currentTemperatures) { // per ogni stanza
				AID msgReceiver= new AID("Finestra",AID.ISLOCALNAME);
				ACLMessage requestLightToggle = new ACLMessage(ACLMessage.REQUEST);
				requestLightToggle.addReceiver(msgReceiver);
				System.out.println("setWindow:::: " +currentTemperatureInRoom.getCurrentTemperature());
				requestLightToggle.setContent(""); // per far funzionare l'IF dopo
				if (!currentTemperatureInRoom.getfanOn()) {
					if ((currentTemperatureInRoom.getCurrentTemperature() != null) && ((currentTemperatureInRoom.getCurrentTemperature().compareTo(tempMaxValue) > 0))) {


						requestLightToggle.setContent("true"); //accendi

					}
				}
				else if((currentTemperatureInRoom.getCurrentTemperature() != null) && ((currentTemperatureInRoom.getCurrentTemperature().compareTo(tempMaxValue) < 0))) {

					requestLightToggle.setContent("false"); //spegni
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
							currentTemperatureInRoom.setfanOn(Boolean.valueOf(inform.getContent()));
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



	private class CurrentTemperatureInRoom {
		private Float currentTemperature;
		private AID roomAgent;
		private Boolean fanOn;

		public CurrentTemperatureInRoom() {
			currentTemperature=null;
			roomAgent=null;
			fanOn=false;
		}

		public CurrentTemperatureInRoom(AID roomAgent) {
			setCurrentTemperature(null);
			setroomAgent(roomAgent);
			fanOn=false;
		}

		public CurrentTemperatureInRoom(AID roomAgent,Float currentTemperature) {
			setCurrentTemperature(currentTemperature);
			setroomAgent(roomAgent);
			fanOn=false;
		}

		public Float getCurrentTemperature() {
			return currentTemperature;
		}

		public void setCurrentTemperature(Float currentTemperature) {
			this.currentTemperature=currentTemperature;
		}

		public AID getroomAgent() {
			return roomAgent;
		}

		public void setroomAgent(AID roomAgent) {
			this.roomAgent=roomAgent;
		}

		public Boolean getfanOn() {
			return fanOn;
		}

		public void setfanOn(Boolean fanOn) {
			this.fanOn=fanOn;
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
		System.out.println("TemperatureAgent "+getAID().getName()+" terminating.");
	}
}
