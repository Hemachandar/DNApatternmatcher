import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*; 
import java.awt.image.BufferedImage;
class about extends Frame 
{
	public about()
	{
		super("ABOUT JDM v1.5");
		try
		{
			setBackground(Color.white);
			//FRAME
			setResizable(false);
			setSize(900,600);
			setVisible(true);
			setLayout(null);
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//pic
			BufferedImage myPic = ImageIO.read(new File("logo1.png"));
			JLabel picLabel1 = new JLabel(new ImageIcon(myPic));
			picLabel1.setBounds(25,150,200,200);
			add(picLabel1);
			//text
			BufferedImage text = ImageIO.read(new File("jdm.png"));
			JLabel picLabel2 = new JLabel(new ImageIcon(text));
			picLabel2.setBounds(30,360,200,200);
			add(picLabel2);
			BufferedImage tit = ImageIO.read(new File("title.png"));
			JLabel picLabel3 = new JLabel(new ImageIcon(tit));
			picLabel3.setBounds(250,150,550,350);
			add(picLabel3);
			addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent w)
				{
					dispose();
				}
			});
		}
		catch(IOException e)
		{
			System.out.println("Error in loading:");
		}
	}
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new about();
			}
		});
	}
}