package temperature;
import java.util.Date;
import java.util.Vector;

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
import jade.proto.AchieveREInitiator;
import jade.proto.AchieveREResponder;


public class FanAgent extends Agent {
	Boolean fanStatus=true; // per non far spegnere il ventilatore dopo il primo ciclo
	Boolean waitFanStatus=false;
	//AID fromAgent;

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
		addBehaviour(new toggleFanFIPA());
		//addBehaviour(new checkFanStatus());
		//addBehaviour(new fanService());

	}

	private class toggleFanFIPA extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7229800159797756192L;

		public void action() {

			MessageTemplate template = MessageTemplate.and(
					MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST) );

			addBehaviour(new AchieveREResponder(myAgent, template) {


				protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {

					ACLMessage agree = request.createReply();
					agree.setPerformative(ACLMessage.AGREE);

					//fromAgent = new AID(request.getSender().getLocalName(),AID.ISLOCALNAME);
					/*
					AID msgReceiver= new AID("Gestore-Seriale",AID.ISLOCALNAME);
					ACLMessage serialAnswer = new ACLMessage(ACLMessage.REQUEST);
					serialAnswer.addReceiver(msgReceiver);
					serialAnswer.setContent("fan1\n");
					myAgent.send(serialAnswer);

					addBehaviour(new checkFanStatus());
					 */
					ChangeFanStatus changeFanStatus = new ChangeFanStatus();
					addBehaviour(changeFanStatus);
					waitFanStatus=true;

					return agree;

				}

				protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {

					ACLMessage inform = request.createReply();
					inform.setPerformative(ACLMessage.INFORM);
					inform.setContent(fanStatus.toString());
					return inform;

				}
			} );
		}
	}

	private class ChangeFanStatus extends OneShotBehaviour {

		@Override
		public void action() {

			ACLMessage requestLumenMessage = new ACLMessage(ACLMessage.REQUEST);

			requestLumenMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			// We want to receive a reply in 10 secs
			requestLumenMessage.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			requestLumenMessage.setContent("fan1\n");
			requestLumenMessage.addReceiver(new AID("Gestore-Seriale",AID.ISLOCALNAME));

			addBehaviour(new AchieveREInitiator(myAgent, requestLumenMessage) {

				/**
				 * 
				 */
				private static final long serialVersionUID = -188445726707114874L;
				protected void handleInform(ACLMessage inform) {
					String messageContenut=inform.getContent();
					if (messageContenut!=null) {
						messageContenut=messageContenut.trim();
						//System.out.println("AgenteVentilatore::::"+messageContenut);
						if (messageContenut!=null) {
							fanStatus = Boolean.valueOf(messageContenut);
							waitFanStatus=false;
							System.out.println("AgenteVentilatore::::"+fanStatus);
						}
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

	private class checkFanStatus extends OneShotBehaviour {

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
					fanStatus = Boolean.valueOf(messageContenut);
				System.out.println("AgenteVentilatore::::"+fanStatus);
			}
			//else
			//block();

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
