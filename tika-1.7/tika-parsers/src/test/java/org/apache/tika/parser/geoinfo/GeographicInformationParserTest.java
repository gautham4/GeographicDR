package org.apache.tika.parser.geoinfo;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.geoinfo.GeographicInformationParser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import java.io.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 4/30/2015.
 */
public class GeographicInformationParserTest {

    @Test
    public void testISO19139() throws Exception{
        String path ="/test-documents/sampleFile.iso19139";
		
        //BufferedReader bufferedReader=new BufferedReader(new FileReader(new File(path)));
        //System.out.println(bufferedReader.readLine());

        Metadata metadata = new Metadata();
        Parser parser=new org.apache.tika.parser.geoinfo.GeographicInformationParser();
        ContentHandler contentHandler=new BodyContentHandler();
        ParseContext parseContext=new ParseContext();
        //InputStream inputStream=new FileInputStream(new File(path));
        InputStream inputStream = GeographicInformationParser.class.getResourceAsStream(path);
        if(inputStream==null)
            System.out.println("Stream null");
        else
            parser.parse(inputStream, contentHandler, metadata, parseContext);

        assertEquals("text/iso19139+xml", metadata.get(Metadata.CONTENT_TYPE));
        assertEquals("UTF-8", metadata.get("CharacterSet"));
        assertEquals("https", metadata.get("TransferOptionsOnlineProtocol "));
        assertEquals("browser", metadata.get("TransferOptionsOnlineProfile "));
        assertEquals("Barrow Atqasuk ARCSS Plant", metadata.get("TransferOptionsOnlineName "));

        String content = contentHandler.toString();
        assertTrue(content.contains("Barrow Atqasuk ARCSS Plant"));
        assertTrue(content.contains("GeographicElementWestBoundLatitude	-157.24"));
        assertTrue(content.contains("GeographicElementEastBoundLatitude	-156.4"));
        assertTrue(content.contains("GeographicElementNorthBoundLatitude	71.18"));
        assertTrue(content.contains("GeographicElementSouthBoundLatitude	70.27"));

    }

}
