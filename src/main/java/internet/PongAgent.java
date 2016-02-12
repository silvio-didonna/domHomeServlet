package internet;
/*****************************************************************

Agent which answers all messages with a "Pong"

*****************************************************************/


import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;

public class PongAgent extends Agent 
{

	protected void setup() 
	{
		
		// pong behaviour
		addBehaviour(new CyclicBehaviour(this) 
		{
			public void action() 
			{
				ACLMessage msg = receive();
				String content= "";
				
				
				if (msg!=null) {
					content=
					"<br/> - " + myAgent.getLocalName() + " received: " + msg.getContent()+
					"<br/> - " + myAgent.getLocalName() + " sent: " + "Pong";
				   
					ACLMessage reply = msg.createReply();
					reply.setPerformative( ACLMessage.INFORM );
					reply.setContent(content);
					send(reply);
					System.out.print(content);
				} 
				else block();
			}
		});
		
	}
		
	 protected void takeDown() 
	   {
		  try { DFService.deregister(this); }
		  catch (Exception e) {}
	   }	
}

