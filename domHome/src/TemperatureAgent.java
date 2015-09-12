import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class TemperatureAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 138736042772986486L;
	private AID[] serverAgents;

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
		addBehaviour(new RequestCurrentTemperature(this,20000));
		//addBehaviour(new Behaviour1bis());
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
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("room-manager");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template); 
			System.out.println("Found the following seller agents:");
			serverAgents = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				serverAgents[i] = result[i].getName();
				System.out.println(serverAgents[i].getName());
			}
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
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
		System.out.println("TemperatureAgent "+getAID().getName()+" terminating.");
	}
}
