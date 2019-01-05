import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel; // For a dynamic table


public class My_Form extends JFrame implements ActionListener
  {
    JTextField Nametxt, Idtxt, Agetxt, Cardnumbertxt, Countrytxt, Countytxt, Citytxt;
    JLabel Name, ID, Age, Gender, CardNumber, Country, County, City, gn;
    JMenuItem clients, exit;
    JMenu choice;
    JMenuBar bar;
    JButton save;
    JRadioButton male, female;
    ButtonGroup bg;
    JButton check;
    JPanel panel1, panel2, panel3;
    DefaultTableModel model;
    JTable jt;
    
    // JDBC driver name and database URL
    public final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    
    //Specify the database name in the URL string. The table has to exist
    public final String DB_URL = "jdbc:mysql://localhost:3306/Insurance";
    
    // Database credentials
    public final String USER = "root";
    public final String PASS = "javaclass";
    
    Connection conn = null, conn2 = null, conn3 = null;
    Statement stmt = null, stmt3 = null;
    PreparedStatement stmt2 = null;
    DatabaseMetaData dbm = null; //check to see if table exists
    ResultSet rs = null; //Used to get the table from the database
    
    int IDnum, Agenum, CardNumbernum;
    
    My_Form()
     {
            super();
            setSize(500, 700); // Size of the frame
            setTitle("Your Information"); //Title of the Frame
            setLayout(new GridLayout(2,1)); ////components will be arranged in a 2 by 1 grid
           
            Nametxt = new JTextField(20);
            Name = new JLabel("Name");
            
            Idtxt = new JTextField(20);
            ID = new JLabel("ID");
            
            Agetxt = new JTextField(20);
            Age = new JLabel("Age");
            
            
            Gender = new JLabel("Gender");
            male =  new JRadioButton("Male");
            female =  new JRadioButton("Female");
            bg = new ButtonGroup();
            bg.add(male);
            bg.add(female);
            gn = new JLabel("Gender");
            
            Cardnumbertxt = new JTextField(20);
            CardNumber = new JLabel("Card Number");
            
            Countrytxt = new JTextField(20);
            Country = new JLabel("Country");
            
            Countytxt = new JTextField(20);
            County = new JLabel("County");
            
            Citytxt = new JTextField(20);
            City = new JLabel("City");
            
            choice = new JMenu("Menu");
            clients =  new JMenuItem("Clients");
            exit = new JMenuItem("Exit");
            choice.add(clients);
            choice.add(exit);
            clients.addActionListener(this);
            exit.addActionListener(this);
            bar = new JMenuBar();
            bar.add(choice);
            setJMenuBar(bar);
            
            save = new JButton("Save");
            
            
            male.addActionListener(this); //registering listeners for the button
            female.addActionListener(this);
            save.addActionListener(this);
            
            panel1 = new JPanel();
            panel2 = new JPanel();
            panel3 = new JPanel();

            
            panel1.add(Name);
            panel1.add(Nametxt);
            panel1.add(ID);
            panel1.add(Idtxt);
            panel1.add(Age);
            panel1.add(Agetxt);
            panel1.add(Gender);
            panel1.add(male);
            panel1.add(female);
            
            panel2.add(CardNumber);
            panel2.add(Cardnumbertxt);
            panel2.add(Country);
            panel2.add(Countrytxt);
            panel2.add(County);
            panel2.add(Countytxt);
            panel2.add(City);
            panel2.add(Citytxt);
            
            panel3.add(save);
            
            add(panel1);
            add(panel2);
            add(panel3);
            
            databaseconnection();  
            

         }
         
    
    public void actionPerformed(ActionEvent ae)
    {
      if (ae.getSource()==male)
      {
        gn.setText("Male");
      }
      
      if (ae.getSource()==female)
      {
        gn.setText("Female");
      }
      
      if (ae.getSource()==clients)
      {
        
      }
      
      if (ae.getSource()==save)
      {
        String n = Nametxt.getText();
        String i = Idtxt.getText();
        String a = Agetxt.getText();
        String g = gn.getText();
        String cd = Cardnumbertxt.getText();
        String c = Countrytxt.getText();
        String ct = Countytxt.getText();
        String cy = Citytxt.getText(); 
        
        try
         {
            Class.forName("com.mysql.jdbc.Driver");
            
               if (!n.equals("") && !i.equals("") && !a.equals("") && !g.equals("") && !cd.equals("") && !c.equals("") && !ct.equals("") && !cy.equals(""))
                  {
                   try
                     {
                        IDnum = Integer.parseInt(i);
                        Agenum = Integer.parseInt(a);
                        CardNumbernum = Integer.parseInt(cd);
                        
                        conn2 = DriverManager.getConnection(DB_URL, USER, PASS);
                        String query = "INSERT INTO Insurance(name, id, age, gender, card number, country, county, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        stmt2 = conn2.prepareStatement(query); 
                        stmt2.setString(1,n);
                        stmt2.setInt(2, IDnum);
                        stmt2.setInt(3, Agenum);
                        stmt2.setString(4, g);
                        stmt2.setInt(5, CardNumbernum);
                        stmt2.setString(6,c);
                        stmt2.setString(7,ct);
                        stmt2.setString(8,cy);
                        stmt2.executeUpdate();
                        
                        System.out.println("Data inserted successfully");
                        
                        JOptionPane.showMessageDialog(this,"Data inserted successfully");
                        
                     }
                     
                    catch (NumberFormatException e) // checks if the fields entered are integers
                        {
                           JOptionPane.showMessageDialog(this,"The data is incorrect...");
                        }
                     
                  }
                  
                   else 
                     {
                       JOptionPane.showMessageDialog(this,"Your fields are empty. Fill them with data...");
                     }                     
                    
                  
            }
            
         
          catch (Exception e) {e.printStackTrace();}
            
               try
                {
                  if(conn2 != null)
                     {
                        stmt2.close();
                        conn2.close();
                     } 
                 
                }
             catch (SQLException se)
               {
                  System.out.println("Unable to close resource");
                  se.printStackTrace();
               }
      }
    } 
    
    public void databaseconnection()
     {
      try
         {
            //STEP 2: Register JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");
            
            //STEP3: Open a connection
            System.out.println("Connecting to a selected database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully");
            
            //STEP 4: Allocate a statement object that will be used for SQL queries
            stmt = conn.createStatement();
            
            //STEP 5: Execute a query or queries
            
            //First check if such table exists on the database
            
            dbm = conn.getMetaData();
            
            rs = dbm.getTables(null, null, "Insurance", null);
            
            if(rs.next())
             {
               System.out.println("table already exists");
             }
            else
              {
               String sql = "CREATE TABLE INSURANCE" + 
                            "( name VARCHAR(255) NOT NULL," +
                            "id INTEGER(20)," +
                            "age INTEGER(10),"+
                            "gender VARCHAR(255) NOT NULL,"+
                            "card number INTEGER(30)," +
                            "country VARCHAR(255) NOT NULL,"+
                            "county VARCHAR(255) NOT NULL,"+
                            "city VARCHAR(255) NOT NULL,)";
                            
               stmt.executeUpdate(sql); //execute the sql statement
               
               System.out.println("table created successully");
              }
              
         }
         
         catch (ClassNotFoundException e)
            {
               System.out.println("JDBC Driver was not found");
            }
         catch (SQLException e)
            {
               System.out.println("Unable to create a connection to the MySql database");
               e.printStackTrace();
            }
         try 
            {
               if(conn != null)
                  {
                     stmt.close();
                     System.out.println("Statement clossed...");
                     
                     conn.close();
                     System.out.println("JDBC Connection clossed...");
                  }
               
            }
            catch (SQLException e)
               {
                  System.out.println("Unable to close resource");
               }
         
     }    
       
    public static void main(String arg [])
         {
            MKH_Insurance i = new MKH_Insurance();
            i.setVisible(true);
         }
         
   }