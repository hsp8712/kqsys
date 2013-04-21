package cn.huangshaoping.page.tag;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @version 1.0
 */

public class ResourceTemplate
{

	private static Logger log = Logger.getLogger(ResourceTemplate.class);
	
    private static ResourceTemplate instance;

    private Map<String, String> templates = new HashMap<String, String>();
    

    private ResourceTemplate()
    {
    	
    }

    /**
     * @param context IAppContext
     * @return ResourceTemplate
     */
    public static ResourceTemplate getInstance()
    {
        if( instance == null )
        {
            instance = new ResourceTemplate();
            return instance;
        }
        return instance;
    }

    /**
     * Load template resource from template file
     * @param name String
     * @return String
     * @throws UnsupportedEncodingException 
     */
    public String loadTemplate( String name) throws UnsupportedEncodingException
    {
        String content = ( String )templates.get( name );
        if( content == null )
        {
            synchronized( this )
            {
                content = ( String )templates.get( name );
                if( content != null )
                {
                    return content;
                }
                InputStream is = null;
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name );
                BufferedReader bf = new BufferedReader( new InputStreamReader( is ) );
                String temp = null;
                try
                {
                    StringBuffer sb = new StringBuffer();
                    while( ( temp = bf.readLine() ) != null )
                    {
                        sb.append( temp );
                    }
                    content = sb.toString();
                }
                catch( Exception e )
                {
                    log.error(e.getMessage(), e);
                }
                templates.put( name, content );
            }
        }
        return content;
    }
}
