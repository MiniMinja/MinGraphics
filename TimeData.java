import java.util.*;
public class TimeData{
	private Day[] days;
	private static String[] dayString = 
		{"Sun","Mon","Tues","Wed","Thurs","Fri","Sat"};
	public TimeData(){
		days = new Day[7];
		for(int i =0;i<7;i++)
			days[i] = new Day(dayString[i]);
	}
	public void addToDays(String days, String times){
		Duration d = readRaw(times);
		int[] timesInInt = parseDays(days);
		for(int i =0 ;i<timesInInt.length;i++){
			this.days[timesInInt[i]].insertTime(d);
		}
	}
	public int[] parseDays(String d){
		if(d.indexOf("-")!=-1){
			int start = -1;
			String[] days = d.split("-");
			if(days[0].startsWith("M")) start = 1;
			else if(days[0].startsWith("Tu")) start = 2;
			else if(days[0].startsWith("W")) start = 3;
			else if(days[0].startsWith("Th")) start = 4;
			else if(days[0].startsWith("F")) start = 5;
			else if(days[0].startsWith("Sa")) start = 6;
			else if(days[0].startsWith("Su")) start = 0;
			
			int end = -1;
			if(days[1].startsWith("M")) end = 1;
			else if(days[1].startsWith("Tu")) end = 2;
			else if(days[1].startsWith("W")) end = 3;
			else if(days[1].startsWith("Th")) end = 4;
			else if(days[1].startsWith("F")) end = 5;
			else if(days[1].startsWith("Sa")) end = 6;
			else if(days[1].startsWith("Su")) end = 0;
			
			int[] ret = new int[end - start + 1];
			for(int i =0;i<ret.length;i++)
				ret[i] = start + i;
				
			return ret;
		}
		else if(d.indexOf(",")!=-1){
			String[] days = d.split(",");	
			int[] ret = new int[days.length];
			for(int i = 0;i<days.length;i++){
				if(days[i].startsWith("M")) ret[i] = 1;
				else if(days[i].startsWith("Tu")) ret[i] = 2;
				else if(days[i].startsWith("W")) ret[i] = 3;
				else if(days[i].startsWith("Th")) ret[i] = 4;
				else if(days[i].startsWith("F")) ret[i] = 5;
				else if(days[i].startsWith("Sa")) ret[i] = 6;
				else if(days[i].startsWith("Su")) ret[i] = 0;
			}
			return ret;		
		}
		else {
			int[] ret = new int[1];
			if(d.startsWith("M")) ret[0] = 1;
			else if(d.startsWith("Tu")) ret[0] = 2;
			else if(d.startsWith("W")) ret[0] = 3;
			else if(d.startsWith("Th")) ret[0] = 4;
			else if(d.startsWith("F")) ret[0] = 5;
			else if(d.startsWith("Sa")) ret[0] = 6;
			else if(d.startsWith("Su")) ret[0] = 0;
			return ret;
		}	
	}
		
	public Duration readRaw(String raw){
		String[] temp = null;
		if(raw.indexOf(" ") != -1){
			temp = raw.split(" ");
			raw = temp[0];
		}
		int indexOfDash = raw.indexOf("-");
		String st = raw.substring(0,indexOfDash);
		if(st.indexOf(":") == -1) st+= ":00";
		int start = timeToInt(st+raw.substring(raw.length()-2).toUpperCase());
		String et = raw.substring(indexOfDash+1,raw.length()-2);
		if(et.indexOf(":") == -1) et+= ":00";
		int end = timeToInt(et+raw.substring(raw.length()-2).toUpperCase());
		if(temp != null) 
			return new Duration(start,end,temp[1]);
		else 
			return new Duration(start,end);
	}
	/**
	 * returns an int that converts like so:
	 *  - 12:00AM is 0
	 *  - 12:01AM is 1
	 *  - 1:00AM is 60
	 *  - 12:00PM is 720 ( 60 min in an hour, 12 hours)
	 *  - 11:59PM is 1439 ( 60 min in an hour, 23 hours and 59 min)
	 */
	public int timeToInt(String t){
		String[] hAM = t.substring(0,t.length()-2).split(":");
		int h = Integer.parseInt(hAM[0]);
		int m = Integer.parseInt(hAM[1]);
		boolean isAM = t.endsWith("AM");
		if(h==12) h-= 12;
		if(!isAM) h+= 12;
		return h * 60 + m;
	}
	public String intToTime(int time){
		String aorp = (time >= 12 * 60) ? "PM":"AM";
		String h ="" + ( time / 60 % 12);
		if(h.equals("0")) h = "12";
		String m = "" + (time %60);
		if(m.length() < 2) m = "0" + m;
		return h+":"+m+aorp;
	}
	public String toString(){
		String ret = "";
		for(Day d: days){
			ret += d.toString() + "\n";
			ArrayList<Duration> times = d.getTimes();
			for(Duration du: times){
				ret += "     "+du.toString();
			}
			ret += "\n";
		}
		return ret;
	}
	public static void main(String[] args){
		TimeData td = new TimeData();
		/*
			for(int i =0;i<=24;i++){
				int h = i % 12;
				if(h == 0) h+= 12;
				System.out.println("The time is: "+ h+":00PM"+
					" Which is: "+ td.timeToInt(h+":00PM"));
			}
		*/


	}
	private class Duration{
		private int s;
		private int e;
		private String task;
		Duration(int s, int e){
			this.s = s;
			this.e = e;
			task = "Unknown";
		}
		Duration(int s, int e,String t){
			this.s = s;
			this.e = e;
			task = t; 
		}
		
		int getStart () { return s;}
		int getEnd () {return e;}
		public String toString(){
			return task + " from " + 
				intToTime(s) 
				+ " to " + 
				intToTime(e);
		}
	}
	private class Day{
		private ArrayList<Duration> dayTimes;
		private String day;
		Day(String d){
			dayTimes = new ArrayList<Duration>();
			day = d;
		}
		public void insertTime(Duration d){
			dayTimes.add(d);
		}
		public ArrayList<Duration> getTimes(){ return dayTimes; }
		public String toString(){
			return "On " + day +":";
		}
	}
}
