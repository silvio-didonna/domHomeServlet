package gateway.agent;
/*****************************************************************

This agent receives the blackboard object  
and its content will be sent to the proper agent

*****************************************************************/

import gateway.bean.BlackBoardBean;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import jade.wrapper.gateway.*;

public class MyGateWayAgent extends GatewayAgent {
	
	BlackBoardBean board = null;
		
	protected void processCommand(java.lang.Object obj) {
			
		if (obj instanceof BlackBoardBean)	{
		
			board = (BlackBoardBean)obj;
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID( board.getReceiver(), AID.ISLOCALNAME) );
			msg.setContent(board.getMessage());			    
			send(msg);
		}
		
	}

	public void setup()
	{
		// Waiting for the answer
		addBehaviour(new CyclicBehaviour(this) 
		{
			 public void action() {

				ACLMessage msg = receive();
				
				if ((msg!=null)&&(board!=null))	{				
					board.setMessage(msg.getContent());
					releaseCommand(board);				
				} else block();
			 }
		});	
		
		super.setup();
	}
		
}
