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

public class AntitheftAgent extends Agent {

    private AID[] serverAgents;
    //private static Map<AID, Float> currentTemperatures = new HashMap<>();
    List<CurrentStatusInRoom> currentStatuses = new LinkedList<CurrentStatusInRoom>();

    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("antitheft-manager");
        sd.setName("JADE-antitheft");
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
                CurrentStatusInRoom currentStatusInRoom = new CurrentStatusInRoom(serverAgents[i]);
                currentStatuses.add(currentStatusInRoom);

            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new RequestCurrentMotionStatuses(this, 5000));
    }

    private class RequestCurrentMotionStatuses extends TickerBehaviour {

        private int nResponders;

        public RequestCurrentMotionStatuses(Agent a, long period) {
            super(a, period);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onTick() {

            ACLMessage requestMotionStatusMessage = new ACLMessage(ACLMessage.REQUEST);

            for (int i = 0; i < serverAgents.length; ++i) {
                requestMotionStatusMessage.addReceiver(serverAgents[i]);
            }

            requestMotionStatusMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            // We want to receive a reply in 10 secs
            requestMotionStatusMessage.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
            requestMotionStatusMessage.setContent("movimento");

            addBehaviour(new AchieveREInitiator(myAgent, requestMotionStatusMessage) {

                protected void handleInform(ACLMessage inform) {
                    //System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
                    String messageContenut = inform.getContent();
                    System.out.println("Agente Gestore-Antifurto::::" + messageContenut);
                    if (messageContenut != null) {
                        try {

                            Boolean motionStatus = Boolean.valueOf(inform.getContent());
                            //System.out.println("Agente Gestore-Fuoco-Bool::::"+fireStatus);

                            Iterator<CurrentStatusInRoom> it = currentStatuses.iterator();
                            while (it.hasNext()) {

                                CurrentStatusInRoom currentStatusInRoom = it.next();
                                //System.out.println(currentTemperatureInRoom.getroomAgent().getName() + " " + msg.getSender().getName());
                                if (currentStatusInRoom.getRoomAgent().getName().equals(inform.getSender().getName())) {
                                    currentStatusInRoom.setCurrentMotionStatus(motionStatus);
                                }
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Agente Gestore-Antifurto::::errore");
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

    private class CurrentStatusInRoom {

        private Boolean currentMotionStatus;
        private AID roomAgent;

        public CurrentStatusInRoom() {
            currentMotionStatus = null;
            roomAgent = null;
            //fanOn = false;
        }

        public CurrentStatusInRoom(AID roomAgent) {
            setCurrentMotionStatus(null);
            setRoomAgent(roomAgent);
            //fanOn = false;
        }

        public CurrentStatusInRoom(AID roomAgent, Boolean currentMotionStatus) {
            setCurrentMotionStatus(currentMotionStatus);
            setRoomAgent(roomAgent);
            //fanOn = false;
        }

        public Boolean getCurrentMotionStatus() {
            return currentMotionStatus;
        }

        public void setCurrentMotionStatus(Boolean currentMotionStatus) {
            this.currentMotionStatus = currentMotionStatus;
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
        System.out.println("AntitheftAgent " + getAID().getName() + " terminating.");
    }
}
