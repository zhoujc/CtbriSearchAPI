package com.ctbri.srcapi;

import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.search.Query;

import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.pojo.POIObject;



public class SearchResult {
	
	private LinkedList<POIObject> vPOIs = new LinkedList<POIObject>();
	private int totalCount;
	private LocalQuery lq;
	private Query q_re;
	
	private long time;
	
	public long getTime()
    {
        return time;
    }

	public LinkedList<POIObject> getPOIs() {
		return vPOIs;
	}

	public void setvPOIs(LinkedList<POIObject> vPOIs) {
		this.vPOIs = vPOIs;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public LocalQuery getLq() {
		return lq;
	}

	public void setLq(LocalQuery lq) {
		this.lq = lq;
	}

	public Query getQ_re() {
		if(q_re == null)
			return this.lq.toQuery();
		else
			return q_re;
	}

	public void setQ_re(Query q_re) {
		this.q_re = q_re;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	

}
