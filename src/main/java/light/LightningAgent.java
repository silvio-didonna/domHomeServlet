package light;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class LightningAgent extends Agent {

    /**
     *
     */
    private static final long serialVersionUID = 697611633768237195L;
    private AID[] serverAgents;
    //private static Map<AID, Float> currentLumens = new HashMap<>();
    //List<CurrentLumenInRoom> currentLumens = new LinkedList<CurrentLumenInRoom>();
    Map <String,CurrentStatusInRoom> currentStatuses = new HashMap<String,CurrentStatusInRoom>();

    protected void setup() {

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("lightning-manager");
        sd.setName("JADE-lightning");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        //ricerca agenti
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sdRoom = new ServiceDescription();
        sdRoom.setType("room-manager");
        template.addServices(sdRoom);
        try {
            DFAgentDescription[] result = DFService.search(this, template);
            //System.out.println("Found the following server agents:");
            serverAgents = new AID[result.length];
            for (int i = 0; i < result.length; ++i) {
                serverAgents[i] = result[i].getName();
                //System.out.println(serverAgents[i].getName());
                //CurrentLumenInRoom currentLumenInRoom = new CurrentLumenInRoom(serverAgents[i]);
                CurrentStatusInRoom currentStatusInRoom = new CurrentStatusInRoom(serverAgents[i]);
                currentStatuses.put(serverAgents[i].getLocalName(), currentStatusInRoom);

            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new RequestCurrentLumen(this, 5000));
        addBehaviour(new SetLight(this, 6000));
        addBehaviour(new SetShutter(this, 6000));
    }

    private class RequestCurrentLumen extends TickerBehaviour {

        /**
         *
         */
        private static final long serialVersionUID = -4219050278752369718L;
        private int nResponders;

        public RequestCurrentLumen(Agent a, long period) {
            super(a, period);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onTick() {

            ACLMessage requestLumenMessage = new ACLMessage(ACLMessage.REQUEST);

            for (int i = 0; i < serverAgents.length; ++i) {
                requestLumenMessage.addReceiver(serverAgents[i]);
            }

            requestLumenMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            // We want to receive a reply in 10 secs
            requestLumenMessage.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
            requestLumenMessage.setContent("lumen");

            addBehaviour(new AchieveREInitiator(myAgent, requestLumenMessage) {
                /**
                 *
                 */
                private static final long serialVersionUID = 4875568271828534008L;

                protected void handleInform(ACLMessage inform) {
                    //System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
                    String messageContenut = inform.getContent();
                    //System.out.println("AgenteGestore-Luce::::" + messageContenut);
                    if (messageContenut != null) {
                        try {
                            int lumen = Integer.parseInt(messageContenut);
							//System.out.println("AgenteGestore-Temperaturafloat::::"+temp);
                            //Float.parseFloat(messageContenut);

                            //Iterator<CurrentLumenInRoom> it = currentLumens.iterator();
                            //while (it.hasNext()) {

                                //CurrentLumenInRoom currentLumenInRoom = it.next();
                                //System.out.println(currentTemperatureInRoom.getroomAgent().getName() + " " + msg.getSender().getName());
                                //if (currentLumenInRoom.getroomAgent().getName().equals(inform.getSender().getName())) {
                   //CurrentStatusInRoom currentStatusInRoom = currentStatuses.get(inform.getSender().getName());      
                   //currentStatusInRoom.setCurrentLumen(lumen);
                   //currentStatuses.put(inform.getSender().getName(),currentStatusInRoom);
                            currentStatuses.get(inform.getSender().getLocalName()).setCurrentLumen(lumen);
                            //System.out.println(currentTemperatureInRoom.getCurrentTemperature());
                                //}
                            //}
                            System.out.println("AgenteGestore-Luce-HASH::::" + currentStatuses.get(inform.getSender().getLocalName()).getCurrentLumen());

                        } catch (NumberFormatException e) {
                            System.out.println("AgenteGestore-Luce::::errore");
                        }
                    }
                }

                protected void handleRefuse(ACLMessage refuse) {
                    System.out.println("Agent " + refuse.getSender().getName() + " refused to perform the requested action");
                    nResponders--;
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
                    if (notifications.size() < nResponders) {
                        // Some responder didn't reply within the specified timeout
                        System.out.println("Timeout expired: missing " + (nResponders - notifications.size()) + " responses");
                    }
                }
            });

        }
    }
    /*
    private class SetLightRoom extends TickerBehaviour {


		private static final long serialVersionUID = -6964271329093498028L;
		
		private int nResponders;

        public SetLightRoom(Agent a, long period) {
            super(a, period);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onTick() {

            ACLMessage requestLumenMessage = new ACLMessage(ACLMessage.REQUEST);

            for (int i = 0; i < serverAgents.length; ++i) {
                requestLumenMessage.addReceiver(serverAgents[i]);
            }

            requestLumenMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            // We want to receive a reply in 10 secs
            requestLumenMessage.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
            requestLumenMessage.setContent("light");

            addBehaviour(new AchieveREInitiator(myAgent, requestLumenMessage) {


				private static final long serialVersionUID = 857005523035750328L;

				protected void handleInform(ACLMessage inform) {
                    //System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
                    String messageContenut = inform.getContent();
                    if (messageContenut != null) {
                        try {
                            int lumen = Integer.parseInt(messageContenut);
							//System.out.println("AgenteGestore-Temperaturafloat::::"+temp);
                            //Float.parseFloat(messageContenut);

                            Iterator<CurrentLumenInRoom> it = currentLumens.iterator();
                            while (it.hasNext()) {

                                CurrentLumenInRoom currentLumenInRoom = it.next();
                                //System.out.println(currentTemperatureInRoom.getroomAgent().getName() + " " + msg.getSender().getName());
                                if (currentLumenInRoom.getroomAgent().getName().equals(inform.getSender().getName())) {
                                    currentLumenInRoom.setCurrentLumen(lumen);
                                    //System.out.println(currentTemperatureInRoom.getCurrentTemperature());
                                }
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("AgenteGestore-Luce::::errore");
                        }
                    }
                }

                protected void handleRefuse(ACLMessage refuse) {
                    System.out.println("Agent " + refuse.getSender().getName() + " refused to perform the requested action");
                    nResponders--;
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
                    if (notifications.size() < nResponders) {
                        // Some responder didn't reply within the specified timeout
                        System.out.println("Timeout expired: missing " + (nResponders - notifications.size()) + " responses");
                    }
                }
            });

        }
    }
    
    */

    private class SetLight extends TickerBehaviour {

        /**
         *
         */
        private static final long serialVersionUID = -8454124241057674169L;
        private int nResponders;

        public SetLight(Agent a, long period) {
            super(a, period);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onTick() {

            int lumenMinValue = 200;
            Set<String> rooms = currentStatuses.keySet();
            Iterator <String> roomIterator = rooms.iterator();
            //for (CurrentLumenInRoom currentLumenInRoom : currentLumens) { // per ogni stanza
            while(roomIterator.hasNext()) {
            	String roomName = roomIterator.next();
                //ricerca agenti luce
                //String roomName = currentLumenInRoom.getroomAgent().getLocalName(); // nome agente stanza
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sdRoom = new ServiceDescription();
                sdRoom.setName(roomName + "-light"); // ad es: salone-light
                template.addServices(sdRoom);
                AID[] lightAgents=null; // da modificare----------------------null
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    //System.out.println("Found the following light agents:");
                    lightAgents = new AID[result.length];
                    for (int i = 0; i < result.length; ++i) {
                        lightAgents[i] = result[i].getName();
                        //System.out.println(lightAgents[i].getName());

                    }
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }

                //AID msgReceiver = new AID("Luce", AID.ISLOCALNAME);
                ACLMessage requestLightToggle = new ACLMessage(ACLMessage.REQUEST);
                //requestLightToggle.addReceiver(msgReceiver);
                requestLightToggle.addReceiver(lightAgents[0]); // da modificare----------------------
                //System.out.println("setLight:::: " + currentLumenInRoom.getCurrentLumen());
                requestLightToggle.setContent(""); // per far funzionare l'IF dopo
                if (!currentStatuses.get(roomName).getlightOn()) {
                    if ((currentStatuses.get(roomName).getCurrentLumen() < lumenMinValue)) {

                        requestLightToggle.setContent("true");

                    }
                } else if ((currentStatuses.get(roomName).getCurrentLumen() > lumenMinValue)) {

                    requestLightToggle.setContent("false");
                }

                if (requestLightToggle.getContent().equalsIgnoreCase("true") || requestLightToggle.getContent().equalsIgnoreCase("false")) {

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
                            System.out.println("Agent " + inform.getSender().getName() + " send" + inform.getContent());
                            currentStatuses.get(roomName).setlightOn(Boolean.valueOf(inform.getContent())); // CONTROLLARE!!!!!!!!!!!!
                        }

                        protected void handleAgree(ACLMessage agree) {
                            System.out.println("Agent " + agree.getSender().getName() + " agreed");
                        }

                        protected void handleRefuse(ACLMessage refuse) {
                            System.out.println("Agent " + refuse.getSender().getName() + " refused to perform the requested action");
                            nResponders--;
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
                            if (notifications.size() < nResponders) {
                                // Some responder didn't reply within the specified timeout
                                System.out.println("Timeout expired: missing " + (nResponders - notifications.size()) + " responses");
                            }
                        }
                    });
                }
            }
        }
    }
    
    private class SetShutter extends TickerBehaviour {

        private int nResponders;

        public SetShutter(Agent a, long period) {
            super(a, period);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onTick() {

            int lumenMinValue = 200;
            
            Set<String> rooms = currentStatuses.keySet();
            Iterator <String> roomIterator = rooms.iterator();
            //for (CurrentLumenInRoom currentLumenInRoom : currentLumens) { // per ogni stanza
            while(roomIterator.hasNext()) {
            	String roomName = roomIterator.next();
                //ricerca agenti luce
                //String roomName = currentLumenInRoom.getroomAgent().getLocalName(); // nome agente stanza
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sdRoom = new ServiceDescription();
                sdRoom.setName(roomName + "-shutter"); // ad es: salone-light
                template.addServices(sdRoom);
                AID[] shutterAgents=null; // da modificare----------------------null
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    //System.out.println("Found the following light agents:");
                    shutterAgents = new AID[result.length];
                    for (int i = 0; i < result.length; ++i) {
                        shutterAgents[i] = result[i].getName();
                        //System.out.println(shutterAgents[i].getName());

                    }
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }

                //AID msgReceiver = new AID("Luce", AID.ISLOCALNAME);
                ACLMessage requestShutterToggle = new ACLMessage(ACLMessage.REQUEST);
                //requestLightToggle.addReceiver(msgReceiver);
                requestShutterToggle.addReceiver(shutterAgents[0]); // da modificare----------------------
                //System.out.println("setShutter:::: " + currentLumenInRoom.getCurrentLumen());
                requestShutterToggle.setContent(""); // per far funzionare l'IF dopo
                if (!currentStatuses.get(roomName).getShutterOpen()) {
                    if ((currentStatuses.get(roomName).getCurrentLumen() < lumenMinValue)) { // DA MODIFICARE!!!!

                        requestShutterToggle.setContent("true");

                    }
                } else if ((currentStatuses.get(roomName).getCurrentLumen() > lumenMinValue)) { // DA MODIFICARE!!!!

                    requestShutterToggle.setContent("false");
                }

                if (requestShutterToggle.getContent().equalsIgnoreCase("true") || requestShutterToggle.getContent().equalsIgnoreCase("false")) {

                    requestShutterToggle.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                    // We want to receive a reply in 10 secs
                    requestShutterToggle.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
                    //requestLightToggle.setContent("dummy-action");

                    addBehaviour(new AchieveREInitiator(myAgent, requestShutterToggle) {


                        protected void handleInform(ACLMessage inform) {
                            System.out.println("Agent " + inform.getSender().getName() + " send" + inform.getContent());
                            currentStatuses.get(roomName).setShutterOpen(Boolean.valueOf(inform.getContent()));
                        }

                        protected void handleAgree(ACLMessage agree) {
                            System.out.println("Agent " + agree.getSender().getName() + " agreed");
                        }

                        protected void handleRefuse(ACLMessage refuse) {
                            System.out.println("Agent " + refuse.getSender().getName() + " refused to perform the requested action");
                            nResponders--;
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
                            if (notifications.size() < nResponders) {
                                // Some responder didn't reply within the specified timeout
                                System.out.println("Timeout expired: missing " + (nResponders - notifications.size()) + " responses");
                            }
                        }
                    });
                }
            }
        }
    }

    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("LightningAgent " + getAID().getName() + " terminating.");
    }

    private class CurrentStatusInRoom {

        private int currentLumen;
        private AID roomAgent;
        private Boolean lightOn;
        private Boolean shutterOpen;

        public CurrentStatusInRoom() {
            currentLumen = 0;
            roomAgent = null;
            lightOn = false;
            shutterOpen = false;
        }

        public CurrentStatusInRoom(AID roomAgent) {
            setCurrentLumen(0);
            setroomAgent(roomAgent);
            lightOn = false;
            shutterOpen = false;
        }

        public CurrentStatusInRoom(AID roomAgent, int currentLumen) {
            setCurrentLumen(currentLumen);
            setroomAgent(roomAgent);
            lightOn = false;
            shutterOpen = false;
        }

        public int getCurrentLumen() {
            return currentLumen;
        }

        public void setCurrentLumen(int currentLumen) {
            this.currentLumen = currentLumen;
        }

        public AID getroomAgent() {
            return roomAgent;
        }

        public void setroomAgent(AID roomAgent) {
            this.roomAgent = roomAgent;
        }

        public Boolean getlightOn() {
            return lightOn;
        }

        public void setlightOn(Boolean lightOn) {
            this.lightOn = lightOn;
        }
        
        public Boolean getShutterOpen() {
            return shutterOpen;
        }

        public void setShutterOpen(Boolean shutterOpen) {
            this.shutterOpen = shutterOpen;
        }
    }
}
