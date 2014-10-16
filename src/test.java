import java.io.IOException;
import java.util.ArrayList;

import com.automic.AECredentials;
import com.automic.ConnectionManager;
import com.automic.objects.ObjectBroker;
import com.uc4.api.objects.UC4Object;
import com.uc4.communication.Connection;


public class test {

	public static void main(String[] args) throws IOException{
		
		String AEHostnameOrIP = "192.168.11.128";
		//String AEHostnameOrIP = "172.16.148.35";
		int AECPPrimaryPort = 2217;
		int AEClientNumber = 1001; // 330;
		String AEUserLogin = "UC4"; //"BSP";
		String AEUserDepartment = "UC4"; //"AUTOMIC";
		String AEUserPassword = "universe"; //"oneAutomation";
		char AEMessageLanguage = 'E';
		
		AECredentials myClient = new AECredentials(AEHostnameOrIP,AECPPrimaryPort,AEClientNumber,AEUserDepartment,AEUserLogin,AEUserPassword,AEMessageLanguage);
	
		ConnectionManager mgr = new ConnectionManager();
		Connection conn = mgr.connectToClient(myClient);
		ObjectBroker broker = new ObjectBroker(conn,false);
		ArrayList<UC4Object> myTable = broker.jobs.getAllJobs();
		
		for(UC4Object object : myTable){
			System.out.println(object.getName());
			
		}
		
	}
}
