package DoAn;

import java.util.ArrayList;
import java.util.Date;

public class Data 
{
	Date date;
	int numOfProduct1;
	int numOfProduct2;
	
	public Data()
	{
		
	}
	
	public Data(Date date, int numOfProduct1, int numOfProduct2) {
		super();
		this.date = date;
		this.numOfProduct1 = numOfProduct1;
		this.numOfProduct2 = numOfProduct2;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumOfProduct1() {
		return numOfProduct1;
	}

	public void setNumOfProduct1(int numOfProduct1) {
		this.numOfProduct1 = numOfProduct1;
	}

	public int getNumOfProduct2() {
		return numOfProduct2;
	}

	public void setNumOfProduct2(int numOfProduct2) {
		this.numOfProduct2 = numOfProduct2;
	}
}
