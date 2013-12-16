package com.ctbri.srcapi.tool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParserReviews {

	public static void main(String[] args) {
//		String test = new String("hanwang王涵1984#$.人比较多，预订还是可以优先，很好。服务不错，比较人性化。手切肉是特色 #$2013-04-18 10:41:35#$1$%饭统饭友#$团购去的人就是多，要等位，还跟着大家一起等，人一多服务肯定就跟不上了，菜品里边肉的质量可是很一般，肯定零点和团购有区别的。#$2013-01-26 17:19:46$%饭统饭友#$老牌饭店了，很喜欢大铜锅，真的很大，羊尾很香很香的，可以多点，小料不错，价钱也不算便宜吧，尴尬的是没太吃饱，折扣后每人一百出头，但物有所值，会让人下次还想去的地方#$2013-01-25 10:44:40$%hanwang王涵1984#$总体来讲还可以，只是座位有些挤。肉的味道很好，麻酱也不错。很地道的老北京铜锅涮肉。#$2013-01-17 15:36:51$%饭统饭友#$团购的就是人多，服务很一般，环境有点乱，上菜速度有点慢，感觉一般#$2013-01-10 16:28:07$%hanwang王涵1984#$.人比较多，预订还是可以优先，很好。服务不");
		String test = new String("今年#$就一垃圾店.大白天丢东西.没人管.千万要小心.那里不安全啊.要小心自己的东西#$2013-01-23 16:25:01#$1$%lily1201#$总在他家吃，这几年基本见证了他的崛起。。。从一中当的饭店发展成以高档的酒店。菜品也是越做越精致。从低价的到高档的都非常全。味道绝不比几家星级酒店的差，可能环境略逊色一些。不过马上要搬到新的大楼了，应该会上一大台阶。#$2012-07-08 16:57:20#$1$%lilyarlen#$开锅豆腐爸爸强排，说过几次今天终于吃到了。狮子头大的油豆腐，汤很鲜美，两锅都被干掉了。 有个鸡汁芦笋什么的很不错。 凉菜里土豆泥不错。 也许是长大了，不像小时候那么爱吃鱼了。多宝鱼有点老。 抄拨烂子 非常好吃，比哪个都好吃。。 停车要趁早，周末一般都有席，但上菜速度还是可以的。服务也没的说。#$2012-06-23 18:07:17#$1$%小狗嘻嘻#$虽然酒店位置比较好，但是房间不大~沙发也不舒服，床也窄……房间那个酒柜倒是做的很漂亮……吃饭的话，用一伯伯话说就是，每次来同利都有新的好吃的~不过今天去的时候正赶上3楼大厅有婚宴，导致包间的上菜速度直接减慢……觉得这个需要改进#$2012-06-21 15:38:58#$1$%ldh_2001#$别人帮订的，住了两天，也不知道是几星级的。房间比较小，我们的房间是朝北的，特别冷。该有的设施倒是都有，不过房间里的电视有点模糊。#$2012-06-20 18:36:44#$1");
		
		Date date = new Date(0);
//		String test2 = new String("hanwang王涵1984#$.人比较多，预订还是可以优先，很好。服务不错，比较人性化。手切肉是特色 #$2013-04-18 10:41:35#$1");

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
