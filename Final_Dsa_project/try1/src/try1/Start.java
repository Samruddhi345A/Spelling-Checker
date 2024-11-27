package try1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
class CreateLoginForm extends JFrame implements ActionListener  
{  
    //initialize button, panel, label, and text field  
    JButton b1;  
    JPanel newPanel;  
    JLabel userLabel, passLabel;  
    final JTextField  textField1, textField2;  
      
    //calling constructor  
    CreateLoginForm()  
    {     
          
        //create label for username   
        userLabel = new JLabel();  
        userLabel.setText("Username");      //set label value for textField1  
          
        //create text field to get username from the user  
        textField1 = new JTextField(15);    //set length of the text  
  
        //create label for password  
        passLabel = new JLabel();  
        passLabel.setText("Password");      //set label value for textField2  
          
        //create text field to get password from the user  
        textField2 = new JPasswordField(15);    //set length for the password  
          
        //create submit button  
        b1 = new JButton("SUBMIT"); //set label to button  
          
        //create panel to put form elements  
        newPanel = new JPanel(new GridLayout(3, 1));  
        newPanel.add(userLabel);    //set username label to panel  
        newPanel.add(textField1);   //set text field to panel  
        newPanel.add(passLabel);    //set password label to panel  
        newPanel.add(textField2);   //set text field to panel  
        newPanel.add(b1);           //set button to panel  
          
        //set border to panel   
        add(newPanel, BorderLayout.CENTER);  
          
        //perform action on button click   
        b1.addActionListener(this);     //add action listener to button  
        setTitle("LOGIN FORM");         //set title to the login form  
    }  
      
    //define abstract method actionPerformed() which will be called on button click   
    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter  
    {  
        String userValue = textField1.getText();        //get user entered username from the textField1  
        String passValue = textField2.getText();        //get user entered pasword from the textField2  
          
        //check whether the credentials are authentic or not  
        if (userValue.equals("admin@gmail.com") && passValue.equals("trialpassword")) {  //if authentic, navigate user to a new page  
              
            //create instance of the NewPage  
        	adminadd page = new adminadd();
            
              
            //make page visible to the user  
        	page.setVisible(true);
        	 
        }  
        else{  
            //show error message  
            System.out.println("Please enter valid username and password");  
        }  
    }  
}  
public class Start {
	public void opening() {
        JFrame frame = new JFrame("Spelling Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        
        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("D:\\java_project\\trial2\\Final_Dsa_project\\try1\\src\\try1\\Spelling.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        
        // Set the layout to OverlayLayout
        JPanel panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));
        
        // Add the background label to the panel
       
        
    // Create buttons
    JButton adminButton = new JButton("Admin Login");
    JButton userButton = new JButton("User  Login");
    
    // Create a panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false); // Make the button panel transparent
    buttonPanel.add(adminButton);
    buttonPanel.add(userButton);
       
    // Add the button panel to the overlay panel
    panel.add(buttonPanel);
    panel.add(backgroundLabel);
    // Add action listeners to the buttons
    adminButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Action for Admin Login button
            try {
                // Create instance of the CreateLoginForm
                CreateLoginForm form = new CreateLoginForm();  
                form.setSize(300, 100);  // Set size of the frame  
                form.setVisible(true);  // Make form visible to the user  
            } catch (Exception ex) {     
                // Handle exception   
                JOptionPane.showMessageDialog(null, ex.getMessage());  
            }  
        }
    });

    userButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	 SpellingChecker checker = new SpellingChecker();
             checker.setVisible(true);
        }
    });

    // Add the main panel to the frame
    frame.add(panel);
    
    // Make the frame visible
    frame.setVisible(true);
  }
   
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Start st= new Start();
		st.opening();
	}
}