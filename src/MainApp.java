import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RBT x = new RBT();
		x.insert(1);
		x.printINorder(x.root);
		
		//x.printINorder(x.root);
		//System.out.println();
		//x.printPOSTorder(x.root);
	}

}
