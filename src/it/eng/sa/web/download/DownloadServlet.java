package it.eng.sa.web.download;

import it.eng.sa.web.upload.SavedFileItem;
import it.eng.sa.web.upload.UploadServlet;

import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
		doPost(req, resp);
	}

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException{

		try {
			String command = req.getParameter("command");
			if (command==null)
				throw new ServletException("command==null in download servlet");
			else if (command.equals("esempio"))
				esempio(req, resp);
			else if(command.equals("session"))
				session(req, resp);
			else if(command.equals("disk"))
				disk(req, resp);
			else if(command.equals("db"))
				db(req, resp);
			else 
				throw new ServletException("command "+command+" is not valid in download servlet");
		}
		catch (Exception e) {
			if (e instanceof ServletException)
				throw (ServletException) e;
			else
				throw new ServletException(e);
		} 
	}
	
	public void esempio(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		byte out[]  = "ciao, sono un esempio".getBytes();
		resp.setContentType("text/plain");
		resp.setHeader("Content-Disposition","attachment; filename=esempio.txt");
		resp.setContentLength(out.length);
		OutputStream ouputStream = resp.getOutputStream();
		ouputStream.write(out);
		ouputStream.flush();
		ouputStream.close();
	}

	public void session(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		String key = req.getParameter("key");
		SavedFileItem item = UploadServlet.getUploaded(key, req.getSession());
		if (item==null) {
			resp.setStatus(404); // file not found
			return;
		}
		resp.setContentType(item.contentType);
		resp.setHeader("Content-Disposition","attachment; filename="+item.fileName);
		resp.setContentLength(item.data.length);
		OutputStream ouputStream = resp.getOutputStream();
		ouputStream.write(item.data);
		ouputStream.flush();
		ouputStream.close();
	}

	public void disk(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		//...
	}

	public void db(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		//...
	}


}
	
