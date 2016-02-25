package gateway.bean;
/*****************************************************************
This the message channel between the GateWayAgent and the servlet
*****************************************************************/

public class BlackBoardBean  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5860172021000743193L;
	private String message = new String("");
	private String receiver = new String("");

	public String getMessage()	{
		  	return message;
	}
	
	public void setMessage(String str)	{
		message=str;
	}
	
	public String getReceiver()	{
		return receiver;
	}
	
	public void setReceiver(String receiver)	{
		this.receiver=receiver;
	}
	
	public BlackBoardBean(String message)	{
		this.message = message;
	}

}