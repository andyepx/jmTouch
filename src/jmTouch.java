

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.TreeSet;

import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;



public class jmTouch {
	
	private static String contentDir = "/Users/Andx/Documents/Work/Museo Santa/touchscreen/Content";
	
	public static void main(String[] args) {
		
		Filewalker fw = new Filewalker(contentDir);
		TreeSet<String> content = fw.walk(contentDir);

		fileHandler fh = new fileHandler();
		
		Mongo m;
		
		try {
			
			m = new Mongo();
			DB db = m.getDB( "museo" );
			
			DBCollection categories = db.getCollection("categories");
			DBCollection articles = db.getCollection("articles");
			DBCollection cc = db.getCollection("content");
			
			String lastCategory = "";
			String lastArticle = "";
			
			dbHandler dbh = new dbHandler(categories, articles, cc);
			
			for ( Iterator<String> dirContent = content.iterator(); dirContent.hasNext(); ) {
				
				String curDir = dirContent.next();
				String splits[] = curDir.split("/");
				
				for (String s : splits) {
					
					if (s.contains("c_")) {
						
						lastCategory = s;
						dbh.category(s);
						//System.out.println(dbh.category(s));
				        
					} else if (s.contains("a_")) {
						
						lastArticle = s;
						dbh.article(s, lastCategory);
						//System.out.println(dbh.article(s, lastCategory));
						
					} else if (s.contains("titolo.txt")) {
						
						String title = "";
						
						try {
							title = fh.readFile(contentDir+curDir);
						} catch (IOException e) {
							System.err.println(e);
						}
						
						dbh.categoryUpdate(lastCategory, title.trim().replace("\n", ""));
						
					} else if (s.contains("contenuto.txt")) {
						
						String articleContent = "";
						
						try {
							articleContent = fh.readFile(contentDir+curDir);
						} catch (IOException e) {
							System.err.println(e);
						}
						
						String articleContents[] = articleContent.split("\n");
						
						dbh.articleUpdate(lastCategory, lastArticle, articleContents[1], articleContents[3]);
						
					}
					
				}
				
			}
	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
	
}
