package gateway.agent;
/*****************************************************************

This agent receives the blackboard object  
and its content will be sent to the proper agent

*****************************************************************/

import java.util.Date;
import java.util.Vector;

import gateway.bean.BlackBoardBean;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;
import jade.wrapper.gateway.*;

public class MyGateWayAgent extends GatewayAgent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1390471131466493985L;
	BlackBoardBean board = null;
	String messageContent = null;
	String messageReceiver = "Termometro";
		
	protected void processCommand(java.lang.Object obj) {
			
		if (obj instanceof BlackBoardBean)	{
		
			board = (BlackBoardBean)obj;
			messageContent = board.getMessage();
			/*
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID( messageReceiver, AID.ISLOCALNAME) );
			msg.setContent(messageContent);			    
			send(msg);
			*/
			
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID( messageReceiver, AID.ISLOCALNAME) );
			msg.setContent(messageContent);			    


			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                // We want to receive a reply in 10 secs
			msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
                //requestLightToggle.setContent("dummy-action");
//			send(msg);

                addBehaviour(new AchieveREInitiator(this, msg) {


                    protected void handleInform(ACLMessage inform) {
                        System.out.println("Agent " + inform.getSender().getName() + " send " + inform.getContent());
                        board.setMessage(inform.getContent());
                        releaseCommand(board);	
                    }

                    protected void handleAgree(ACLMessage agree) {
                        System.out.println("Agent " + agree.getSender().getName() + " agreed");
                    }

                    protected void handleRefuse(ACLMessage refuse) {
                        System.out.println("Agent " + refuse.getSender().getName() + " refused to perform the requested action");
                        //nResponders--;
                    }

                    protected void handleFailure(ACLMessage failure) {
                        if (failure.getSender().equals(myAgent.getAMS())) {
							// FAILURE notification from the JADE runtime: the receiver
                            // does not exist
                            System.out.println("Responder does not exist");
                        } else {
                            System.out.println("Agent " + failure.getSender().getName() + " failed to perform the requested action");
                        }
                    }

                    protected void handleAllResultNotifications(Vector notifications) {
                        //if (notifications.size() < nResponders) {
                            // Some responder didn't reply within the specified timeout
                        //    System.out.println("Timeout expired: missing  responses");
                        //}
                    }
                    
                });	
                	
			
		}
		
	}
/*
	public void setup()
	{
		
		// Waiting for the answer
		addBehaviour(new CyclicBehaviour(this) 
		{
			private static final long serialVersionUID = 1599048356876043814L;

			public void action() {
				MessageTemplate template = MessageTemplate.and(
						MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
						MessageTemplate.MatchPerformative(ACLMessage.INFORM) );
				
				ACLMessage msg = receive(template);
				
				//if ((msg!=null)&&(messageContent!=null))	{
				if ((msg!=null)&&(messageContent!=null))	{
					messageContent = msg.getContent();
					System.out.println(messageContent);
					releaseCommand(messageContentBackup);				
				} else block();
			 }
		});	
		
		super.setup();
	}
*/
		
}
