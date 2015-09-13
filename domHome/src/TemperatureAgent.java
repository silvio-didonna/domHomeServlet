import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		addBehaviour(new getCurrentTemperature());
		addBehaviour(new setFan(this,6000));
	}

	private class RequestCurrentTemperature extends TickerBehaviour {

		public RequestCurrentTemperature(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = -7544895415079804828L;

		@Override
		protected void onTick() {

			ACLMessage requestTemperatureMessage = new ACLMessage(ACLMessage.REQUEST);

			for (int i = 0; i < serverAgents.length; ++i) {
				requestTemperatureMessage.addReceiver(serverAgents[i]);
			}
			requestTemperatureMessage.setContent("temperatura");
			myAgent.send(requestTemperatureMessage);
		}
	}

	private class getCurrentTemperature extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2518394469526299190L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage msg = myAgent.receive(mt);
			if (msg!=null) {

				String messageContenut=msg.getContent();
				System.out.println("AgenteGestore-Temperatura::::"+messageContenut);
				if (messageContenut!=null)
					try {
						Float temp=new Float(messageContenut);
						//System.out.println("AgenteGestore-Temperaturafloat::::"+temp);
						//Float.parseFloat(messageContenut);

						Iterator <CurrentTemperatureInRoom> it = currentTemperatures.iterator();
						while(it.hasNext()) {

							CurrentTemperatureInRoom currentTemperatureInRoom = it.next();
							//System.out.println(currentTemperatureInRoom.getroomAgent().getName() + " " + msg.getSender().getName());
							if (currentTemperatureInRoom.getroomAgent().getName().equals(msg.getSender().getName())) {
								currentTemperatureInRoom.setCurrentTemperature(temp);
								//System.out.println(currentTemperatureInRoom.getCurrentTemperature());
							}
						}

					} catch (NumberFormatException e) {
						System.out.println("AgenteGestore-Temperatura::::errore");
					}

			}
			else {
				block();
			}

		}

	}

	private class setFan extends TickerBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8612995381883772852L;

		public setFan(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick() {
			Float tempMaxValue = new Float(30);
			for(CurrentTemperatureInRoom currentTemperatureInRoom:currentTemperatures) {
				System.out.println("setFan:::: " +currentTemperatureInRoom.getCurrentTemperature());
				if (!currentTemperatureInRoom.getfanOn()) {
					if ((currentTemperatureInRoom.getCurrentTemperature() != null) && ((currentTemperatureInRoom.getCurrentTemperature().compareTo(tempMaxValue) > 0))) {

						ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
						AID msgReceiver= new AID("Ventilatore",AID.ISLOCALNAME);
						serialAnswer.addReceiver(msgReceiver);
						//serialAnswer.setContent("fan1\n");
						myAgent.send(serialAnswer);

						MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
						ACLMessage msg = myAgent.receive(mt);
						if (msg!=null) {

							String messageContenut=msg.getContent();
							if (messageContenut!=null)
								currentTemperatureInRoom.setfanOn(Boolean.valueOf(messageContenut));
							//System.out.println("AgenteGestore-Temperatura (on)::::"+messageContenut);
							System.out.println("AgenteGestore-Temperatura (on)::::"+currentTemperatureInRoom.getfanOn());
							//if (messageContenut!=null)
							//fanStatus = Boolean.getBoolean(messageContenut);

						}
						else {
							block();
						}

					}
				}
				else if((currentTemperatureInRoom.getCurrentTemperature() != null) && ((currentTemperatureInRoom.getCurrentTemperature().compareTo(tempMaxValue) < 0))) {
					ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
					AID msgReceiver= new AID("Ventilatore",AID.ISLOCALNAME);
					serialAnswer.addReceiver(msgReceiver);
					//serialAnswer.setContent("fan1\n");
					myAgent.send(serialAnswer);

					MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
					ACLMessage msg = myAgent.receive(mt);
					if (msg!=null) {

						String messageContenut=msg.getContent();
						if (messageContenut!=null)
							currentTemperatureInRoom.setfanOn(Boolean.valueOf(messageContenut));
						//System.out.println("AgenteGestore-Temperatura (off)::::"+messageContenut);
						System.out.println("AgenteGestore-Temperatura (off)::::"+currentTemperatureInRoom.getfanOn());
						//if (messageContenut!=null)
						//fanStatus = Boolean.getBoolean(messageContenut);

					}
					else {
						block();
					}
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
