package com.dcdzsoft.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.RowSet;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dcdzsoft.EduException;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.config.BusiHandlerEntity;
import com.dcdzsoft.config.BusinessConfig;
import com.dcdzsoft.constant.Constant;
import com.dcdzsoft.print.MyPrintUtils;
import com.dcdzsoft.print.PrintUtils;
import com.dcdzsoft.util.JsonUtils;
import com.dcdzsoft.util.NumberUtils;
import com.dcdzsoft.util.StringUtils;
import com.dcdzsoft.util.WebUtils;

public class MyService extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();

	private String g_ReportPath = "";

	private String g_UserFilePath = "";

	// Initialize global variables
	public void init() throws ServletException {
		ServletContext context = getServletContext();

		String realPath = context.getRealPath("/");
		if (!realPath.endsWith("/"))
			realPath = realPath + "/";

		g_ReportPath = realPath + "report/";

		g_UserFilePath = realPath + "temp/";
		// System.out.println("ReportPath="+g_ReportPath);
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType(CONTENT_TYPE);

		HttpSession session = request.getSession(true);

		// 合法性检查 前台必须通过url?的形式把以下两个参数传递过来
		// String jsessionid = request.getRequestedSessionId();
		String uuid = WebUtils.getStringParameter("uuid", request);

		String sUuid = WebUtils.getSessionAttribute("uuid", session);
		String sOperID = WebUtils.getOperIDAttribute(session);

		if (StringUtils.isEmpty(sOperID)) {
			PrintWriter out = response.getWriter();

			out.println(JsonUtils.getErrorJSONStr("系统超时，请重新登陆", null));
			out.close();

			return;
		}
		if (StringUtils.isEmpty(uuid) || !uuid.equals(sUuid)) {
			PrintWriter out = response.getWriter();

			out.println(JsonUtils.getErrorJSONStr("非法的请求", null));
			out.close();

			return;
		}

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) // 处理文件上传的请求
		{
			String FuncMenuID = WebUtils.getStringParameter("FuncMenuID",
					request);
			String cmd = WebUtils.getStringParameter("cmd", request);

			if (StringUtils.isEmpty(cmd))
				cmd = "default";

			PrintWriter out = response.getWriter();

			String outMsg = handLoadFileBusiness(request);

			out.println(outMsg);
			out.flush();
			out.close();
		} else {
			String FuncMenuID = WebUtils.getStringParameter("FuncMenuID",
					request);
			String cmd = WebUtils.getStringParameter("cmd", request);

			if (StringUtils.isEmpty(cmd))
				cmd = "default";

			if (cmd.indexOf("print") != -1 || cmd.indexOf("export") != -1) {
				try {
					String key = FuncMenuID + cmd;
					BusinessConfig cfg = BusinessConfig.getInstance();
					BusiHandlerEntity handler = cfg.getHandler(key);

					if (handler == null) {
						String errMsg = JsonUtils.getErrorJSONStr(
								cfg.getNoFuncMsg(), null);

						PrintWriter out = response.getWriter();
						out.println(errMsg);
						out.flush();
						out.close();

						return;
					}
					String datetimeStr = Constant.dateFromat.format(new Date());
					datetimeStr = datetimeStr.replaceAll(" ", "_");
					datetimeStr = datetimeStr.replaceAll(":", "_");
					byte[] bytes = handPrintExpBusiness(FuncMenuID, cmd,
							request);
					if (cmd.indexOf("print") != -1) {
						// //inline --- 在线打开 IE下为何打开两个pdf???
						if (cfg.getPrintWay().equals("pdf"))
							response.setContentType("application/pdf");
						else
							response.setContentType("text/html; charset=utf-8");

						response.setHeader(
								"content-disposition",
								"inline;filename=\""
										+ URLEncoder.encode(
												handler.getAttachFile(),
												"UTF-8") + "_" + datetimeStr
										+ ".pdf\"");
					} else if (cmd.indexOf("export") != -1) {
						// //attachment --- 作为附件下载
						response.setContentType("application/vnd.ms-excel");
						response.setHeader(
								"content-disposition",
								"attachment;filename=\""
										+ URLEncoder.encode(
												handler.getAttachFile(),
												"UTF-8") + "_" + datetimeStr
										+ ".xls\"");
					}
					response.setContentLength(bytes.length);
					ServletOutputStream ouputStream = response
							.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);

					ouputStream.flush();
					ouputStream.close();
				} catch (EduException e) {
					String errMsg = e.getMessage();

					PrintWriter out = response.getWriter();
					out.println(errMsg);
					out.flush();
					out.close();
				}
			} else {
				PrintWriter out = response.getWriter();

				String outMsg = handCommonBusiness(FuncMenuID, cmd, request);
				out.println(outMsg);
				out.flush();
				out.close();
			}
		}
	}

	// 一般的业务处理
	private String handCommonBusiness(String FuncMenuID, String cmd,
			HttpServletRequest request) {
		String outMsg = "";

		BusinessConfig cfg = BusinessConfig.getInstance();
		String key = FuncMenuID + cmd;

		BusiHandlerEntity handler = cfg.getHandler(key);
		if (handler == null) {
			outMsg = JsonUtils.getErrorJSONStr(cfg.getNoFuncMsg(), null);
		} else {
			try {
				Class inClass = Class.forName(handler.getParamClass());
				Class adapterClass = Class.forName(handler.getAdapterClass());

				Object inParam = WebUtils.buildBussinessDTOParam(inClass,
						request, handler);
				String methodName = "doBusiness";

				Method method = adapterClass.getMethod(methodName,
						new Class[] { inClass });
				Object result = method.invoke(adapterClass,
						new Object[] { inParam });

				String returntype = handler.getReturntype();
				if ("S".equals(returntype)) // 更新操作
				{
					outMsg = JsonUtils.getSuccessJSONStr(
							handler.getSucessMsg(), result);
				} else if ("N".equals(returntype)) // 查询一次返回
				{
					RowSet rset = (RowSet) result;

					// 功能权限菜单
					if ("querymenu".equalsIgnoreCase(cmd)
							&& "13010000".equalsIgnoreCase(FuncMenuID))
						outMsg = WebUtils.buildFuncMenuJSONStr(rset, false);
					else if ("querymenu".equalsIgnoreCase(cmd)
							&& "13020000".equalsIgnoreCase(FuncMenuID))
						outMsg = WebUtils.buildFuncMenuJSONStr(rset, true);
					// 特殊权限菜单
					else if ("queryspe".equalsIgnoreCase(cmd)
							&& "13010000".equalsIgnoreCase(FuncMenuID))
						outMsg = WebUtils.buildSpeRightJSONStr(rset, false);
					else if ("queryspe".equalsIgnoreCase(cmd)
							&& "13020000".equalsIgnoreCase(FuncMenuID))
						outMsg = WebUtils.buildSpeRightJSONStr(rset, true);
					// 运营网点查询树
					else if ("querytree".equalsIgnoreCase(cmd)
							&& "12150000".equalsIgnoreCase(FuncMenuID))
						outMsg = WebUtils.buildDepartTreeJSONStr(rset);
					else
						outMsg = JsonUtils.getJSONFromRowSet(rset, 0);
				} else if ("P".equals(returntype)) // 查询多次返回
				{
					Class inCountClass = Class.forName(handler
							.getParamCountClass());
					Object inCountParam = WebUtils.buildBussinessDTOParam(
							inCountClass, request, handler);

					Method methodCount = adapterClass.getMethod(methodName,
							new Class[] { inCountClass });
					Object objCount = methodCount.invoke(adapterClass,
							new Object[] { inCountParam });

					int count = NumberUtils.parseInt(objCount.toString());

					RowSet rset = (RowSet) result;

					outMsg = JsonUtils.getJSONFromRowSet(rset, count);
				}

			} catch (NoSuchMethodException e) {
				outMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);

				System.err.println("[My Service:NoSuchMethodException]"
						+ e.getMessage());

			} catch (java.lang.reflect.InvocationTargetException e) {
				outMsg = JsonUtils.getErrorJSONStr(handler.getErrorMsg(),
						e.getTargetException());
			} catch (Exception e) {
				outMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);
				System.err.println("[My Service]" + e.getMessage());
			}
		}

		return outMsg;
	}

	// 打印和导出业务的处理
	private byte[] handPrintExpBusiness(String FuncMenuID, String cmd,
			HttpServletRequest request) throws EduException {
		String errMsg = "";
		byte[] bytes = new byte[0];

		BusinessConfig cfg = BusinessConfig.getInstance();
		String key = FuncMenuID + cmd;

		BusiHandlerEntity handler = cfg.getHandler(key);
		if (handler == null) {
			errMsg = JsonUtils.getErrorJSONStr(cfg.getNoFuncMsg(), null);
			throw new EduException(errMsg);
		}

		try {
			String methodName = "doBusiness";
			Object resultRep = null;// 报表参数
			Object result = null;
			Map parameters = null;

			// 报表的参数
			if (StringUtils.isNotEmpty(handler.getParamRepClass())) {
				Class inRepClass = Class.forName(handler.getParamRepClass());
				Class adapterRepClass = Class.forName(handler
						.getAdapterRepClass());

				Object inRepParam = WebUtils.buildBussinessDTOParam(inRepClass,
						request, handler);

				Method methodRep = adapterRepClass.getMethod(methodName,
						new Class[] { inRepClass });
				resultRep = methodRep.invoke(adapterRepClass,
						new Object[] { inRepParam });
			}

			// 报表的数据
			if (StringUtils.isNotEmpty(handler.getParamClass())) {
				Class inClass = Class.forName(handler.getParamClass());
				Class adapterClass = Class.forName(handler.getAdapterClass());

				Object inParam = WebUtils.buildBussinessDTOParam(inClass,
						request, handler);

				Method method = adapterClass.getMethod(methodName,
						new Class[] { inClass });
				result = method.invoke(adapterClass, new Object[] { inParam });
			}

			String fileName = g_ReportPath + handler.getReportFile();

			// 生成实际的报表数据流
			if (cmd.indexOf("print") != -1) {
				if (result instanceof com.dcdzsoft.dto.business.OutParamPrintData) {
					bytes = MyPrintUtils
							.printToPdf(
									(com.dcdzsoft.dto.business.OutParamPrintData) result,
									fileName);
				} else {
					if (resultRep == null)
						parameters = PrintUtils.initReportParameter(request,
								"print");
					else
						parameters = PrintUtils.initReportParameter(
								(RowSet) resultRep, "print");

					if ("pdf".equalsIgnoreCase(cfg.getPrintWay()))
						bytes = PrintUtils.exportToPdf(result == null ? null
								: (RowSet) result, parameters, fileName);
					else
						bytes = PrintUtils.exportToHtml(result == null ? null
								: (RowSet) result, parameters, fileName);
				}

			} else if (cmd.indexOf("export") != -1) {
				if (resultRep == null)
					parameters = PrintUtils.initReportParameter(request,
							"export");
				else
					parameters = PrintUtils.initReportParameter(
							(RowSet) resultRep, "export");
				bytes = MyPrintUtils.exportToXls(result == null ? null
						: (RowSet) result, parameters, fileName);
			}
		} catch (NoSuchMethodException e) {
			errMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);

			System.err.println("[My Service:NoSuchMethodException]"
					+ e.getMessage());

			throw new EduException(errMsg);

		} catch (java.lang.reflect.InvocationTargetException e) {
			errMsg = JsonUtils.getErrorJSONStr(handler.getErrorMsg(),
					e.getTargetException());

			throw new EduException(errMsg);
		} catch (Exception e) {
			errMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);
			System.err.println("[My Service]=" + e.getMessage());
			e.printStackTrace();

			throw new EduException(errMsg);
		}

		return bytes;
	}

	// 带文件上传的业务处理
	private JSONObject getBusinessParam(HttpServletRequest request) {

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024);
		factory.setRepository(new File(g_UserFilePath));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(10 * 1024 * 1024);
		JSONObject paramJson = new JSONObject();

		try {
			List<FileItem> fileItems = upload
					.parseRequest((HttpServletRequest) request);
			// System.out.println("fileItems size="+fileItems.size());
			Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();

				if (fi.isFormField()) {

					String fieldName = fi.getFieldName();
					String fieldvalue = fi.getString();
					//
					paramJson.put(fieldName, fieldvalue);
					// System.out.println("name:" + fieldName);
					// System.out.println("value:" + fieldvalue);
				} else {
					// System.out.println("name:.....");
				}
			}
		} catch (Exception e) {// FileUploadException

		}

		return paramJson;
	}

	private String handLoadFileBusiness(HttpServletRequest request) {
		String FuncMenuID = "";
		String cmd = "default";

		String outMsg = "";
		// 设置保存的文件和路径名
		String filePath = "";
		String fileName = "";

		// 读取数据，保存文件
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024);
		factory.setRepository(new File(filePath));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(10 * 1024 * 1024);
		JSONObject paramJson = new JSONObject();
		try {
			List<FileItem> fileItems = upload
					.parseRequest((HttpServletRequest) request);
			Iterator<FileItem> iter = fileItems.iterator();

			// #start 获取form字段内容
			iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					// 获取form字段内容
					String fieldName = fi.getFieldName();
					// System.out.println("name:" + fieldName);
					String fieldvalue = "";// fi.getString();
					fieldvalue = new String(fi.get(), "utf-8");
					paramJson.put(fieldName, fieldvalue);
					// System.out.println("value:" + fieldvalue);
				}
			}
			if (paramJson.has("FuncMenuID") && paramJson.has("cmd")) {
				FuncMenuID = paramJson.getString("FuncMenuID");
				cmd = paramJson.getString("cmd");
				if ("edit".equalsIgnoreCase(cmd)
						&& "12210000".equalsIgnoreCase(FuncMenuID)
						&& paramJson.has("CompanyID")) {
					// company logos upload
					String companyid = paramJson.getString("CompanyID");

					if (StringUtils.isNotEmpty(companyid)) {
						filePath = apCfg.getFullCompanyLogosPath() + "/";//
						fileName = companyid;
					}
				}
			}

			// #end
			iter = fileItems.iterator();
			// 保存文件
			while (iter.hasNext()) {
				FileItem fi = iter.next();

				if (!fi.isFormField()) {
					String filename = fi.getName();
					// if (filename != null) {
					// System.out.println(filename);
					// }
					if (StringUtils.isBlank(filename)) {
						continue;
					}
					String extensionName = filename.substring(
							filename.lastIndexOf("."), filename.length());
					String contentType = fi.getContentType();
					// System.out.println(contentType);

					if (StringUtils.isEmpty(filePath)) {
						filePath = g_UserFilePath;
					}
					if (StringUtils.isEmpty(fileName)) {
						fileName = StringUtils.getUUID();
					}

					// boolean isInMemory = fi.isInMemory();
					// long sizeInBytes = fi.getSize();

					String url = filePath + fileName + extensionName;
					File uploadedFile = new File(url);
					if (!uploadedFile.exists()) {
						uploadedFile.getParentFile().mkdirs();
					}

					fi.write(uploadedFile);

					if (paramJson.has("LogoUrl")) {
						String logouri = apCfg.getCompanyLogosPath() + "/"
								+ fileName + extensionName;
						paramJson.put("LogoUrl", logouri);
						System.out.println("uploaded:" + filename + " to "
								+ url + ",logouri=" + logouri);
						// System.out.println("AbsolutePath:"+uploadedFile.getAbsolutePath());
					}

					fileName = "";

				}
			}

		} catch (Exception e) {// FileUploadException
			System.out.println(e.getMessage());
		}

		// 业务处理
		if ("edit".equalsIgnoreCase(cmd)
				&& "12210000".equalsIgnoreCase(FuncMenuID)) {
			BusinessConfig cfg = BusinessConfig.getInstance();

			String key = FuncMenuID + cmd;

			BusiHandlerEntity handler = cfg.getHandler(key);
			if (handler == null) {
				outMsg = JsonUtils.getErrorJSONStr(cfg.getNoFuncMsg(), null);
			} else {
				try {
					// 执行业务

					Class inClass = Class.forName(handler.getParamClass());
					Class adapterClass = Class.forName(handler
							.getAdapterClass());

					Object inParam = WebUtils.buildBussinessDTOParam(inClass,
							request, handler, paramJson);
					String methodName = "doBusiness";

					Method method = adapterClass.getMethod(methodName,
							new Class[] { inClass });

					Object result = method.invoke(adapterClass,
							new Object[] { inParam });
					outMsg = JsonUtils.getSuccessJSONStr(
							handler.getSucessMsg(), 0);// result

				} catch (NoSuchMethodException e) {
					outMsg = JsonUtils
							.getErrorJSONStr(cfg.getSysErrMsg(), null);

					System.err.println("[My Service:NoSuchMethodException]"
							+ e.getMessage());

				} catch (java.lang.reflect.InvocationTargetException e) {
					outMsg = JsonUtils.getErrorJSONStr(handler.getErrorMsg(),
							e.getTargetException());
				} catch (Exception e) {
					outMsg = JsonUtils
							.getErrorJSONStr(cfg.getSysErrMsg(), null);
					System.err.println("[My Service]" + e.getMessage());
				}
			}
		} else {
			outMsg = JsonUtils.getSuccessJSONStr("", 0);// Successful
		}
		// String filetype = paramJson.getString("filetype");
		return outMsg;
	}

	// Clean up resources
	public void destroy() {
	}
}
