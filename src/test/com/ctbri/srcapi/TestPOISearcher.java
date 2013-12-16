package test.com.ctbri.srcapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.lucene.search.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.ctbri.srcapi.POISearcher;
import com.ctbri.srcapi.SearchResult;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.pojo.MapPoint;
import com.ctbri.srhcore.pojo.POIObject;



public class TestPOISearcher {

	@Test
	public void testById() {
//		fail("Not yet implemented");
		POIObject o = POISearcher.searchById("925100702");
		System.out.println(" "
//				+" cid:"+o.getFieldValue("cid")
				+" "+ o.getFieldValue("nid")
				+"  name:"+o.getName()
//				+" trace:"+o.getFieldValue(U.Field_PP_TRACE)
				
				+"  score:"+o.getScore()
				+"  con:"+o.getFieldValue(U.Field_confidenceLevel)
				+"  price:"+o.getFieldValue("price93")
				+"  from:"+o.getFieldValue("from")
				+"  district:"+o.getFieldValue("district")
				+"  dis:"+o.getDistance()
				+"  add:"+o.getAddr()
				
				+"  [type:"+o.getType()
				+"]  tag:"+o.getTag()
				+"  brand:"+o.getBrand()
				+" des "+ o.getFieldValue("des")
				+"  rank :"+o.getRank()
//				+"  photo:"+o.getSinglePhoto()
				+" ,"+o.getLon()+","+ o.getLat()
//				+" custom: "+o.getCustomer()
				);			
	}
	
	//http://localhost:8080/search/getNearPoiByLatLon.jsp?city=北京市&lon=116.46644&lat=39.94167&querykey=电影院&poiType=&range=1500&pn=1&rn=
	//http://localhost:8080/search/getPoiByKeyword.jsp?city=北京市&querykey=图&poiType=&pn=1&rn=&otherquery=&encode=utf-8
	//http://localhost:8080/search/getNearPoiByLatLon.jsp?city=北京市&lon=116.46644&lat=39.94167&querykey=电影院&poiType=&range=2000&pn=1&rn=&encode=utf-8
	//http://10.9.56.83:8080/search/getNearPoiByLatLon.jsp?city=北京市&lon=116.46644&lat=39.94167&querykey=电影院&poiType=&range=2000&pn=1&rn=&encode=utf-8
	
	@Test
	public void testPOISearch()
	{
//		SearchResult sr = POISearcher.search("北京", "", "", "tag:01 tag:01", 1, 30, false, false);
		SearchResult sr = POISearcher.search("上海", "", "", "(+tag:02 +tag:0 +brand:imax +brand:地心)", 1, 100, false, false,0,0l,0l);
//		SearchResult sr = POISearcher.search("上海", "娄陆公路789", "", "", 1, 100,
//				true,true,false,true,true);
//		SearchResult sr = POISearcher.search("深圳", "粤潮汇", "", "", 1, 20,
//				true,true,false,true,true);
//		SearchResult sr = POISearcher.search("沈阳", "沈阳市于洪区邮政局", "", "", 1, 200,
//				true,true,false,false,true);
//		SearchResult sr = POISearcher.search("济南", "长清区人民医院", "", "", 1, 200,
//				true,true,false,true,true);
//		SearchResult sr = POISearcher.search("全国", "天安门", "", "", 1, 200,
//				true,true,false,false,true);
//		SearchResult sr = POISearcher.search("大兴安岭地区", "餐饮", "", "", 1, 300,
//				true,true,false,false,true);
//		SearchResult sr = POISearcher.search("武汉", "紫阳湖公园", "", "", 1, 300,
//				true,true,false,false,true);
//		SearchResult sr = POISearcher.search("上海", "延安中路839号", "", "", 1, 300,
//				true,true,false,false,true);
//		SearchResult sr = POISearcher.search("鍖椾含甯", "闃滄垚闂ㄥ澶ц2鍙?", "", "", 1, 50,
//				true,true,false,false,true);
//		SearchResult sr = POISearcher.search("北京", "体育场", "", "", 1, 100,
//				true,true,false,true,true);
//		SearchResult sr = POISearcher.search("八一", "广场", "", "", 1, 30,
//				false,false,true);
//		SearchResult sr = POISearcher.search("北京", "阜成门 餐饮", "", "-(title:红楼 title:校园)", 1, 50,
//				false,false,false,true);
//		SearchResult sr = POISearcher.search("全国", "朝阳区", "", "-(title:红楼 title:校园)", 1, 50,
//				false,false,false);
//		SearchResult sr = POISearcher.search("北京", "丽泽桥", strType, strQuery, 1, 100,userBuy.equals("true"),if_merge.equals("true"),false);
		System.out.println(sr.getTotalCount());
//		U.log.debug(sr.getPOIs().size());
//		
//		U.log.info("total: "+sr.getTotalCount());
//		U.log.debug(sr.getLq().getTotalNum());
		LocalQuery  localquery = sr.getLq();

//		U.log.debug(localquery.getCenterPoi().getLat()+"  =  "+ localquery.getCenterPoi().getLon());

		int count = 1;
		System.out.println("||"+sr.getQ_re());
		
		for (Iterator<POIObject> iterator = sr.getPOIs().iterator(); iterator.hasNext()&& count <=200;) {
			POIObject o = (POIObject) iterator.next();

			System.out.println((count++)+" "
//					+" cid:"+o.getFieldValue("cid")
					+" "+ o.getFieldValue("nid")
					+"  name:"+o.getName()
//					+" trace:"+o.getFieldValue(U.Field_PP_TRACE)
					+"  score:"+o.getScore()
					+"  rank :"+o.getRank()
					+"  aveCost "+ o.getFieldValue("avePerCost")
					+"  discount "+ o.getFieldValue("zcDiscount")+"|"+o.getFieldValue("ftDiscount")
					
					+"  con:"+o.getFieldValue(U.Field_confidenceLevel)
					+"  price:"+o.getFieldValue("price93")
					+"  from:"+o.getFieldValue("from")
					+"  district:"+o.getFieldValue("district")
					+"  dis:"+o.getDistance()
					+"  add:"+o.getAddr()
					
					+"  [type:"+o.getType()
					+"]  tag:"+o.getTag()
					+"  brand:"+o.getBrand()
					
					
//					+"  photo:"+o.getSinglePhoto()
					+" ,"+o.getLon()+","+ o.getLat()
//					+" custom: "+o.getCustomer()
					);			
		}
	}
	
	@Test
	public void testNearbySearch()
	{

//		116.38723,39.94389 116.38723,39.94389  116.38993,40.00821  116.41386,39.90874
//		116.38986,39.99093  116.38986,39.99093  116.36073,39.91562
//		double lon = 116.40523;
//		double lat = 39.91081; 
		long t1 = 0l;
		long t2 = 0l;
		long t3 = 0l;
		long start = System.currentTimeMillis();
		int times = 1;
		SearchResult sr = null;
		for (int i = 0; i < times; i++) {
			if(i == 1)
				start = System.currentTimeMillis();
//			sr = POISearcher.searchNearby("北京","如家", "", new MapPoint(116.45593,39.90762),20000, 
//					strQuery, 1, 200, false,"",true,false,true,7);
//			 sr = POISearcher.searchNearby("北京","", "", new MapPoint(116.34814,39.92177),1000, 
//			"", 1, 200, false,"",true,true,true,7);
//			sr = POISearcher.searchNearby("南昌市","休闲娱乐", "", new MapPoint(115.9441,28.73173),0, 
//					"", 1, 30, false,"",true,true,5);
//			sr = POISearcher.searchNearby("驻马店","火车站", "", new MapPoint(114.10847,32.49386),1000, 
//					"", 1, 30, false,"",true,true,5);//115.9441,28.73173
//			 sr = POISearcher.searchNearby("南昌","休闲娱乐", "", new MapPoint(115.9441,28.73173),1500, 
//						"", 1, 30, false,"",true,true,5);
//			 sr = POISearcher.searchNearby("南昌","休闲娱乐", "", new MapPoint(115.9441,28.73173),1500, 
//						"", 1, 30, false,"",true,true,5);
//			 sr = POISearcher.searchNearby("北京","医院", "", new MapPoint(116.42465,39.90366),1500, 
//						"", 1, 30, false,"",true,true,5);
			 //116.48831179,39.93361317 观湖国际  116.49515,39.93807
//			 sr = POISearcher.searchNearby("北京","酒店", "", new MapPoint(116.46644,39.94167),1000, 
//						"", 1, 200, false,"",true,false,true,true,5);
//			sr = POISearcher.searchNearby("北京市", "酒吧", "", new MapPoint(116.36660013,39.90937059), 0, "",
//					0, 50, true, "", true,true,false,false, 5);
			sr = POISearcher.searchNearby("", "" ,"",new MapPoint(116.36784,39.94015),5000,  "" ,1,100, false,0);

//			 sr = POISearcher.searchNearby("上海","历史", "", new MapPoint(121.40807,31.20903),1000, 
//						"", 1, 10, false,"",false,false,false,5);
//			 sr = POISearcher.searchNearby("济南","银行", "", new MapPoint( 116.74901728,36.55235491),0, 
//						"", 1, 100, false,"",false,true,true,5);
			 
//			 sr = POISearcher.searchNearby("北京","", "", new MapPoint(116.34814,39.92177),1000, 
//						"", 1, 200, false,"",true,false,7);
//			sr = POISearcher.searchNearby("北京","foobar", "", new MapPoint(116.41116,39.9828),500, 
//					strQuery, 1, 50, false,"",true,false,true,7);
			
			if(i >0)
			{
				t1 += POISearcher.analytime;
				t2 += POISearcher.retrivetime;
				t3 += POISearcher.resorttime;
			}
		}
		System.out.println(System.currentTimeMillis()-start);
		System.out.println("[time1]" + (t1/(times-1>0?times-1:1)) + "[time2]" + (t2/(times-1>0?times-1:1)) + "[time3]" + (t3/(times-1>0?times-1:1)));
		LocalQuery  localquery = sr.getLq();
//		U.log.debug(localquery.centerName+" |============");
		Query query = localquery.toQuery();
		System.out.println("nTotalPoiCount "+ sr.getTotalCount());
		int count = 1;
		System.out.println("||"+query.toString());
				for (Iterator iterator = sr.getPOIs().iterator(); iterator.hasNext();) {
			POIObject o = (POIObject) iterator.next();
			System.out.println((count++)+" "+o.getFieldValue("nid")
					+ "  name:"+o.getName()
					+ "  dis:"+o.getDistance()
					+"  score:"+o.getScore()
					+ "  type:"+o.getType()
					
					+"  price:"+o.getFieldValue("price")
					+"  from:"+o.getFieldValue("from")
//					+"  add:"+o.getAddr()
					+ "  conf:"+o.getFieldValue(U.Field_confidenceLevel)
					+ "  dir:"+o.getDirection()
					+ "  tag:"+o.getTag()
					+ "  invol:"+o.getFieldValue("invol")

					+ "  lonlat:" + o.getLon()+","+ o.getLat()
					+ "  dis:"+ o.getDistance() 
//					+ "  addscore:"+ o.getAddressScore() 
			
					);		
			}
	}
	
	@Test
	public void testJson()
	{
		File testfile = new File("test.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(testfile)));
			String content = br.readLine();
			JSONObject jo = new JSONObject(content);
			System.out.println(jo.get("city"));
			JSONObject pois = jo.getJSONObject("pois");
			JSONArray arr = pois.getJSONArray("result");
			System.out.println(arr.length());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
