import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.Iterator;

public class Filewalker {

	public String source;
	
	Filewalker(String sourcePath) {
		this.source = sourcePath;
	}
	
	TreeSet<String> content = new TreeSet<String>();
    
	public TreeSet<String> walk( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();
        
         

        for ( File f : list ) {
        	
        	if (f.getAbsoluteFile().getName().compareTo(".DS_Store") != 0) {
        		
        		content.add(f.getAbsoluteFile().getPath().replace(this.source, ""));
        		
	            if ( f.isDirectory() ) {
	                walk( f.getAbsolutePath() );
	                //System.out.println( "Dir:" + f.getAbsoluteFile().getPath().replace(this.source, "") );
	            }
	            else {
	                //System.out.println( "File:" + f.getAbsoluteFile().getPath().replace(this.source, "") );                
	            }
	            
        	}
        	
        }
        
        /*for ( Iterator<String> flavoursIter = content.iterator(); flavoursIter.hasNext(); ) {
            System.out.println( flavoursIter.next() );
          }*/
        
        return content;
        
    }
}