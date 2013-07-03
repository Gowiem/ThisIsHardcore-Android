package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;

import org.junit.Test;
import org.w3c.dom.Document;

import artisansupport.InstallHelper;

public class InstallerTests {

	
	public File getFileForResource(String myResource) throws IOException 
	{
	    URL url = this.getClass().getClassLoader().getResource(myResource);
	    String fileName;
	    if (url.getProtocol().equals("file")) {
	        fileName = url.getFile();        
	    } else if (url.getProtocol().equals("jar")) {
	        JarURLConnection jarUrl = (JarURLConnection) url.openConnection();
	        fileName = jarUrl.getJarFile().getName();            
	    } else {
	        throw new IllegalArgumentException("Not a file");
	    }
	    File file = new File(fileName);
	    return file;
	}
	
	
	@Test
	public void testNOHasArtisanLibrary() throws IOException {
		
		File f = getFileForResource("assets/classpath.xml");
		Document doc= InstallHelper.getDocumentForFile(f);
		boolean libs = InstallHelper.hasArtisanLibrary(doc);
		assertTrue("classpath shouldn't have artisan libs in it", libs != true);
		
	}

	@Test
	public void testHasArtisanLibrary() throws IOException {
		
		File f = getFileForResource("assets/installed_classpath.xml");
		Document doc= InstallHelper.getDocumentForFile(f);
		boolean libs = InstallHelper.hasArtisanLibrary(doc);
		assertTrue("classpath shouldn't have artisan libs in it", libs);
		
	}
	
	@Test
	public void testHasAspectJBuild() throws IOException {
		
		File f = getFileForResource("assets/installed_project.xml");
		Document doc= InstallHelper.getDocumentForFile(f);
		boolean aspectjBuilder = InstallHelper.hasAspectJBuilder(doc);
		
		assertTrue("classpath should have aspectj in it", aspectjBuilder );
		
	}
	
	@Test
	public void testHasAspectJNature() throws IOException {
		
		File f = getFileForResource("assets/installed_project.xml");
		Document doc= InstallHelper.getDocumentForFile(f);
		boolean aspectjnature = InstallHelper.hasAspectJNature(doc);
		
		assertTrue("classpath should have aspectj in it",  aspectjnature);
		
	}
	
	@Test
	public void testNOHasAspectJBuild() throws IOException {
		
		File f = getFileForResource("assets/project.xml");
		Document doc= InstallHelper.getDocumentForFile(f);
		boolean aspectjBuilder = InstallHelper.hasAspectJBuilder(doc);
		
		assertTrue("classpath should have aspectj in it", aspectjBuilder != true );
		
	}
	
	@Test
	public void testNOHasAspectJNature() throws IOException {
		
		File f = getFileForResource("assets/project.xml");
		Document doc= InstallHelper.getDocumentForFile(f);
		boolean aspectjnature = InstallHelper.hasAspectJNature(doc);
		
		assertTrue("classpath should have aspectj in it",  aspectjnature != true);
		
	}

}
