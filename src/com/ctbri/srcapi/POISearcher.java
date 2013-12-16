package com.ctbri.srcapi;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.ana.analysis.LocalAnalyzer;
import com.ctbri.srhcore.pojo.MapPoint;
import com.ctbri.srhcore.pojo.POIObject;
import com.ctbri.srhcore.prc.Resort;
import com.ctbri.srhcore.prc.util.USort;
import com.ctbri.srhcore.search.Retriever;
import com.ctbri.srhcore.search.util.UIndex;
import com.ctbri.srhcore.util.ChineseCoding;




public class POISearcher {
	
	public static long analytime = 0l;
	public static long retrivetime = 0l;
	public static long resorttime = 0l;
	
	public static POIObject searchById(String id)
	{
		File storeDir = new File(C.Index_Store_Path);
		IndexSearcher fs = UIndex.getIndexSearcher(storeDir.listFiles());
		TermQuery q = new TermQuery(new Term(U.Field_nid, id));
		try {
			TopDocs tds = fs.search(q, 1);
			ScoreDoc[] sd=tds.scoreDocs;
			if(sd.length <1) return null;
			if(sd.length >1)
			{
				System.out.println(id +" has Wrong data");
			}
			POIObject poi = new POIObject();
			for (int i = 0; i < sd.length; i++) {
				Document doc = fs.doc(sd[i].doc);
//				POIObject poi = new POIObject();
				poi.setLDoc(doc);
				break;

			}
			System.out.println("     "+ poi.getName());
			return poi;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static SearchResult search(String strCity, String strQuery, String strType,String strOtherQuery, 
			int start, int end, boolean sortbyprice,boolean userbuy)
	{
		return search( strCity,  strQuery,  strType, strOtherQuery, 
				 start,  end,  sortbyprice, userbuy, 0,0l,0l);
	}
	
	/**
	 * @param strCity 城市
	 * @param strQuery 查询
	 * @param strType 类型
	 * @param strOtherQuery 其他语句 目前的lucene4好像有queryparser有bug，必须小心
	 * @param start 开始
	 * @param end 结束
	 * @param sortbyprice 是否按照价格排序
	 * @param userbuy 使用加载用户购买点
	 * @return 排序的方式 1距离 推荐度 折扣 消费
	 */
	public static SearchResult search(String strCity, String strQuery, String strType,String strOtherQuery, 
			int start, int end, boolean sortbyprice,boolean userbuy,int sortby,double lon,double lat){
		
		long startTime = System.currentTimeMillis();
		String tempQuery = ChineseCoding.DBC2SBC(ChineseCoding.TraditionalToSimplify(strQuery)).toLowerCase();
		SearchResult sr = new SearchResult();
		LocalAnalyzer ana = new LocalAnalyzer();
		if(strCity != null && (strCity.endsWith("省")||strCity.endsWith("自治区")/*|| strCity.endsWith("行政区")*/))
		{
			strCity = "全国";
		}
		LocalQuery lq = ana.parse(strCity+" "+strQuery);
		
		Retriever tr = new Retriever(lq);
		tr.POISearch(start, end, C.Search_total_POI, false, strType, strOtherQuery,  userbuy);
		
		Resort rst = new Resort(lq);
		if(sortbyprice)
		{
		
			Collections.sort(lq.getPoiResult(), new Comparator<POIObject>() {

				@Override
				public int compare(POIObject o1, POIObject o2) {
					// TODO Auto-generated method stub
					String id1_source = o1.getFieldValue("from");
					String id2_source = o2.getFieldValue("from");
					String price1 = o1.getFieldValue("price");
					String price2 = o2.getFieldValue("price");
					if(id1_source.trim().length() >0 && id2_source.trim().length() == 0)
					{
						return -1;		
					}					
					if(id2_source.trim().length() >0 && id1_source.trim().length() == 0)
					{
						return 1;		
					}
					if(id1_source.trim().length() == 0 && id2_source.trim().length() == 0)
						return 0;
					float price_1 = 0;
					float price_2 = 0;
					
					try {
						price_1 = Float.parseFloat(price1);
						if(price_1<1) price_1 =100f;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						price_1 = 100f;
					}
					try {
						price_2 = Float.parseFloat(price2);
						if(price_2<1) price_2 =100f;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						price_2 = 100f;

					}
					return Float.compare(price_1, price_2);
				}
		    		
			});
		}
		//////////////////////////
		LinkedList<POIObject> point = lq.getPoiResult();
		if(sortby == 0 && lon>0 && lat>0)
		{
			LinkedList<POIObject> source = new LinkedList<POIObject>();
			for (int i = 0; i < point.size(); i++) {
				POIObject p = point.get(i);
				double dis = USort.getDistance(p.getLat(), p.getLon(), lat, lon);
				p.setDistance(dis);				
				source.add(p);
			}
			Collections.sort(source, new Comparator<POIObject> ()
					{
						public int compare(POIObject p1, POIObject p2) {
							// TODO Auto-generated method stub
							double dis1 = p1.getDistance();
							double dis2 = p2.getDistance();
							if(dis1-dis2>0)
								return 1;
							else 
								return -1;					
						}					
					}
					);
			point = source;
		}
		if(sortby ==1)
		{
			point = USort.sortByDiscount(lq.getPoiResult());
		}
		else if(sortby ==2)
			point = USort.sortByRank(lq.getPoiResult());
		else if(sortby == 3)
		{
//			point = USort.sortByDiscount(lq.getPoiResult());
//			point = USort.sortByRank(point);
			point = USort.sortByAvePerCost(lq.getPoiResult());
		}
		lq.setPoiResult(point);
		//////////////////////////////////////
		
		LinkedList<POIObject> tempall = new LinkedList<POIObject>();
		if(lq.getPoiResult().size()>0)
		{
			tempall.addAll(lq.getPoiResult());
		}
		
		start = Math.max(1, start);
		end = Math.min(end, tempall.size());
		if(start-1 < end)
			sr.getPOIs().addAll(tempall.subList(start-1, end));
		sr.setTotalCount(lq.getTotalResult());
		sr.setLq(lq);
		
		long endTime = System.currentTimeMillis();
		sr.setTime(endTime-startTime);
		return sr;
	}
	public static SearchResult searchNearby(String strCity, String strQuery, String strType, MapPoint point, int range, 
			String strOtherQuery, int start, int end,boolean sortbyprice)
	{
		return searchNearby( strCity,  strQuery,  strType,  point,  range, 
				 strOtherQuery,  start,  end, sortbyprice,0);
	}
	
	public static SearchResult searchNearby(String strCity, String strQuery, String strType, MapPoint point, int range, 
			String strOtherQuery, int start, int end,boolean sortbyprice,int sortby) 
	{
		long startTime = System.currentTimeMillis();
		SearchResult sr = new SearchResult();
		LocalAnalyzer ana = new LocalAnalyzer();
		LocalQuery lq = ana.parse(strCity+" "+strQuery);
		String tempQuery = ChineseCoding.DBC2SBC(ChineseCoding.TraditionalToSimplify(strQuery)).toLowerCase();
		
		Retriever tr = new Retriever(lq);	
		tr.NearbySearch(point, range, start, end, C.Search_total_Nearby, "", strType,strOtherQuery,true);
//		U.log.info("=========||"+lq.toQuery());

		
		Resort rst = new Resort(tr.getLq());
//		rst.sortNearbyDefault();
		rst.sortNearbyDistance();		
		
		if(sortbyprice)
		{
		
			Collections.sort(lq.getPoiResult(), new Comparator<POIObject>() {

				@Override
				public int compare(POIObject o1, POIObject o2) {
					// TODO Auto-generated method stub
					String id1_source = o1.getFieldValue("from");
					String id2_source = o2.getFieldValue("from");
					String price1 = o1.getFieldValue("price");
					String price2 = o2.getFieldValue("price");
					if(id1_source.trim().length() >0 && id2_source.trim().length() == 0)
					{
						return -1;		
					}					
					if(id2_source.trim().length() >0 && id1_source.trim().length() == 0)
					{
						return 1;		
					}
					if(id1_source.trim().length() == 0 && id2_source.trim().length() == 0)
						return 0;
					float price_1 = 0;
					float price_2 = 0;
					
					try {
						price_1 = Float.parseFloat(price1);
						if(price_1<1) price_1 =100f;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						price_1 = 100f;
					}
					try {
						price_2 = Float.parseFloat(price2);
						if(price_2<1) price_2 =100f;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						price_2 = 100f;

					}
					return Float.compare(price_1, price_2);
				}
		    		
			});
		}
		
		LinkedList<POIObject> pointt = lq.getPoiResult();
		if(sortby ==1)
		{
			pointt = USort.sortByDiscount(lq.getPoiResult());
		}
		else if(sortby ==2)
			pointt = USort.sortByRank(lq.getPoiResult());
		else if(sortby == 3)
		{
//			pointt = USort.sortByDiscount(lq.getPoiResult());
//			pointt = USort.sortByRank(pointt);
			pointt = USort.sortByAvePerCost(lq.getPoiResult());
		}
		lq.setPoiResult(pointt);
		
		LinkedList<POIObject> tempall = new LinkedList<POIObject>();
		if(lq.getPoiResult().size()>0)
		{
			tempall.addAll(lq.getPoiResult());
		}
		start = Math.max(1, start);
		end = Math.min(end, tempall.size());
		if(start-1 < end)
			sr.getPOIs().addAll(tempall.subList(start-1, end));
		
		sr.setLq(lq);
		sr.setTotalCount(lq.getTotalResult());
		
		long endTime = System.currentTimeMillis();
		sr.setTime(endTime-startTime);
		return sr;
	}


}
