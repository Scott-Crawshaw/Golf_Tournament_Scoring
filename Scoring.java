import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Scoring {
	public ArrayList<Player> players = new ArrayList<Player>();
	public String location = "";
	
	public static void main(String[] args) {
		Scoring app = new Scoring();

	}

	public Scoring() {
		makeFrame();

	}

	public void makeFrame() {
		//build the GUI
		JFrame guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Twilight League Scoring");
		guiFrame.setSize(300,250);
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setLayout(new GridLayout(3, 1));

		JButton filePick = new JButton("Click Here to Choose Your Spreadsheet");
		JLabel filename = new JLabel(" No Spreadsheet Selected");
		filePick.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JFileChooser chooser = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		            filename.setText(chooser.getSelectedFile().getName());
		            location=chooser.getSelectedFile().getPath();
		        }
			}
			});

		JButton go = new JButton("Click Here to Generate Results");
		go.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				makePlayers();
				getWinners();
			}
			});
		
		guiFrame.add(filename);
		guiFrame.add(filePick);
		guiFrame.add(go);
		guiFrame.pack();
		guiFrame.setVisible(true);
	}

	public void results(String q, String n, String g) {
		//build results screen
		JFrame resultsFrame = new JFrame();
		resultsFrame.setTitle("Twilight League Results");
		resultsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		resultsFrame.setLayout(new GridLayout(1, 3));
		
		JLabel quota = new JLabel(q);
		JLabel net = new JLabel(n);
		JLabel gross = new JLabel(g);
		
		Border border = quota.getBorder();
		Border margin = new EmptyBorder(10,10,10,10);
		quota.setBorder(new CompoundBorder(border, margin));
		Border border2 = net.getBorder();
		Border margin2 = new EmptyBorder(10,10,10,10);
		net.setBorder(new CompoundBorder(border2, margin2));
		Border border3 = gross.getBorder();
		Border margin3 = new EmptyBorder(10,10,10,10);
		gross.setBorder(new CompoundBorder(border3, margin3));
		
		resultsFrame.add(quota);
		resultsFrame.add(net);
		resultsFrame.add(gross);
		resultsFrame.pack();
		resultsFrame.setVisible(true);
		
	}
	
	public void makePlayers() {
		//read player info from csv and build player objects
		String line="";
		Boolean start=false;
		
		try (BufferedReader br = new BufferedReader(new FileReader(location))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator'
            	if(start) {
                players.add(new Player(line.split(",")));
            	}
            	start=true;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void getWinners() {
		//establish the winners and write to html strings
		int count=0;
		String quota = "<html>";
		String net = "<html>";
		String gross = "<html>";
		
		//sort by quota and then write the best quotas
		Collections.sort(players, Player.PlayerQuota);
		
		quota+=("Best Quota:");
		quota+="<br/>";
		for(Player person : players) {
			count++;
			if(person.quota>0) {
				quota+=(count + ": " + person.name + " with a +" + person.quota);quota+="<br/>";
			}
			else {
				quota+=(count + ": " + person.name + " with a " + person.quota);quota+="<br/>";
			}
		}
		count=0;
		quota+=("");
		quota+="<br/>";
		quota+="</html>";

		//sort by net and then write the best quotas
		Collections.sort(players,Player.PlayerNet);
		
		net+=("Best Net:");
		net+="<br/>";
		
		for(Player person : players) {
			count++;
			net+=(count + ": " + person.name + " with a " + person.net);
			net+="<br/>";
		}

		count=0;
		net+=("");
		net+="<br/>";
		net+="</html>";

		//sort by gross and then write the best quotas
		Collections.sort(players,Player.PlayerGross);
		
		gross+=("Best Gross:");
		gross+="<br/>";
		
		for(Player person : players) {
			count++;
			gross+=(count + ": " + person.name + " with a " + person.gross);
			gross+="<br/>";
		}
		
		gross+="</html>";
		
		results(quota, net, gross);

	}

}
