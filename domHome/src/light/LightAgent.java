package light;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
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


public class LightAgent extends Agent {
	Boolean lightStatus=false;
	AID fromAgent;

	/**
	 * 
	 */
	private static final long serialVersionUID = 6998352636636720971L;

	protected void setup() {


		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("light-manager");
		sd.setName("JADE-light");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch(FIPAException fe) {
			fe.printStackTrace();
		}
		//addBehaviour(new toggleFan());
		//addBehaviour(new checkFanStatus());
		//addBehaviour(new fanService());

		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST) );

		addBehaviour(new AchieveREResponder(this, template) {
			protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
				//System.out.println("Agent "+getLocalName()+": REQUEST received from "+request.getSender().getName()+". Action is "+request.getContent());

				//System.out.println("Agent "+getLocalName()+": Agree");
				ACLMessage agree = request.createReply();
				agree.setPerformative(ACLMessage.AGREE);


				// We refuse to perform the action
				//System.out.println("Agent "+getLocalName()+": Refuse");
				//throw new RefuseException("check-failed");

				fromAgent = new AID(request.getSender().getLocalName(),AID.ISLOCALNAME);
				AID msgReceiver= new AID("Gestore-Seriale",AID.ISLOCALNAME);
				ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
				serialAnswer.addReceiver(msgReceiver);
				serialAnswer.setContent("light1\n");
				myAgent.send(serialAnswer);

				addBehaviour(new checkLightStatus());


				return agree;

			}

			protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {

				//System.out.println("Agent "+getLocalName()+": Action successfully performed");
				ACLMessage inform = request.createReply();
				inform.setPerformative(ACLMessage.INFORM);
				inform.setContent(lightStatus.toString());
				return inform;


				//System.out.println("Agent "+getLocalName()+": Action failed");
				//throw new FailureException("unexpected-error");

			}
		} );
	}



	private class toggleLight extends CyclicBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 9072626078728707911L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.not(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST)),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			//System.out.println("Server behaviour 1 wait a message.");
			ACLMessage msg = myAgent.receive(mt);

			if (msg!=null) {
				fromAgent = new AID(msg.getSender().getLocalName(),AID.ISLOCALNAME);
				AID msgReceiver= new AID("Gestore-Seriale",AID.ISLOCALNAME);
				ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
				serialAnswer.addReceiver(msgReceiver);
				serialAnswer.setContent("light1\n");
				myAgent.send(serialAnswer);

				addBehaviour(new checkLightStatus());
				addBehaviour(new replyWithStatus());


			}
			else
				block();

		}
	}

	private class checkLightStatus extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2794051229003161225L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage msgFromSerial = myAgent.blockingReceive(mt); // ATTENZIONE, è una BLOCKING
			if (msgFromSerial!=null) {

				String messageContenut=msgFromSerial.getContent();
				messageContenut=messageContenut.trim();
				//System.out.println("AgenteVentilatore::::"+messageContenut);
				if (messageContenut!=null)
					lightStatus = Boolean.valueOf(messageContenut);
				System.out.println("AgenteLuce::::"+lightStatus);
			}
			//else
			//block();

		}

	}


	private class replyWithStatus extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7851399022936675519L;

		@Override
		public void action() {

			//ACLMessage reply = msg.createReply();
			ACLMessage reply = new ACLMessage(ACLMessage.AGREE);
			reply.addReceiver(fromAgent);
			//reply.setPerformative(ACLMessage.AGREE);
			reply.setContent(lightStatus.toString());
			myAgent.send(reply);
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
