import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class dbHandler {
	
	//private DBCollection categories;
	//private DBCollection articles;
	private DBCollection content;
	
	dbHandler(DBCollection categories, DBCollection articles, DBCollection content) {
		
		//this.categories = categories;
		//this.articles = articles;
		this.content = content;
		
	}

	public DBObject category(String s) {
		
		DBObject exists = checkCategory(s);
        
        if (exists == null) {
        	
        	BasicDBObject doc = new BasicDBObject();
        	doc.put("category", s);
            doc.put("name", s);
            
            content.insert(doc);
            
            DBCursor cur = content.find(doc);
            
            System.out.println("NEW");
            
            while(cur.hasNext()) {
                return cur.next();
            }
        	
        }
        
        return exists;
		
	}
	
	public DBObject checkCategory(String s) {
		
		BasicDBObject query = new BasicDBObject();
        query.put("category", s);
        DBCursor cur = content.find(query);
                
        while(cur.hasNext()) {
            return cur.next();
        }
        
        return null;
        
	}
	
	public DBObject categoryUpdate(String category, String title) {
		
		BasicDBObject query = new BasicDBObject();
		query.put("category", category);
		
		/*DBCursor cursor = content.find(query);
		while(cursor.hasNext()) {
		    return cursor.next();
		}*/
		
		//String cCode = (String) content.findOne(query);
		
		BasicDBObject doc = new BasicDBObject().append("$set", 
				new BasicDBObject().append("name", title));
		
		content.update(query, doc);
		
		//System.out.println(cName);
		
		return null;
		
	}
	
	public DBObject articleUpdate(String category, String article, String title, String contents) {
		
		BasicDBObject query = new BasicDBObject();
		query.put("category", category);
		query.put("articles.article", article);
		
		/*DBCursor cursor = content.find(query);
		while(cursor.hasNext()) {
		    return cursor.next();
		}*/
		
		//String cCode = (String) content.findOne(query);
		
		BasicDBObject doc = new BasicDBObject().append("$set", 
				new BasicDBObject().append("atitle", title));
		
		content.update(query, doc);
		
		//System.out.println(cName);
		
		return null;
		
	}
	
	public DBObject checkArticleInCategory(String s, String category) {
		
		BasicDBObject query = new BasicDBObject();
		query.put("category", category);
        query.put("articles.article", s);
        DBCursor cur = content.find(query);
                
        while(cur.hasNext()) {
            return cur.next();
        }
        
        return null;
        
	}

	public DBObject article(String s, String category) {
		
		DBObject exists = checkArticleInCategory(s, category);
		
		if (exists == null) {
			
			BasicDBObject article = new BasicDBObject();
			article.put("article", s);
			
			//BasicDBObject doc = new BasicDBObject();
        	//doc.append("articles", article);
            //doc.put("category", lastCategory);
            
			BasicDBObject newArticle = new BasicDBObject().append("$push", 
					new BasicDBObject().append("articles", article));
			
            content.update(new BasicDBObject().append("category", category), newArticle);
            
            /*DBCursor cur = articles.find(doc);
            
            System.out.println("NEW");
            
            while(cur.hasNext()) {
                return cur.next();
            }*/
			
		}
		
		return null;
	}
	
}
