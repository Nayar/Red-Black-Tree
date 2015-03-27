import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RBT {
	RBTnode root;
	private static final boolean R = true;
	private static final boolean B = false;
	Frame f;
	int noNodes = 0;
	final int XSPACING = 30;
	final int YSPACING = 75;
	Graphics2D g2d;
	
	
	class RBTDraw extends JFrame {
		public RBTDraw(){
			JPanel buttonPanel = new JPanel();
			JPanel drawingPanel = new RBTDrawPanel();
			final JTextField valueTB = new JTextField(5);
			buttonPanel.add(new JLabel("Insert Number"));
			buttonPanel.add(valueTB);
			ActionListener l = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == valueTB){
						insert(Integer.valueOf((valueTB.getText())));
						//System.out.println(Integer.getInteger((valueTB.getText())));
						valueTB.setText("");
						//repaint();
					}
				}
			};
			valueTB.addActionListener(l);
			//valueTB.getText();
			setLayout(null);
			buttonPanel.setBounds(0, 0, 1200, 25);
			drawingPanel.setBounds(0,25,1200,675);
			add(buttonPanel);
			add(drawingPanel);
			
		}
		
		class RBTDrawPanel extends JPanel {
			Double height;
			public void paintComponent(Graphics g){
				g2d = (Graphics2D)g;
				
				if(noNodes == 0)
					return;
				
				height = Math.floor(Math.log10(noNodes) / Math.log10(2));
				
				makegnodes(null, root, 500, 0,height.intValue());			
			}
			
			public void makegnodes(RBTnode parent, RBTnode current,int x,int y,int h){
				if(current == null)
					return;
				
				if(parent == null){
					drawnode(current,x,x,y,h);
					return;
				}
				
				if(current != null){
					if(current.get_cbit())
						g2d.setPaint(Color.red);
					else
						g2d.setPaint(Color.black);
					int xshift;
					if(current == parent.get_right())
						xshift = (int) (x + XSPACING * Math.pow(2,h));
					else
						xshift = (int) (x - XSPACING * Math.pow(2,h));
					drawnode(current,xshift,x,y,h);
					g2d.setPaint(Color.black);
				}
			}
			
			private void drawnode(RBTnode current,int xshift,int x,int y,int h){
				g2d.draw(new Ellipse2D.Double(xshift,y+50,50,50));
				g2d.drawString(Integer.toString(current.get_value()), xshift+25, y+25+50);
				g2d.draw(new Line2D.Double(x+25, y+25, xshift+25, y+50));
				makegnodes(current, current.get_left(), xshift, y+YSPACING,h-1);
				makegnodes(current, current.get_right(), xshift, y+YSPACING,h-1);
			}
		}
	}
	
	public RBT() {
		root=null;
		f = new RBTDraw();
		
	}
	
	public RBTnode get_root() {
		return root;
	}
	
	private boolean red(RBTnode x){
		if (x == null) return false;
		return x.get_cbit();
	}
	private RBTnode rotateRight(RBTnode h)
	{ 
		RBTnode x = h.get_left();
		h.set_left(x.get_right());
		x.set_right(h);
		//DisplayTree();
		return x;
	}
	private RBTnode rotateLeft(RBTnode h)
	{
		RBTnode x = h.get_right();
		h.set_right(x.get_left());
		x.set_left(h);
		//DisplayTree();
		return x;
	}
	private RBTnode insertR(RBTnode h, int x, boolean sw)
	{
		if (h == null) {
			return new RBTnode(x, R);
		}
		//Split a 4-node
		if (red(h.get_left()) && red(h.get_right()))
		{
			h.set_cbit(R);
			h.get_left().set_cbit(B);
			h.get_right().set_cbit(B);
		}
		if (x< h.get_value())
		{
			h.set_left(insertR(h.get_left(), x, false));
			if (red(h) && red(h.get_left()) && sw) h = rotateRight(h);
			if (red(h.get_left()) && red(h.get_left().get_left()))
			{
				h = rotateRight(h);
				h.set_cbit(B);
				h.get_right().set_cbit(R);
			}
		}
		else
		{
			h.set_right(insertR(h.get_right(), x, true));
			if (red(h) && red(h.get_right()) && !sw)
				h = rotateLeft(h);
			if (red(h.get_right()) && red(h.get_right().get_right()))
			{
				h = rotateLeft(h);
				h.set_cbit(B);
				h.get_left().set_cbit(R);
			}
		}
		return h;
	}
	void insert(int x)
	{
		//DisplayTree();
		root = insertR(root, x, B);
		root.set_cbit(B);
		noNodes++;
		DisplayTree();
	}
	
	public RBTnode SearchElement (int x) {
		RBTnode T;
		T=root;
		while((T!=null)&&(T.get_value()!=x)) {
		if(x<T.get_value())
			T=T.get_left();
		else
			T=T.get_right();
		}
		return T;
	}
	
	public void PrinRBTNode(RBTnode T)
	{
		System.out.print("\t("+ T.get_value()+","+T.get_cbit()+")");
	}
	public void printPREorder(RBTnode T)
	{
		if (T !=null)
		{
			PrinRBTNode(T) ;
			printPREorder(T.get_left());
			printPREorder(T.get_right());
		}
	}
	
	public void printINorder(RBTnode T)
	{
		if (T !=null)
		{
			printINorder(T.get_left());
			PrinRBTNode(T) ;
			printINorder(T.get_right());
		}
	}
	
	public void printPOSTorder(RBTnode T)
	{
		if (T !=null)
		{
			printPOSTorder(T.get_left());
			printPOSTorder(T.get_right());
			PrinRBTNode(T);
		}
	}
	public void removeAll(RBTnode T)
	{
		if(T!=null)
		{
			//Traverse the left subtree in postorder
			removeAll(T.get_left());
			//Traverse the right subtree in postorder
			removeAll(T.get_right());
			//Delete the leaf node from the tree
			T=null; //hint to be collected by Garbage Collector
		}
	}
	
	public void DisplayTree(){
			
		//f = new RBTDraw();
		System.out.print("Displaying Tree..\n");
		//f.removeAll();//or remove(JComponent)
		//f.validate();
		f.repaint();
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		
		f.setSize(1200, 700);
		f.setVisible(true);
		try {
			System.out.print("Sleeping..\n");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}	
	}
}
