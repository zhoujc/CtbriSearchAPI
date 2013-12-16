package com.ctbri.srcapi.tool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParserReviews {

	public static void main(String[] args) {
//		String test = new String("hanwang����1984#$.�˱Ƚ϶࣬Ԥ�����ǿ������ȣ��ܺá����񲻴��Ƚ����Ի�������������ɫ #$2013-04-18 10:41:35#$1$%��ͳ����#$�Ź�ȥ���˾��Ƕ࣬Ҫ��λ�������Ŵ��һ��ȣ���һ�����϶��͸������ˣ���Ʒ�������������Ǻ�һ�㣬�϶������Ź�������ġ�#$2013-01-26 17:19:46$%��ͳ����#$���Ʒ����ˣ���ϲ����ͭ������ĺܴ���β�������ģ����Զ�㣬С�ϲ�����ǮҲ������˰ɣ����ε���û̫�Ա����ۿۺ�ÿ��һ�ٳ�ͷ����������ֵ���������´λ���ȥ�ĵط�#$2013-01-25 10:44:40$%hanwang����1984#$�������������ԣ�ֻ����λ��Щ�������ζ���ܺã��齴Ҳ�����ܵص����ϱ���ͭ�����⡣#$2013-01-17 15:36:51$%��ͳ����#$�Ź��ľ����˶࣬�����һ�㣬�����е��ң��ϲ��ٶ��е������о�һ��#$2013-01-10 16:28:07$%hanwang����1984#$.�˱Ƚ϶࣬Ԥ�����ǿ������ȣ��ܺá�����");
		String test = new String("����#$��һ������.����춪����.û�˹�.ǧ��ҪС��.���ﲻ��ȫ��.ҪС���Լ��Ķ���#$2013-01-23 16:25:01#$1$%lily1201#$�������ҳԣ��⼸�������֤���������𡣡�����һ�е��ķ��귢չ���Ըߵ��ľƵꡣ��ƷҲ��Խ��Խ���¡��ӵͼ۵ĵ��ߵ��Ķ��ǳ�ȫ��ζ�������ȼ����Ǽ��Ƶ�Ĳ���ܻ�����ѷɫһЩ����������Ҫ�ᵽ�µĴ�¥�ˣ�Ӧ�û���һ��̨�ס�#$2012-07-08 16:57:20#$1$%lilyarlen#$���������ְ�ǿ�ţ�˵�����ν������ڳԵ��ˡ�ʨ��ͷ����Ͷ������������������������ɵ��ˡ� �и���֭«��ʲô�ĺܲ��� �����������಻�� Ҳ���ǳ����ˣ�����Сʱ����ô�������ˡ��౦���е��ϡ� �������� �ǳ��óԣ����ĸ����óԡ��� ͣ��Ҫ���磬��ĩһ�㶼��ϯ�����ϲ��ٶȻ��ǿ��Եġ�����Ҳû��˵��#$2012-06-23 18:07:17#$1$%С������#$��Ȼ�Ƶ�λ�ñȽϺã����Ƿ��䲻��~ɳ��Ҳ���������Ҳխ���������Ǹ��ƹ������ĺ�Ư�������Է��Ļ�����һ������˵���ǣ�ÿ����ͬ�������µĺóԵ�~��������ȥ��ʱ��������3¥�����л��磬���°�����ϲ��ٶ�ֱ�Ӽ����������������Ҫ�Ľ�#$2012-06-21 15:38:58#$1$%ldh_2001#$���˰ﶩ�ģ�ס�����죬Ҳ��֪���Ǽ��Ǽ��ġ�����Ƚ�С�����ǵķ����ǳ����ģ��ر��䡣���е���ʩ���Ƕ��У�����������ĵ����е�ģ����#$2012-06-20 18:36:44#$1");
		
		Date date = new Date(0);
//		String test2 = new String("hanwang����1984#$.�˱Ƚ϶࣬Ԥ�����ǿ������ȣ��ܺá����񲻴��Ƚ����Ի�������������ɫ #$2013-04-18 10:41:35#$1");

//		System.out.println(parseReview(test2).toString());
		System.out.println(parse(test).get(0).source);
	}
	
	public static List<Review> parse(String input){
		if(!input.contains("#$") && !input.contains("$%"))
			return null;
		ArrayList<Review> reviews= new ArrayList<Review>();
		ArrayList<String> records= new ArrayList<String>();
		while(input.contains("$%")){
			String  newOne =input.substring(0,input.indexOf("$%"));
			records.add(newOne);
			input = input.substring(input.indexOf("$%")+2,input.length());
		}
		records.add(input);
		for(String record:records){
			if(record.contains("#$"))
				reviews.add(parseReview(record));
		}
		return reviews;
	}
	
	public static Review parseReview(String input){
		Review review =new Review();
		try{
			review.username = input.substring(0,input.indexOf("#$"));
			input = input.substring(input.indexOf("#$")+2,input.length());
			if(input.contains("#$")){
				review.comment = input.substring(0,input.indexOf("#$"));
				input = input.substring(input.indexOf("#$")+2,input.length());
				if(input.contains("#$")){
					review.time = input.substring(0,input.indexOf("#$"));
					input = input.substring(input.indexOf("#$")+2,input.length());
					review.source = input;
				}else {
					review.time = input;
				}
			}
			else {
				review.comment = input;
			}
			
		}catch(Exception e){
		}
		return review;
	}}
