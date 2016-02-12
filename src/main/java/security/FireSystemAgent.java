package security;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

public class FireSystemAgent extends Agent {

    private AID[] serverAgents;
    //private static Map<AID, Float> currentTemperatures = new HashMap<>();
    List<CurrentFireStatusInRoom> currentFireStatuses = new LinkedList<CurrentFireStatusInRoom>();

    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("fire-system-manager");
        sd.setName("JADE-fire-system");
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
            //System.out.println("Found the following seller agents:");
            serverAgents = new AID[result.length];
            for (int i = 0; i < result.length; ++i) {
                serverAgents[i] = result[i].getName();
                //System.out.println(serverAgents[i].getName());
                CurrentFireStatusInRoom currentFireStatusInRoom = new CurrentFireStatusInRoom(serverAgents[i]);
                currentFireStatuses.add(currentFireStatusInRoom);

            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new RequestCurrentFireStatuses(this, 5000));
    }

    private class RequestCurrentFireStatuses extends TickerBehaviour {

        private int nResponders;

        public RequestCurrentFireStatuses(Agent a, long period) {
            super(a, period);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onTick() {

            ACLMessage requestFireStatusMessage = new ACLMessage(ACLMessage.REQUEST);

            for (int i = 0; i < serverAgents.length; ++i) {
                requestFireStatusMessage.addReceiver(serverAgents[i]);
            }

            requestFireStatusMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            // We want to receive a reply in 10 secs
            requestFireStatusMessage.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
            requestFireStatusMessage.setContent("fuoco");

            addBehaviour(new AchieveREInitiator(myAgent, requestFireStatusMessage) {

                protected void handleInform(ACLMessage inform) {
                    //System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
                    String messageContenut = inform.getContent();
                    System.out.println("Agente Gestore-Fuoco::::" + messageContenut);
                    if (messageContenut != null) {
                        try {

                            Boolean fireStatus = Boolean.valueOf(inform.getContent());
                            //System.out.println("Agente Gestore-Fuoco-Bool::::"+fireStatus);

                            Iterator<CurrentFireStatusInRoom> it = currentFireStatuses.iterator();
                            while (it.hasNext()) {

                                CurrentFireStatusInRoom currentFireStatusInRoom = it.next();
                                //System.out.println(currentTemperatureInRoom.getroomAgent().getName() + " " + msg.getSender().getName());
                                if (currentFireStatusInRoom.getRoomAgent().getName().equals(inform.getSender().getName())) {
                                    currentFireStatusInRoom.setCurrentFireStatus(fireStatus);
                                }
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Agente Gestore-Fuoco::::errore");
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

    private class CurrentFireStatusInRoom {

        private Boolean currentFireStatus;
        private AID roomAgent;

        public CurrentFireStatusInRoom() {
            currentFireStatus = null;
            roomAgent = null;
            //fanOn = false;
        }

        public CurrentFireStatusInRoom(AID roomAgent) {
            setCurrentFireStatus(null);
            setRoomAgent(roomAgent);
            //fanOn = false;
        }

        public CurrentFireStatusInRoom(AID roomAgent, Boolean currentFireStatus) {
            setCurrentFireStatus(currentFireStatus);
            setRoomAgent(roomAgent);
            //fanOn = false;
        }

        public Boolean getCurrentFireStatus() {
            return currentFireStatus;
        }

        public void setCurrentFireStatus(Boolean currentFireStatus) {
            this.currentFireStatus = currentFireStatus;
        }

        public AID getRoomAgent() {
            return roomAgent;
        }

        public void setRoomAgent(AID roomAgent) {
            this.roomAgent = roomAgent;
        }

    }

    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("FireSystemAgent " + getAID().getName() + " terminating.");
    }
}
