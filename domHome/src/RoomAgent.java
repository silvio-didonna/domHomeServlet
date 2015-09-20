import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import internet.ThingSpeak;
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
import jade.proto.AchieveREInitiator;
import jade.proto.AchieveREResponder;



public class RoomAgent extends Agent {
	String temperatureReplyWith = "temperature";
	String lumenReplyWith = "lumen";
	private float temperature;
	private int lumens;
	private boolean door;
	private boolean motion;
	private boolean flame;

	private boolean tempOrLumen=true;

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
		//addBehaviour(new AskCurrentTemperature(this,5000));
		//addBehaviour(new GetCurrentTemperature());
		addBehaviour(new GetCurrentTemperatureFIPA(this,5000));

		//addBehaviour(new AskCurrentLumen(this, 5000));
		//addBehaviour(new GetCurrentLumen());
		addBehaviour(new GetCurrentLumenFIPA(this, 5000));

		addBehaviour(new RoomService());


		addBehaviour(new sendToThingSpeak(this,16000));
	}

	public class RoomService extends OneShotBehaviour{

		/**
		 * 
		 */
		private static final long serialVersionUID = 3522316552296891322L;

		public void action() {
			MessageTemplate template = MessageTemplate.and(
					MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST) );

			addBehaviour(new AchieveREResponder(myAgent, template) {


				/**
				 * 
				 */
				private static final long serialVersionUID = 288442867080805012L;

				protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
					//System.out.println("Agent "+getLocalName()+": REQUEST received from "+request.getSender().getName()+". Action is "+request.getContent());
					if (request.getContent().equalsIgnoreCase("temperatura") || request.getContent().equalsIgnoreCase("lumen")) {
						// We agree to perform the action.
						ACLMessage agree = request.createReply();
						agree.setPerformative(ACLMessage.AGREE);
						return agree;
					}
					else {
						// We refuse to perform the action
						throw new RefuseException("Message content not supported");
					}
				}

				protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
					ACLMessage inform = request.createReply();
					inform.setPerformative(ACLMessage.INFORM);

					switch(request.getContent()) {
					case("temperatura"): 
						inform.setContent(String.valueOf(temperature));
					break;
					case("lumen"):
						inform.setContent(String.valueOf(lumens));
					break;
					}

					return inform;

				}
			} );
		}
	}

	private class GetCurrentTemperatureFIPA extends TickerBehaviour {

		public GetCurrentTemperatureFIPA(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = -7843279741024063018L;

		@Override
		protected void onTick() {
			ACLMessage requestTemperatureMessage = new ACLMessage(ACLMessage.REQUEST);

			requestTemperatureMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			// We want to receive a reply in 10 secs
			requestTemperatureMessage.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			requestTemperatureMessage.setContent("temperatura");
			requestTemperatureMessage.addReceiver(new AID("Termometro",AID.ISLOCALNAME));

			addBehaviour(new AchieveREInitiator(myAgent, requestTemperatureMessage) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 4875568271828534008L;
				protected void handleInform(ACLMessage inform) {
					String messageContenut=inform.getContent();
					if (messageContenut!=null) {
							temperature=new Float(messageContenut);
							System.out.println("Room-Temp::::"+messageContenut);
					}
				}
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Agent "+refuse.getSender().getName()+" refused to perform the requested action");
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
					//if (notifications.size() < nResponders) {
					// Some responder didn't reply within the specified timeout
					//System.out.println("Timeout expired: missing "+(nResponders - notifications.size())+" responses");
					//}
				}
			} );


		}

	}

	private class GetCurrentLumenFIPA extends TickerBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5306499643103460629L;


		public GetCurrentLumenFIPA(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}


		@Override
		protected void onTick() {
			ACLMessage requestTemperatureMessage = new ACLMessage(ACLMessage.REQUEST);

			requestTemperatureMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			// We want to receive a reply in 10 secs
			requestTemperatureMessage.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			requestTemperatureMessage.setContent("lumen");
			requestTemperatureMessage.addReceiver(new AID("Sensore-Luci",AID.ISLOCALNAME));

			addBehaviour(new AchieveREInitiator(myAgent, requestTemperatureMessage) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 4875568271828534008L;
				protected void handleInform(ACLMessage inform) {
					String messageContenut=inform.getContent();
					//System.out.println("AgenteGestore-Salone::::"+messageContenut);
					if (messageContenut!=null) {
							lumens = Integer.parseInt(messageContenut);
							System.out.println("Room-Lumen::::"+messageContenut);
					}
				}
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Agent "+refuse.getSender().getName()+" refused to perform the requested action");
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
					//if (notifications.size() < nResponders) {
					// Some responder didn't reply within the specified timeout
					//System.out.println("Timeout expired: missing "+(nResponders - notifications.size())+" responses");
					//}
				}
			} );


		}

	}

	private class AskCurrentTemperature extends TickerBehaviour {

		public AskCurrentTemperature(Agent a, long period) {
			super(a, period);
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

	private class sendToThingSpeak extends TickerBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8713839390230869708L;

		public sendToThingSpeak(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onTick() {
			if (tempOrLumen) {
				//invia temperatura

				try {
					ThingSpeak.getHTML("https://api.thingspeak.com/update?api_key=OW7HWDZ4UTP04RT6&field1="+String.valueOf(temperature));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				

			} else {
				//invia lumen

				try {
					ThingSpeak.getHTML("https://api.thingspeak.com/update?api_key=OW7HWDZ4UTP04RT6&field2="+String.valueOf(lumens));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			tempOrLumen=!tempOrLumen;

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
