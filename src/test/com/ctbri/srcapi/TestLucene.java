package test.com.ctbri.srcapi;


import java.io.File;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.analyzing.AnalyzingQueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer;

public class TestLucene {

	@Test
	public void testSearch() {
		String indexpath = "D:/workspace/CtbriSearchCore/index/cinema1";

//		String path = "D:/My Documents/Tencent Files/289833594/FileRecv/cs2.9·Ö/";
//		IndexReader reader = IndexReader.open(FSDirectory.getDirectory(new File(path)));
		try {
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexpath)));
			IndexSearcher search = new IndexSearcher(reader);

			String usrQuery = "(tag:1) +(name:Ó°³Ç)";
			AnalyzingQueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"brand", new CtbriAnalyzer(C.segMode));			
			
			Query q = qp.parse(usrQuery);
			TopDocs td = search.search(q, 10);
			System.out.println(td.scoreDocs.length);
			IndexReader reader2 = reader;
			System.out.println(reader2.hashCode() +"  "+ reader.hashCode());
			reader2.close();
//			reader.close();
			Thread.sleep(1000000);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
