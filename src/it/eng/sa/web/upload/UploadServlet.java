package it.eng.sa.web.upload;


import it.eng.sa.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static SavedFileItem buildSavedFileItem(FileItem item, String key, HttpSession session) throws Exception {
		SavedFileItem si = new SavedFileItem();
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		InputStream is = item.getInputStream();
		
		byte buf[] = new byte[10000];
		while(true) {
			int n = is.read(buf);
			if (n<=0)
				break;
			b.write(buf,0,n);
		}
		
		si.data = b.toByteArray();
		si.contentType = item.getContentType();
		si.fileName = item.getName();
		si.sessionId = session.getId();
		return si;
	}
	
	public static SavedFileItem getUploaded(String key, HttpSession session) {
		SavedFileItem s = (SavedFileItem) session.getAttribute("uploaded."+key);
		if (s==null) {
			// cerca su file
		}
		if (s==null) {
			// cerca su db
		}
		return s;
	}

	public static void cleanSession(String sessionId) {
		// cancella eventuali file salvati su disco o DB
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        String sessionKey = req.getParameter("sessionKey");
        String fileKey = req.getParameter("fileKey");
        String dbKey = req.getParameter("sessionKey");
		FileItem fileItem = null;
		HttpSession session = req.getSession();
        try {
			ServletFileUpload uploader = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = uploader.parseRequest(req);
			for(FileItem item: items) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					if(name.equals("sessionKey"))
						sessionKey = item.getString();
					if(name.equals("fileKey"))
						fileKey = item.getString();
					if(name.equals("dbKey"))
						dbKey = item.getString();
				}
				else
					fileItem = item;
			}
			if (sessionKey!=null && fileItem!=null) {
				Log.web.debug("salvo file upload su session, key="+sessionKey);
				SavedFileItem si = buildSavedFileItem(fileItem, sessionKey, session);
				// TODO: per ora salva sempre il file in session, si potrebbe mettere una logica per salvare
				// file di grandi dimensioni su disco o su db
				session.setAttribute("uploaded."+sessionKey, si);				

			}
			else if (fileKey!=null && fileItem!=null) {
				Log.web.debug("salvo file upload su file, key="+fileKey);
				// TODO
			}
			else if (dbKey!=null && fileItem!=null) {
				Log.web.debug("salvo file upload su db, key="+dbKey);
				// TODO
			}
			else {
				Log.web.info("upload senza effetti");
			}
		} 
		catch (Exception e) {
			throw new ServletException("Cannot parse multipart request.", e);
		}
	}
	

}
	
