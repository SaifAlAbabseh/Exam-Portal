package examportal;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Timer extends Thread{
	
	JLabel hour, minute, second;
	
	
	public Timer(JLabel hour, JLabel minute, JLabel second) {
		this.hour=hour;
		this.minute=minute;
		this.second=second;
	}
	
	private String getDuration() {
		/*int ttemp=(60-minuteInt)+m;
		int tttemp=h-hourInt;
		if(tttemp>1) {
			tttemp--;
			
		}
		else {
			
		}*/
		
		int d=StartExam.duration;
		
		
		int hourInt = new GregorianCalendar().get(Calendar.HOUR_OF_DAY);
		if(hourInt==0) {
			hourInt=24;
		}
		int minuteInt = new GregorianCalendar().get(Calendar.MINUTE);
		
		
		int secondInt = new GregorianCalendar().get(Calendar.SECOND);
		
		
		
		
		int temp=d+StartExam.minute;
		
		int hoursMore=temp/60;
		int minutesMore=temp%60;
		
		int h=hoursMore+StartExam.hour;
		int m=minutesMore;
		
		
		
		if(h>24) {
			hourInt = hourInt + StartExam.hour;
		}
		
		if(hourInt==StartExam.hour && minuteInt>=StartExam.minute) {
			d-=(minuteInt-StartExam.minute);
		}
		else if(hourInt==h && minuteInt<m) {
			d=(m-minuteInt);
		}
		else if(hourInt>StartExam.hour && hourInt<h) {
			int hl,ml;
			if(m>=minuteInt) {
				hl=h-hourInt;
				ml=m-minuteInt;
			}
			else {
				hl=(h-hourInt)-1;
				int mml=60-minuteInt;
				ml=mml+m;
			}
			d=(hl*60)+ml;
		}
		
		
		if(secondInt>0) {
			d--;
			secondInt = 60 - secondInt;
		}
		
		return ""+d+"."+secondInt;
	}
	@Override
	public void run() {
		String result = getDuration();
		String resArr[] = result.split("\\.");
		int duration = Integer.parseInt(resArr[0]);
		int h=duration/60;
		int m=duration%60;
		int s=Integer.parseInt(resArr[1]);
		
		
		while(h>0 || m>0 || s>0) {
			if(h>=10) {
				hour.setText(""+h);
			}
			else {
				hour.setText("0"+h);
			}
			if(m>=10) {
				minute.setText(""+m);
			}
			else {
				minute.setText("0"+m);
			}
			if(s>=10) {
				second.setText(""+s);
			}
			else {
				second.setText("0"+s);
			}
			
			if(m==0) {
				if(h!=0) {
					h--;
					m=60;
				}
			}
			
			if(s==0) {
				if(m!=0) {
					m--;
					s=60;
				}
			}
			else {
				s--;
			}
			
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Unknown Error");
			}
		}
		System.exit(0);
	}
}
