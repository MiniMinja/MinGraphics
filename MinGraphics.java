import javax.swing.*;
import java.awt.*;
/**
 *This class will allow for all graphics to be managed
 *It will open up a Jframe, repaint at 60 fps no matter what
 */
public class MinGraphics extends JPanel implements Runnable{
	private JFrame frame;
	public MinGraphics(int w, int h){
		frame = new JFrame();
		
		this.setPreferredSize(new Dimension(w,h));
		
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	public void run(){
		while(true){
			repaint();
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	public void paint(Graphics g){
		super.paint(g);
	}
	public static void main(String[] args){
		new MinGraphics(1000,500);
	}

	
}
