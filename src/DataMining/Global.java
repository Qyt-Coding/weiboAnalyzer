package DataMining;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



//全局变量

public final class Global {
	public static String access_token = "2.00QT9bhHp5zdPC00456d84f6px7AxB";
	public static String screen_name = "mylove25168";
	public static String uIDString = "6486005242";
	public static int docCount = 0;
	public static int ad = 1;
	public static int unad = 0;
	public static String[] noMeanStrings ={"此微博已被",
											"[0-9a-zA-z]*"};
	public static String[] noMeanLex={"[0-9a-zA-z.:/]*",
										"我",
										"你",
										"他",
										"不是",
										"啊",
										"要",
										"噗噗",
										"最",
										"之",
										"有",
										"哦",
										"@"
										};
	public static int noMeanNum = 2;
	public static int noMeanLexNum = 13;
	public static List<String> usedIDList = new ArrayList<String>();
	public static DBHander dBer = new DBHander();
	public static PeopleQueue queue = new PeopleQueue();
	public static splitSystem split = new splitSystem();
	public static List<Doc> docList = new LinkedList<Doc>();
	
	public static boolean markNolex(String s){
		for(int i =0; i< noMeanLexNum;i++){
			if(s.matches(noMeanLex[i])){
				return true;
			}

		}
		return false;
	}
	/**
	 * 这里主要是分类用的   一般这里不起作用
	 * @param s
	 * @return
	 */
	public static boolean markNoMean(String s){
		if(s.contains(noMeanStrings[0]))
			return true;
		if(s.length() < 6)
			return true;
		for(int i =0; i< noMeanNum;i++){
			if(s.matches(noMeanStrings[i])){
				System.out.println("no Mean:");
				return true;
			}

		}
		return false;
	}
}
