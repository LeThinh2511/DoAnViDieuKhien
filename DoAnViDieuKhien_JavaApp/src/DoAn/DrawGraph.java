package DoAn;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

@SuppressWarnings("serial")
public class DrawGraph extends JPanel {
	
	
	
	private int MAX_SCORE = 160;	// Gia tri cao nhat cua do thi 
	private int PREF_W = 900;		// Chieu rong cua do thi
	private int PREF_H = 320;		// Chieu cao cua do thi
	private int BORDER_GAP = 30;	// Do day cua duong bien
	private Color GRAPH_COLOR = Color.green; // Mau cua cac duong trong do thi
	private Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180); // Mau cua cac diem trong do thi
	private Stroke GRAPH_STROKE = new BasicStroke(3f); // Doi tuong Stroke de ve duong
	private int GRAPH_POINT_WIDTH = 12; // Ban kinh cua cac diem
	private int Y_POINT = 16; // so diem theo chieu doc
	private ArrayList<Integer> product1; // Mang cac gia tri cua san pham 1 can ve
	private ArrayList<Integer> product2; // Mang cac gia tri cua san pham 2 can ve
	private Boolean mode = true; // true: xem theo thang, fasle: xem theo nam

	// Constructor khoi tao cac gia tri can thiet de ve do thi
	public DrawGraph(ArrayList<Integer> product1, ArrayList<Integer> product2, int width, int height, Boolean mode) 
	{
		this.product1 = product1;
		this.product2 = product2;
		this.PREF_H = height;
		this.PREF_W = width;
		this.mode = mode;
		if (mode == false) // Chinh lai gia tri cao nhat ung voi che do xem
		{
			this.MAX_SCORE = 4800;
		}
	}
	
	// Ham nhan du lieu la mot ArrayList<Data> va luu cac gia tri can ve vao ArrayList<Integer>
	@SuppressWarnings("deprecation")
	public void getNewData(ArrayList<Data> data, Boolean mode)
	{
		this.mode = mode;
		
		if (mode == true) // Neu la che do xem theo thang
		{
			this.MAX_SCORE = 160;
			ArrayList<Integer> product1 = new ArrayList<>();
			ArrayList<Integer> product2 = new ArrayList<>();
			
			for (int i = 0; i < data.size(); i++)
			{
				product1.add(data.get(i).numOfProduct1);
				product2.add(data.get(i).numOfProduct2);
			}
			this.product1 = product1;
			this.product2 = product2;
		}
		else // Nguoc lai che do xem theo nam
		{
			this.MAX_SCORE = 4800;
			ArrayList<Integer> product1 = new ArrayList<>();
			ArrayList<Integer> product2 = new ArrayList<>();
			int temp1 = 0;
			int temp2 = 0;
			int month = 0;
			Data tempData = null;
			for (int i = 0; i < data.size(); i++)
			{
				tempData = data.get(i);
				if (i == data.size() - 1)
				{
					temp1 += tempData.numOfProduct1;
					temp2 += tempData.numOfProduct2;
					product1.add(temp1);
					product2.add(temp2);
				}
				if (tempData.date.getMonth() == month)
				{
					temp1 += tempData.numOfProduct1;
					temp2 += tempData.numOfProduct2;
				}
				else
				{
					product1.add(temp1);
					product2.add(temp2);
					temp1 = tempData.numOfProduct1;
					temp2 = tempData.numOfProduct2;
					month++;
				}
			}
			this.product1 = product1;
			this.product2 = product2;
		}
		
		this.repaint();
	}

	// Ham ve do thi dua vao cac thong so da co
	@Override
	public void paintComponent(Graphics g) 
	{
		this.removeAll(); // Xoa tat ca de ve lai, tranh viec cac components chong len nhau
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Tinh toan chieu ngang va chieu doc
		double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (product1.size() - 1);
		double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE - 1);

		// Lay cac diem can ve cho san pham 1
      	List<Point> graphPoints1 = new ArrayList<Point>();
      	for (int i = 0; i < product1.size(); i++) 
      	{
      		int x1 = (int) (i * xScale + BORDER_GAP);
      		int y1 = (int) ((MAX_SCORE - product1.get(i)) * yScale + BORDER_GAP);
      		graphPoints1.add(new Point(x1, y1));
      	}
      	
     // Lay cac diem can ve cho san pham 2
      	List<Point> graphPoints2 = new ArrayList<Point>();
      	for (int i = 0; i < product2.size(); i++) 
      	{
      		int x1 = (int) (i * xScale + BORDER_GAP);
      		int y1 = (int) ((MAX_SCORE - product2.get(i)) * yScale + BORDER_GAP);
      		graphPoints2.add(new Point(x1, y1));
      	}

      	// Ve hai truc x, y 
      	g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
      	g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

      	// Tao cac nhan cua truc tuong ung voi che do xem (theo chieu doc)
      	if (mode == true)
      	{
      		JLabel donViY = new JLabel("(sản phẩm)");
      		donViY.setBounds(0, 0, 100, 20);
      		this.add(donViY);
      		
      		JLabel donViX = new JLabel("(ngày)");
      		donViX.setBounds(getWidth() - 2*BORDER_GAP, getHeight() - BORDER_GAP * 2, 100, 20);
      		this.add(donViX);
      	}
      	else
      	{
      		JLabel donViY = new JLabel("(x100 sản phẩm)");
      		donViY.setBounds(0, 0, 130, 20);
      		this.add(donViY);
      		
      		JLabel donViX = new JLabel("(tháng)");
      		donViX.setBounds(getWidth() - 2*BORDER_GAP, getHeight() - BORDER_GAP * 2, 100, 20);
      		this.add(donViX);
      	}
      	// Tao nhan va ve cac gach the hien diem gia tri
  		for (int i = 0; i < Y_POINT; i++) 
      	{
  			if (mode == false)
  			{
  				JLabel label = new JLabel("" + (MAX_SCORE * 1.0 /(Y_POINT*100)) * i);// tao nhan theo truc y
  				label.setBounds(0, BORDER_GAP + (Y_POINT - i) * ((PREF_H - 2 * BORDER_GAP) / Y_POINT) - 5, BORDER_GAP, 10);
  	      		this.add(label);
  			}
  			else
  			{
  				JLabel label = new JLabel("" + (MAX_SCORE/Y_POINT) * i);// tao nhan theo truc y
  				label.setBounds(0, BORDER_GAP + (Y_POINT - i) * ((PREF_H - 2 * BORDER_GAP) / Y_POINT) - 5, BORDER_GAP, 10);
  	      		this.add(label);
  			}
      		int x0 = BORDER_GAP;
      		int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
      		int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_POINT + BORDER_GAP);
      		int y1 = y0;
      		g2.drawLine(x0, y0, x1, y1);
      	}

  	// Tao cac nhan cua truc tuong ung voi che do xem (theo chieu doc)
      	for (int i = 0; i < product1.size(); i++) 
      	{
      		JLabel label = new JLabel("" + (i + 1));
      		label.setBounds(i * (getWidth() - BORDER_GAP * 2) / (product1.size() - 1) + BORDER_GAP - 5, getHeight() - BORDER_GAP/2, BORDER_GAP, 10);
      		this.add(label);
      		int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (product1.size() - 1) + BORDER_GAP;
      		int x1 = x0;
      		int y0 = getHeight() - BORDER_GAP;
      		int y1 = y0 - GRAPH_POINT_WIDTH;
      		g2.drawLine(x0, y0, x1, y1);
      	}
      	
      	// ve do thi cho san pham 1
      	Stroke oldStroke = g2.getStroke();
      	g2.setColor(GRAPH_COLOR);
      	g2.setStroke(GRAPH_STROKE);
      	for (int i = 0; i < graphPoints1.size() - 1; i++) 
      	{
      		int x1 = graphPoints1.get(i).x;
      		int y1 = graphPoints1.get(i).y;
      		int x2 = graphPoints1.get(i + 1).x;
      		int y2 = graphPoints1.get(i + 1).y;
      		g2.drawLine(x1, y1, x2, y2);
      	}

      	g2.setStroke(oldStroke);      
      	g2.setColor(GRAPH_POINT_COLOR);
      	for (int i = 0; i < graphPoints1.size(); i++) 
      	{
      		int x = graphPoints1.get(i).x - GRAPH_POINT_WIDTH / 2;
      		int y = graphPoints1.get(i).y - GRAPH_POINT_WIDTH / 2;;
      		int ovalW = GRAPH_POINT_WIDTH;
      		int ovalH = GRAPH_POINT_WIDTH;
         	g2.fillOval(x, y, ovalW, ovalH);
      	}
      	
      	// ve do thi cho san pham 2
      	g2.setColor(Color.BLUE);
      	g2.setStroke(GRAPH_STROKE);
      	for (int i = 0; i < graphPoints2.size() - 1; i++) 
      	{
      		int x1 = graphPoints2.get(i).x;
      		int y1 = graphPoints2.get(i).y;
      		int x2 = graphPoints2.get(i + 1).x;
      		int y2 = graphPoints2.get(i + 1).y;
      		g2.drawLine(x1, y1, x2, y2);
      	}

      	g2.setStroke(oldStroke);      
      	g2.setColor(GRAPH_POINT_COLOR);
      	for (int i = 0; i < graphPoints2.size(); i++) 
      	{
      		int x = graphPoints2.get(i).x - GRAPH_POINT_WIDTH / 2;
      		int y = graphPoints2.get(i).y - GRAPH_POINT_WIDTH / 2;;
      		int ovalW = GRAPH_POINT_WIDTH;
      		int ovalH = GRAPH_POINT_WIDTH;
         	g2.fillOval(x, y, ovalW, ovalH);
      	}
	}

	// Ham tra ve mot Dimension tu chieu rong va chieu cao
	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(PREF_W, PREF_H);
	}
}
