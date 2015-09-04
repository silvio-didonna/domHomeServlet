//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;



public class mainClass {
	static String thermometerName = "Termometro";
	static String fanName = "Ventilatore";
	static String temperatureName = "Gestore-Temperatura";
	static String roomName = "Gestore-Salone";

	public static void main (String args[]) throws Exception {

		//---------------------********************************************-----------------------------------
		// http://jade.tilab.com/pipermail/jade-develop/2008q3/012874.html

		// Get a hold on JADE runtime
		Runtime rt = Runtime.instance();

		// Exit the JVM when there are no more containers around
		rt.setCloseVM(true);
		//System.out.print("runtime created\n");

		// Create a default profile
		Profile profile = new ProfileImpl(null, 1200, null);
		//System.out.print("profile created\n");

		//System.out.println("Launching a whole in-process platform..."+profile);
		jade.wrapper.AgentContainer mainContainer = rt.createMainContainer(profile);

		// now set the default Profile to start a container
		ProfileImpl pContainer = new ProfileImpl(null, 1200, null);
		//System.out.println("Launching the agent container ..."+pContainer);

		jade.wrapper.AgentContainer cont = rt.createAgentContainer(pContainer);
		//System.out.println("Launching the agent container after ..."+pContainer);

		//System.out.println("containers created");
		//System.out.println("Launching the rma agent on the main container ...");
		AgentController rma = mainContainer.createNewAgent("rma","jade.tools.rma.rma", new Object[0]);
		rma.start();
		//---------------------********************************************-----------------------------------


		// Agent creation on local container
		SerialComm arduino = new SerialComm();
		//List<Object> argList = new ArrayList<Object>();
		//argList.add(arduino);
		Object[] argList = new Object[1];
		argList[0]=arduino;
		AgentController fanAgent = cont.createNewAgent (fanName, "FanAgent", null);

		fanAgent.start();

		AgentController thermometerAgent = cont.createNewAgent (thermometerName, "ThermometerAgent", argList);

		thermometerAgent.start();
		


		/*
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 */
		
		AgentController roomAgent = cont.createNewAgent (roomName, "RoomAgent", null);

		roomAgent.start();
		
		AgentController temperatureAgent = cont.createNewAgent (temperatureName, "TemperatureAgent", null);

		temperatureAgent.start();


	}
}






