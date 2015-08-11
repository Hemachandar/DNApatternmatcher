import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*; 
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class dwnld extends JFrame implements ActionListener , Observer
{
	private JMenuBar mbar;
	private JMenu file,abt;
	private JMenuItem e,abt1;
	private JCheckBoxMenuItem dloc;
	private JPanel jp1,jpm,jp2,jp3,jp4,jtp1,jtp2,jtp3,jtp4;
	private JScrollPane jsp1,jsp2;
	private JLabel jl1,img,info3,info4;
	private JTabbedPane jtp;
	private JTextField search;
	private TextArea txta;
	private JButton add,resume,stop,pause,clear,cb,open,openf,info1,info2,show,play;
	private ImageIcon i1,i2,i3,i4;
	private DownloadsTableModel tableModel;
	private JTable table;
	private Download selectedDownload;
	private boolean clearing;
	private int w,h,flag=0;
	public String fdir,fname;
	class jmain extends JPanel
	{
		dwnld d;
		public jmain(dwnld d)
		{
			this.d = d;
			jp2=new JPanel();
			jp3=new JPanel();
			jp4=new JPanel();
			jp2.setBounds(0,0,750,70);//north
			jp3.setBounds(0,70,750,270);//center
			jp4.setBounds(0,340,750,240);//south
			jp2.setBorder(BorderFactory.createTitledBorder("ADD DOWNLOAD"));
			jp3.setBorder(BorderFactory.createTitledBorder("DOWNLOADS"));
			jp4.setBorder(BorderFactory.createTitledBorder("DOWNLOADED INFO"));
			//jp2
			//jp2.setBackground(Color.gray);
			jp2.add(jl1 = new JLabel("PASTE YOUR URL HERE TO DOWNLOAD"));
			jp2.add(search = new JTextField(50));
			jp2.add(add = new JButton("ADD"));
			add.addActionListener(d);
			add.setPreferredSize(new Dimension(w=add.getPreferredSize().width,h=add.getPreferredSize().height-5));
			jp2.add(cb = new JButton("CLEAR"));
			cb.addActionListener(d);
			cb.setPreferredSize(new Dimension(w+15,h));
			//jp3
			i1 = new ImageIcon("play.png");
			resume = new JButton(i1);
			resume.setActionCommand("resume");
			resume.addActionListener(d);
			resume.setPreferredSize(new Dimension(w,h));
			jp3.add(resume);
			i2 = new ImageIcon("pause.png");
			pause = new JButton(i2);
			pause.setActionCommand("pause");
			pause.addActionListener(d);
			pause.setPreferredSize(new Dimension(w,h));
			jp3.add(pause);
			i3 = new ImageIcon("stop.png");
			stop = new JButton(i3);
			stop.setActionCommand("stop");
			stop.addActionListener(d);
			stop.setPreferredSize(new Dimension(w,h));
			jp3.add(stop);
			clear = new JButton("Clear");
			clear.setPreferredSize(new Dimension(w+15,h));
			jp3.add(clear);
			tableModel = new DownloadsTableModel();
			table = new JTable(tableModel);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent e)
				{
					tableSelectionChanged();
				}
			});
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			ProgressRenderer renderer = new ProgressRenderer(0, 100);
			renderer.setStringPainted(true);
			table.setDefaultRenderer(JProgressBar.class, renderer);
			table.setRowHeight((int)renderer.getPreferredSize().getHeight());
			JPanel downloadsPanel = new JPanel();
			downloadsPanel.setLayout(new BorderLayout());
			downloadsPanel.add(new JScrollPane(table),BorderLayout.CENTER);
			downloadsPanel.setPreferredSize(new Dimension(730,200));
			jp3.add(downloadsPanel);
			pause.setEnabled(false);
			resume.setEnabled(false);
			stop.setEnabled(false);
			clear.setEnabled(false);
			//jp4
			jp4.setLayout(null);
			jtp = new JTabbedPane();
			jtp.addTab("FILE PREVIEW",jsp1=new JScrollPane(jtp1=new JPanel(),ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
			jtp.addTab("IMAGE PREVIEW",jsp2=new JScrollPane(jtp2=new JPanel(),ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
			jtp.addTab("FILE INFO",jtp3=new JPanel());
			jtp.addTab("MUSIC PREVIEW",jtp4=new JPanel());
			jp4.add(jtp);
			jtp.setBounds(10,17,720,210);
			//jtp1
			jtp1.setLayout(new BorderLayout());
			jtp1.add(info1=new JButton("CLICK HERE TO SHOW PREVIEW"),BorderLayout.NORTH);
			info1.addActionListener(d);
			info1.setPreferredSize(new Dimension(info1.getPreferredSize().width+80,info1.getPreferredSize().height));
			JLabel l1;
			jtp1.add(l1=new JLabel("         "),BorderLayout.CENTER);
			jtp1.add(txta=new TextArea(),BorderLayout.SOUTH);
			txta.setEditable(false);
			txta.setEnabled(false);
			
			//jtp2
			jtp2.setLayout(new BorderLayout());
			jtp2.add(info2=new JButton("CLICK HERE TO SHOW IMAGE PREVIEW"),BorderLayout.NORTH);
			info2.addActionListener(d);
			info2.setPreferredSize(new Dimension(info2.getPreferredSize().width+80,info2.getPreferredSize().height));
			JLabel l2;
			jtp2.add(l2=new JLabel("         "),BorderLayout.CENTER);
			//Image image=GenerateImage.toImage(true);
			jtp2.add(img=new JLabel(new ImageIcon("default.png")),BorderLayout.SOUTH);
			
			//jtp3
			JPanel jtp3_1=new JPanel();
			JPanel jtp3_2=new JPanel();
			JPanel jtp3_3=new JPanel();
			jtp3.setLayout(new BorderLayout());
			open=new JButton("SHOW IN FOLDER");
			open.addActionListener(d);
			jtp3_1.add(open);
			openf=new JButton("OPEN FILE");
			openf.addActionListener(d);
			jtp3_1.add(openf);
			show=new JButton("SHOW DETAILS");
			show.addActionListener(d);
			jtp3_1.add(show);
			jtp3.add(jtp3_1,BorderLayout.NORTH);
			jtp3_2.add(info3=new JLabel("DIRECTORY NAME : "));
			jtp3_3.add(info4=new JLabel("FILE NAME      : "));
			jtp3.add(jtp3_2,BorderLayout.CENTER);
			jtp3.add(jtp3_3,BorderLayout.SOUTH);
			add(jp2);
			add(jp3);
			add(jp4);
			
			//jtp4
			
			jtp4.add(play=new JButton("CLICK HERE TO PLAY THE MUSIC"));
			play.addActionListener(d);
		}
		private void tableSelectionChanged()
		{
			if (selectedDownload!=null)
				selectedDownload.deleteObserver(d);//this
			if (!clearing)
			{
				selectedDownload=tableModel.getDownload(table.getSelectedRow());
				selectedDownload.addObserver(d);//this
				updateButtons();
			}
		}
	}
	public dwnld()
	{
		//FRAME
		super("JDM v1.5");
		setResizable(false);
		setSize(1010,620);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//MENUBAR
		mbar=new JMenuBar();
		setJMenuBar(mbar);
		file=new JMenu("File");
		file.add(dloc=new JCheckBoxMenuItem("Enable default location"));
		dloc.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				if(flag==0)
				{
					flag=1;
					Frame f=new Frame("Set Default Location");
					f.setLayout(new FlowLayout());
					f.setVisible(true);
					f.setSize(700,200);
					f.add(new Label("Enter Path : "));
					TextField tf1=new TextField(100);
					f.add(tf1);
					Button fb=new Button("SAVE");
					f.add(fb);
					fb.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent ae)
						{
							if(ae.getSource()==fb)
							{
								fdir=tf1.getText();
								f.dispose();
							}
						}
					});
				}
				else	
					flag=0;
			}
		});
		file.add(new JMenuItem("----------------------------------"));
		file.add(e = new JMenuItem("Exit"));
		e.addActionListener(this);
		mbar.add(file);
		abt=new JMenu("Help");
		abt.add(abt1=new JMenuItem("About"));
		abt1.addActionListener(new ActionListener()
		{
				public void actionPerformed(ActionEvent ae)
				{
					new about();
				}
		});
		mbar.add(abt);
		//PANELS
		jp1=new JPanel();
		jp1.setBounds(0,0,250,580);//west
		jp1.setBorder(BorderFactory.createTitledBorder("JAVA DOWNLOAD MANAGER"));
		jpm=new jmain(this);
		jpm.setLayout(null);
		jpm.setBounds(250,0,750,580);//east
		try
		{
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
		}
		catch(IOException e)
		{
			System.out.println("Error in loading:");
		}
		//ADD
		add(jp1);
		add(jpm);
	}
	private URL verify(String u)
	{
		if(!u.toLowerCase().startsWith("http://"))
			return null;
		URL test=null;
		try
		{
			test=new URL(u);
		}
		catch(Exception e)
		{
			return null;
		}
		if(test.getFile().length()<2)
			return null;
		return test;
	}
	private void updateButtons()
	{
		if (selectedDownload != null)
		{
			int status = selectedDownload.getStatus();
			switch (status)
			{
				case Download.DOWNLOADING:	pause.setEnabled(true);
											resume.setEnabled(false);
											stop.setEnabled(true);
											clear.setEnabled(false);
											break;
				case Download.PAUSED:		pause.setEnabled(false);
											resume.setEnabled(true);
											stop.setEnabled(true);
											clear.setEnabled(false);
											break;
				case Download.ERROR:		pause.setEnabled(false);
											resume.setEnabled(true);
											stop.setEnabled(false);
											clear.setEnabled(true);
											break;
				default: 					pause.setEnabled(false);
											resume.setEnabled(false);
											stop.setEnabled(false);
											clear.setEnabled(true);
			}
		}
		else
		{
			pause.setEnabled(false);
			resume.setEnabled(false);
			stop.setEnabled(false);
			clear.setEnabled(false);
		}
	}
	public void update(Observable o, Object arg)
	{
		if (selectedDownload!=null && selectedDownload.equals(o))
			updateButtons();
	}
		String getFileName(URL url) {
    String fileName = url.getFile();
    return fileName.substring(fileName.lastIndexOf('/') + 1);
  }
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==e)
		{
			dispose();
		}
		if(ae.getSource()==add)
		{
			String mys;
			URL u=verify(search.getText());
			if(u!=null)
			{
				if(flag==0)
				{
				FileDialog fd=new FileDialog(this,"Choose folder to save the file",FileDialog.SAVE);
				fd.setFile(getFileName(u));
				fd.setVisible(true);
				fname=fd.getFile();
				fdir=fd.getDirectory();
				if(fd.getFile()==null && fd.getDirectory()==null)
					return;
				}
				else
					fname=getFileName(u);
				tableModel.addDownload(new Download(u,this));
				search.setText("");
			}
			else
				JOptionPane.showMessageDialog(this,"Invalid Download URL", "Error",JOptionPane.ERROR_MESSAGE);
		}
		if(ae.getSource()==cb)
		{
			search.setText("");
		}
		if(ae.getSource()==open)
		{
			try{
			if(selectedDownload==null)
				return;
			Desktop.getDesktop().open(new File(selectedDownload.fdir));
			}
			catch(Exception e){System.out.println(e);}
		}
		if(ae.getSource()==openf)
		{
			try{
			if(selectedDownload==null)
				return;
			Desktop.getDesktop().open(new File(selectedDownload.fdir+selectedDownload.fname));
			}
			catch(Exception e){System.out.println(e);}
		}
		if(ae.getSource()==pause)
		{
			selectedDownload.pause();
			updateButtons();
		}
		if(ae.getSource()==resume)
		{
			selectedDownload.resume();
			updateButtons();
		}
		if(ae.getSource()==stop)
		{
			selectedDownload.cancel();
			updateButtons();
		}
		if(ae.getSource()==play)
		{
			if(selectedDownload==null)
				return;
			int mi=selectedDownload.fname.lastIndexOf(".");
			String s=selectedDownload.fname.substring(mi+1);
			String bip;
			if(s.equals("wav") || s.equals("mp3"))
			{
				//AudioClip music=new AudioClip(selectedDownload.fdir,selectedDownload.fname);
				//music.play();
				
				try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(selectedDownload.fdir+selectedDownload.fname).getAbsoluteFile());
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
				} catch(Exception ex) {
				System.out.println("Error with playing sound.");
				ex.printStackTrace();
				}
			}
		}	
		if(ae.getSource()==info1)
		{
			try{
			FileReader f=null;
			if(selectedDownload==null)
				return;
			int mi=selectedDownload.fname.lastIndexOf(".");
			String s=selectedDownload.fname.substring(mi+1);
			if(s.equals("html") || s.equals("htm") || s.equals("txt") || s.equals("doc") || s.equals("docx") || s.equals("pdf"))
				f=new FileReader(selectedDownload.fdir+selectedDownload.fname);
			else	
				return;
			char buf[]=new char[1024000];
			String msg;
			int ch,k=0;
			while((ch=f.read())!=-1)
				buf[k++]=(char)ch;
			msg=String.valueOf(buf);
			txta.setEnabled(true);
			txta.setText(msg);
			this.revalidate();
			jtp1.revalidate();
			}catch(Exception e){System.out.println(e);}
		}
		if(ae.getSource()==info2)
		{
			if(selectedDownload==null)
				return;
			int mi=selectedDownload.fname.lastIndexOf(".");
			String s=selectedDownload.fname.substring(mi+1);
			if(s.equals("png") || s.equals("jpeg") || s.equals("jpg") || s.equals("gif"))
				img.setIcon(new ImageIcon(selectedDownload.fdir+selectedDownload.fname));
			this.revalidate();
			jtp2.revalidate();
			jsp2.revalidate();
		}
		if(ae.getSource()==show)
		{
			if(selectedDownload==null)
				return;
			info3.setText("DIRECTORY NAME : "+selectedDownload.fdir);
			info4.setText("FILE NAME      : "+selectedDownload.fname);
		}
		if(ae.getSource()==clear)
		{
			clearing = true;
			tableModel.clearDownload(table.getSelectedRow());
			clearing = false;
			selectedDownload = null;
			updateButtons();
		}
	}
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new dwnld();
			}
		});
	}
}