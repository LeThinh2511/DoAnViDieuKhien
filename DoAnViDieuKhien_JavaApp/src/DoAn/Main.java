package DoAn;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class Main extends JFrame{
	
	SerialCommunicator couter = new SerialCommunicator(); // Doi tuong chiu trach nhiem doc du lieu tu cong serial => dem san pham
	
	// So luong san pham 1, 2
	int numOfProduct1 = 0;
	int numOfProduct2 = 0;
	
	// Doi tuong chiu trach nhiem lam viec voi database
	DatabaseManager manager = new DatabaseManager();
	// Doi tuong chiu trach nhiem ve do thi
	DrawGraph graph;
	
	
	// Cac tab tren chuong trinh chinh
	JPanel panelContent = new JPanel();
    JPanel inforPanel = new JPanel();
    JPanel graphPanel = new JPanel();
    JPanel countPanel = new JPanel();
    JPanel searchPanel = new JPanel();
    JMenuBar menuBar = new JMenuBar();
    CardLayout cardLayout = new CardLayout(); // Layout dieu khien cac tab tren chuong trinh
    
    
    int buttonHeight = 30;
    int buttonWidth = 80;
    int frameHeight = 800;
    int frameWidth = 1000;
    
    //----------------------------Constructor----------------------------------
	
	public Main()
	{
		this.couter.initialize(); /////////////////
		
		// Tao mot thread de doc du lieu
		this.setTitle("Hệ thống đếm và phân loại sản phẩm");
		this.setBounds(200, 50, frameWidth, frameHeight);
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
		
		configInforMenu();
		configCountMenu();
		configGraphMenu();
		configSearchMenu();
		
		this.panelContent.setLayout(cardLayout);
		this.panelContent.add(inforPanel, "inforPanel");
		this.panelContent.add(countPanel, "countPanel");
		this.panelContent.add(searchPanel, "searchPanel");
		this.panelContent.add(graphPanel, "graphPanel");
		this.panelContent.setBounds(0, buttonHeight, frameWidth, frameHeight - buttonHeight);
		this.cardLayout.show(panelContent, "inforPanel");
		this.add(panelContent);
		
		
		JMenu inforMenu = new JMenu("Thông tin");
		inforMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				cardLayout.show(panelContent, "inforPanel");
				//inforMenu.setSelected(false);
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JMenu countMenu = new JMenu("Đếm sản phẩm");
		countMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				cardLayout.show(panelContent, "countPanel");
				//countMenu.setSelected(false);
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JMenu graphMenu = new JMenu("Biểu đồ");
		graphMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				cardLayout.show(panelContent, "graphPanel");
				//graphMenu.setSelected(false);
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JMenu searchMenu = new JMenu("Tra cứu");
		searchMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				cardLayout.show(panelContent, "searchPanel");
				//searchMenu.setSelected(false);
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.menuBar.add(inforMenu);
		this.menuBar.add(searchMenu);
		this.menuBar.add(countMenu);
		this.menuBar.add(graphMenu);
		this.menuBar.setBounds(0, 0, frameWidth, buttonHeight);
		
		this.setJMenuBar(menuBar);
	}
	
	
	// -----------------------configure menus---------------------------
	
	
	// ---------------------- configure infor menu----------------------------
	public void configInforMenu()
	{
		inforPanel.setLayout(null);
		inforPanel.setBackground(Color.WHITE);
		
		JPanel inforArea = new JPanel();
		inforArea.setLayout(null);
		inforArea.setBounds(frameWidth/2 - 240, 50, 480, 250);
		inforArea.setBackground(Color.WHITE);
		inforPanel.add(inforArea);
		
		JLabel doAnLabel = new JLabel("ĐỒ ÁN VI ĐIỀU KHIỂN");
		doAnLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		doAnLabel.setBounds(125, 0, 250, buttonHeight);
		inforArea.add(doAnLabel);
		
		JLabel deTaiLabel = new JLabel("ĐỀ TÀI: HỆ THỐNG ĐẾM VÀ PHÂN LOẠI SẢN PHẨM");
		deTaiLabel.setBounds(0, 30, 480, buttonHeight);
		deTaiLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		inforArea.add(deTaiLabel);
		
		JLabel gvhdLabel = new JLabel("GVHD: TRẦN THẾ VŨ");
		gvhdLabel.setBounds(125, 70, 480, buttonHeight);
		gvhdLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		inforArea.add(gvhdLabel);
		
		JLabel svthLabel = new JLabel("SINH VIÊN THỰC HIỆN:");
		svthLabel.setBounds(125, 150, 480, buttonHeight);
		svthLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		inforArea.add(svthLabel);
		
		JTextArea nameArea = new JTextArea("1. Lê Thịnh                                             MSSV: 102150199 \n2. Nguyễn Văn Kỳ Phong                       MSSV: 102150187 \n3. Đậu Thị Lễ                                          MSSV: 102150176");
		nameArea.setBounds(0, 180, 480, 75);
		nameArea.setFont(new Font("Serif", Font.PLAIN, 20));
		inforArea.add(nameArea);
		
		JLabel picLabel = new JLabel();
		picLabel.setBounds(frameWidth/2 - 300, 300, 600, 450);
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("Picture/sanPham.jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("loi load picture!");
		}
		Image dimg = img.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		picLabel.setIcon(imageIcon);
		inforPanel.add(picLabel);
	}
	
	
	// -------------------------------config graph menu------------------------------
	public void configGraphMenu()
	{
		graphPanel.setLayout(null);
		JPanel graphArea = new JPanel();
		graphArea.setBounds(50, 50, 900, 480);
		graphPanel.add(graphArea);
		ArrayList<Integer> scores1 = new ArrayList<Integer>();
		ArrayList<Integer> scores2 = new ArrayList<Integer>();
		graph = new DrawGraph(scores1, scores2, 900, 480, true);
		graphArea.add(graph);
		
		JPanel configArea = new JPanel();
		configArea.setLayout(null);
		configArea.setBounds(50, 550, 900, 200);
		configArea.setBorder(BorderFactory.createTitledBorder("Cấu hình:"));
		graphPanel.add(configArea);
		
		JRadioButton yearRB = new JRadioButton("Năm");
		JRadioButton monthRB = new JRadioButton("Tháng");
		ButtonGroup modeGroup = new ButtonGroup();
		modeGroup.add(yearRB);
		modeGroup.add(monthRB);
		monthRB.setSelected(true);
		
		JLabel modeLabel = new JLabel("Chế độ:");
		modeLabel.setBounds(20, 50, 100, 20);
		yearRB.setBounds(20, 70, 100, 20);
		monthRB.setBounds(20, 90, 100, 20);
		configArea.add(modeLabel);
		configArea.add(monthRB);
		configArea.add(yearRB);
		
		JLabel yearLabel = new JLabel("Chọn năm:");
		yearLabel.setBounds(200, 50, 100, 20);
		configArea.add(yearLabel);
		
		Integer[] years = new Integer[] {2015, 2016, 2017, 2018};
		JComboBox<Integer> yearCB = new JComboBox<>(years);
		yearCB.setBounds(200, 70, 100, 20);
		configArea.add(yearCB);
		
		
		Integer[] months = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		JComboBox<Integer> monthCB = new JComboBox<>(months);
		monthCB.setBounds(200, 110, 100, 20);
		configArea.add(monthCB);
		
		JLabel monthLabel = new JLabel("Chọn tháng:");
		monthLabel.setBounds(200, 90, 100, 20);
		configArea.add(monthLabel);
		
		
		JButton showButton = new JButton("Xem");
		showButton.setBounds(350, 70, 100, 30);
		configArea.add(showButton);
		showButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (monthRB.isSelected())
				{
					if (manager.isConnected == true)
					{
						ArrayList<Data> data = manager.getDataOfAMonth(yearCB.getItemAt(yearCB.getSelectedIndex()), monthCB.getItemAt(monthCB.getSelectedIndex()));
						graph.getNewData(data, true);
					}
					else
					{
						annouce(true);
					}
				}
				else
				{
					if (manager.isConnected == true)
					{
						ArrayList<Data> data = manager.getDataOfAYear(yearCB.getItemAt(yearCB.getSelectedIndex()));
						graph.getNewData(data, false);
					}
					else 
					{
						annouce(true);
					}
					
				}
			}
		});
		
		yearRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				monthCB.setEnabled(false);
			}
		});
		
		monthRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				monthCB.setEnabled(true);
			}
		});
	}
	
	//--------------------------config search menu----------------------------
	
	public void configSearchMenu()
	{
		searchPanel.setLayout(null);
		
		JPanel configArea = new JPanel();
		configArea.setBounds(50, 50, 250, 700);
		configArea.setLayout(null);
		configArea.setBorder(BorderFactory.createTitledBorder("Cấu hình:"));
		searchPanel.add(configArea);
		
		JPanel resultArea = new JPanel();
		resultArea.setBounds(300, 57, 650, 690);
		resultArea.setBorder(BorderFactory.createTitledBorder(""));
		searchPanel.add(resultArea);
		
		JLabel input = new JLabel("Nhập ngày: ");
		input.setBounds(25, 20, 100, 20);
		configArea.add(input);
		
		JTextField inputTF = new JTextField();
		inputTF.setBounds(25, 40, 100, 30);
		inputTF.setUI(new HintTextFieldUI("  dd-mm-yyyy", true));
		configArea.add(inputTF);
		
		JButton showDayDataButton = new JButton("Xem");
		showDayDataButton.setBounds(125, 40, 100, 30);
		configArea.add(showDayDataButton);
		showDayDataButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			String inputString = inputTF.getText();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				java.util.Date date = dateFormat.parse(inputString);
				ArrayList<Data> datas = new ArrayList<Data>();
				if (manager.isConnected == true)
				{
					Data data = manager.getDataOfADate(date);
					if (data == null)
					{
						JOptionPane.showMessageDialog(searchPanel, "Không có dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, null);
					}
					else
					{
						datas.add(data);
						showResult(resultArea, datas);
					}
				}
				else 
				{
					annouce(true);
				}
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(searchPanel, "Định dạng ngày đúng là: dd-mm-yyyy.", "Định dạng ngày sai!", JOptionPane.INFORMATION_MESSAGE, null);
			}
			}
		});
		
		JLabel monthData = new JLabel("Xem dữ liệu trong tháng: ");
		monthData.setBounds(25, 100, 200, 20);
		configArea.add(monthData);
		
		JLabel yearLabel = new JLabel("Chọn năm:");
		yearLabel.setBounds(25, 120, 100, 20);
		configArea.add(yearLabel);
		
		Integer[] years = new Integer[] {2015, 2016, 2017, 2018};
		JComboBox<Integer> yearCB = new JComboBox<>(years);
		yearCB.setBounds(25, 140, 100, 20);
		configArea.add(yearCB);
		
		
		Integer[] months = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		JComboBox<Integer> monthCB = new JComboBox<>(months);
		monthCB.setBounds(25, 180, 100, 20);
		configArea.add(monthCB);
		
		JLabel monthLabel = new JLabel("Chọn tháng:");
		monthLabel.setBounds(25, 160, 100, 20);
		configArea.add(monthLabel);
		
		JButton showMonthDataButton = new JButton("Xem");
		showMonthDataButton.setBounds(125, 160, 100, 30);
		configArea.add(showMonthDataButton);
		showMonthDataButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (manager.isConnected == true)
				{
					ArrayList<Data> data = manager.getDataOfAMonth(yearCB.getItemAt(yearCB.getSelectedIndex()), monthCB.getItemAt(monthCB.getSelectedIndex()));
					showResult(resultArea, data);
				}
				else 
				{
					annouce(true);
				}
				
			}
		});
		
		JLabel yearData = new JLabel("Xem dữ liệu trong năm: ");
		yearData.setBounds(25, 250, 200, 20);
		configArea.add(yearData);
		
		JLabel year1Label = new JLabel("Chọn năm:");
		year1Label.setBounds(25, 270, 100, 20);
		configArea.add(year1Label);
		
		JComboBox<Integer> yearCB1 = new JComboBox<>(years);
		yearCB1.setBounds(25, 290, 100, 20);
		configArea.add(yearCB1);
		
		JButton showyearDataButton = new JButton("Xem");
		showyearDataButton.setBounds(125, 285, 100, 30);
		configArea.add(showyearDataButton);
		showyearDataButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (manager.isConnected == true)
				{
					ArrayList<Data> data = manager.getDataOfAYear(yearCB1.getItemAt(yearCB1.getSelectedIndex()));
					showResult(resultArea, data);
				}
				else 
				{
					annouce(true);
				}
			}
		});
		
	}
	
	// ------------------------config count menu-------------------------
	
	public void configCountMenu()

	{
		this.countPanel.setLayout(null);
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.setBounds(0, 0, 200, 100);
		p1.setBackground(Color.WHITE);
		JPanel p2 = new JPanel(); 
		p2.setLayout(new BorderLayout());
		p2.setBounds(400, 0, 200, 100);
		p2.setBackground(Color.WHITE);
		
		
		JLabel l1 = new JLabel("0");
		l1.setFont(new Font("Serif", Font.PLAIN, 80));
		l1.setHorizontalAlignment(JLabel.CENTER);
		JLabel p1Label = new JLabel("Sản phẩm 1");
		p1Label.setBounds(60, 110, 100, buttonHeight);
		JLabel l2 = new JLabel("0");
		l2.setFont(new Font("Serif", Font.PLAIN, 80));
		l2.setHorizontalAlignment(JLabel.CENTER);
		JLabel p2Label = new JLabel("Sản phẩm 2");
		p2Label.setBounds(460, 110, 100, buttonHeight);
		p1.add(l1, BorderLayout.CENTER);
		p2.add(l2, BorderLayout.CENTER);
		
		JPanel showArea = new JPanel();
		showArea.setLayout(null);
		showArea.setBounds(frameWidth/2 - 300, 100, 600, 200);
		countPanel.add(showArea);
		showArea.add(p2);
		showArea.add(p1);
		showArea.add(p1Label);
		showArea.add(p2Label);
		
		JLabel isConnectedLabel = new JLabel("");
		isConnectedLabel.setHorizontalAlignment(JLabel.CENTER);
		isConnectedLabel.setSize(500, buttonHeight);
		if (couter.isConnected == true)
		{
			isConnectedLabel.setForeground(Color.GREEN);
			isConnectedLabel.setText("                                Đã kết nối tới board!                               ");
		}
		else
		{
			isConnectedLabel.setForeground(Color.RED);
			isConnectedLabel.setText("                                Không tìm thấy board!                                ");
		}
		
		JPanel controlArea = new JPanel();
		controlArea.setBounds(frameWidth/2 - 150, 400, 300, 200);
		countPanel.add(controlArea);
		controlArea.add(isConnectedLabel);
		
		JButton saveButton = new JButton("Save");
		saveButton.setSize(100, 30);
		saveButton.setHorizontalAlignment(JButton.CENTER);
		controlArea.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (manager.isConnected == true)
				{
					Data data = new Data();
					Date newDate = Date.valueOf(LocalDate.now());
					data = manager.getDataOfADate(newDate);
					
					if (data == null)
					{
						data = new Data();
						System.out.println("chua co du lieu hom nay");
						data.numOfProduct1 = numOfProduct1;
						data.numOfProduct2 = numOfProduct2;
						data.date = newDate;
						manager.addNewData(data);
						couter.numOfP1 = 0;
						couter.numOfP2 = 0;
					}
					else
					{
						data.date = newDate;
						data.numOfProduct1 += numOfProduct1;
						data.numOfProduct2 += numOfProduct2;
						manager.updateData(data);
						System.out.println("update data");
						couter.numOfP1 = 0;
						couter.numOfP2 = 0;
						System.out.println(numOfProduct1);
					}
				}
				else 
				{
					annouce(true);
				}
			}
		});
		
		JToggleButton toggleBut = new JToggleButton("OFF");
		toggleBut.setSize(100, 30);
		toggleBut.setHorizontalAlignment(JToggleButton.CENTER);
		controlArea.add(toggleBut);
		toggleBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (couter.shouldCheckBoard == true)
				{
					couter.initialize();
				}
				if (couter.isConnected == true)
				{
					isConnectedLabel.setForeground(Color.green);
					isConnectedLabel.setText("                                Đã kết nối tới board!                               ");
					if (couter.writeMessage(0) == 1)
					{
						if (toggleBut.isSelected())
						{
							toggleBut.setText("ON");
						}
						else
						{
							toggleBut.setText("OFF");
						}
					}
					else
					{
						couter.shouldCheckBoard = true;
						isConnectedLabel.setForeground(Color.red);
						isConnectedLabel.setText("                                Không tìm thấy board!                                ");
						JOptionPane.showMessageDialog(null, "Không tìm thấy board!", "Thông báo!", JOptionPane.INFORMATION_MESSAGE, null);
					}
				}
				else
				{
					annouce(false);
					isConnectedLabel.setText("                                Không tìm thấy board!                                ");
				}
			}
		});
		
		new Thread()
		{
			public void run()
			{
				while(true)
				{
					numOfProduct1 = couter.numOfP1;
					numOfProduct2 = couter.numOfP2;
					l1.setText(String.valueOf(numOfProduct1));
					l2.setText(String.valueOf(numOfProduct2));
				}
			}
		}.start();
	}
	
	// Ham hien thi thong bao
	public void annouce(Boolean temp)
	{
		if (temp == true)
		{
			JOptionPane.showMessageDialog(searchPanel, "Chưa kết nối cơ sở dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, null);
		}
		else if (temp == false)
		{
			JOptionPane.showMessageDialog(searchPanel, "Không tìm thấy board!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
	
	// Ve do thi va add vao mot JPanel
	public void showResult(JPanel target, ArrayList<Data> data)
	{
		target.removeAll();
		DataTableModel model = new DataTableModel(data);
        JTable result = new JTable(model);
        result.setShowGrid(true);
        result.setGridColor(Color.black);
        JScrollPane scrollPane = new JScrollPane(result);
        scrollPane.setSize(650, 700);
        target.add(scrollPane);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        result.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        result.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        result.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
	}

	
	
	// ----------------------------ham main----------------------------
	public static void main(String[] args) {
		new Main();
	}
	
	
	 public class DataTableModel extends AbstractTableModel {

	        private ArrayList<Data> datas;

	        public DataTableModel(ArrayList<Data> datas) {
	            this.datas = new ArrayList<>(datas);
	        }
			@Override
	        public int getRowCount() {
	            return datas.size();
	        }

	        @Override
	        public int getColumnCount() {
	            return 3;
	        }

	        @Override
	        public String getColumnName(int column) {
	            String name = "??";
	            switch (column) {
	                case 0:
	                    name = "Ngày";
	                    break;
	                case 1:
	                    name = "Sản phẩm 1";
	                    break;
	                case 2:
	                    name = "Sản phẩm 2";
	                    break;
	            }
	            return name;
	        }

	        @Override
	        public Class<?> getColumnClass(int columnIndex) {
	            Class<?> type = String.class;
	            switch (columnIndex) {
	                case 0:
	                	type = Date.class;
	                case 1:
	                case 2:
	                    type = Integer.class;
	                    break;
	            }
	            return type;
	        }

	        @Override
	        public Object getValueAt(int rowIndex, int columnIndex) {
	            Data data = datas.get(rowIndex);
	            Object value = null;
	            switch (columnIndex) {
	                case 0:
	                    value = data.date;
	                    break;
	                case 1:
	                    value = data.numOfProduct1;
	                    break;
	                case 2:
	                    value = data.numOfProduct2;
	                    break;
	            }
	            return value;
	        }            
	    }        

}
