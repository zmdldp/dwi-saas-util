package com.dwi.basic.boot.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.CaseInsensitiveMap;
import cn.hutool.core.map.multi.ListValueMap;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.net.multipart.MultipartFormData;
import cn.hutool.core.net.multipart.UploadSetting;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;




/**
 * Springboot Servlet工具类
 * 
 * @author delta
 */
public class ServletUtils {
	
	final static Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;
	
	/**
	 * 获得Http Method
	 *
	 * @return Http Method
	 */
	public static String getMethod() {
		return getRequest().getMethod();
	}
	
	/**
	 * 是否为GET请求
	 *
	 * @return 是否为GET请求
	 */
	public static boolean isGetMethod() {
		return Method.GET.name().equalsIgnoreCase(getMethod());
	}

	/**
	 * 是否为POST请求
	 *
	 * @return 是否为POST请求
	 */
	public static boolean isPostMethod() {
		return Method.POST.name().equalsIgnoreCase(getMethod());
	}
	
	/**
	 * 获得请求URI
	 *
	 * @return 请求URI
	 */
	public static String getURI() {
		return getRequest().getRequestURI();
	}

	/**
	 * 获得请求路径Path
	 *
	 * @return 请求路径
	 */
	public static String getPath() {
		return getRequest().getServletPath();
	}
	
	/**
	 * 获取请求参数
	 *
	 * @return 参数字符串
	 */
	public static String getQuery() {
		return getRequest().getQueryString();
	}	

	/**
	 * 获得请求header中的信息
	 *
	 * @param headerKey 头信息的KEY
	 * @return header值
	 */
	public static String getHeader(Header headerKey) {
		return getHeader(headerKey.toString());
	}

	/**
	 * 获得请求header中的信息
	 *
	 * @param headerKey 头信息的KEY
	 * @return header值
	 */
	public static String getHeader(String headerKey) {
		return getRequest().getHeader(headerKey);
	}

	/**
	 * 获得请求header中的信息
	 *
	 * @param headerKey 头信息的KEY
	 * @param charset   字符集
	 * @return header值
	 */
	public static String getHeader(String headerKey, Charset charset) {
		final String header = getHeader(headerKey);
		if (null != header) {
			return CharsetUtil.convert(header, CharsetUtil.CHARSET_ISO_8859_1, charset);
		}
		return null;
	}

	
	/**
	 * 获取Content-Type头信息
	 *
	 * @return Content-Type头信息
	 */
	public static String getContentType() {
		return getHeader(Header.CONTENT_TYPE);
	}

	/**
	 * 获取编码，获取失败默认使用UTF-8，获取规则如下：
	 *
	 * <pre>
	 *     1、从Content-Type头中获取编码，类似于：text/html;charset=utf-8
	 * </pre>
	 *
	 * @return 编码，默认UTF-8
	 */
	public static Charset getCharset() {
		
		final String contentType = getContentType();
		final String charsetStr = HttpUtil.getCharset(contentType);
		return CharsetUtil.parse(charsetStr, DEFAULT_CHARSET);
	}

	/**
	 * 获得User-Agent
	 *
	 * @return User-Agent字符串
	 */
	public static String getUserAgentStr() {
		return getHeader(Header.USER_AGENT);
	}

	/**
	 * 获得User-Agent，未识别返回null
	 *
	 * @return User-Agent字符串，未识别返回null
	 */
	public static UserAgent getUserAgent() {
		return UserAgentUtil.parse(getUserAgentStr());
	}

	/**
	 * 获得Cookie信息字符串
	 *
	 * @return cookie字符串
	 */
	public static String getCookiesStr() {
		return getHeader(Header.COOKIE); 
	}

	/**
	 * 获得Cookie信息列表
	 *
	 * @return Cookie信息列表
	 */
	public static Collection<HttpCookie> getCookies() {
		return getCookieMap().values();
	}

	/**
	 * 获得Cookie信息Map，键为Cookie名，值为HttpCookie对象
	 *
	 * @return Cookie信息Map
	 */
	public static Map<String, HttpCookie> getCookieMap() {
		return Collections.unmodifiableMap(CollUtil.toMap(
				NetUtil.parseCookies(getCookiesStr()),
				new CaseInsensitiveMap<>(),
				HttpCookie::getName));
	}

	/**
	 * 获得指定Cookie名对应的HttpCookie对象
	 *
	 * @param cookieName Cookie名
	 * @return HttpCookie对象
	 */
	public static HttpCookie getCookie(String cookieName) {
		return getCookieMap().get(cookieName);
	}

	/**
	 * 是否为Multipart类型表单，此类型表单用于文件上传
	 *
	 * @return 是否为Multipart类型表单，此类型表单用于文件上传
	 */
	public static boolean isMultipart() {
		if (false == isPostMethod()) {
			return false;
		}

		final String contentType = getContentType();
		if (StrUtil.isBlank(contentType)) {
			return false;
		}
		return contentType.toLowerCase().startsWith("multipart/");
	}

	/**
	 * 获取请求体文本，可以是form表单、json、xml等任意内容<br>
	 * 使用{@link #getCharset()}判断编码，判断失败使用UTF-8编码
	 *
	 * @return 请求
	 * @throws IOException 
	 * @throws IORuntimeException 
	 */
	public static String getBody() {
		return getBody(getCharset());
	}

	/**
	 * 获取请求体文本，可以是form表单、json、xml等任意内容
	 *
	 * @param charset 编码
	 * @return 请求
	 * @throws IOException 
	 * @throws IORuntimeException 
	 */
	public static String getBody(Charset charset) {
		return StrUtil.str(getBodyBytes(), charset);
	}

	/**
	 * 获取body的bytes数组
	 *
	 * @return body的bytes数组
	 * @throws IOException 
	 * @throws IORuntimeException 
	 */
	public static byte[] getBodyBytes() {
		return IoUtil.readBytes(getBodyStream(), true);
	}

	/**
	 * 获取请求体的流，流中可以读取请求内容，包括请求表单数据或文件上传数据
	 *
	 * @return 流
	 * @throws IOException 
	 */
	public static InputStream getBodyStream() {
		try {
			return getRequest().getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return new ByteArrayInputStream(new byte[0]);
		}
	}
	
	/**
	 * 获取指定名称的参数值，取第一个值
	 * @param name 参数名
	 * @return 参数值
	 * @since 5.5.8
	 */
	public static String getParam(String name){
		return getParams().get(name, 0);
	}
	
	/**
	 * 获取指定名称的参数值
	 *
	 * @param name 参数名
	 * @return 参数值
	 * @since 5.5.8
	 */
	public static List<String> getParams(String name){
		return getParams().get(name);
	}

    /**
     * 获取String参数
     */
    public static String getParam(String name, String defaultValue)
    {
        return Convert.toStr(getParam(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParamToInt(String name)
    {
        return Convert.toInt(getParam(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParamToInt(String name, Integer defaultValue)
    {
        return Convert.toInt(getParam(name), defaultValue);
    }
    
    
    /**
	 * 获取参数Map
	 *
	 * @return 参数map
	 */
	@SuppressWarnings("unchecked")
	public static ListValueMap<String, String> getParams() {
			ListValueMap<String, String> paramsCache = new ListValueMap<>();
			final Charset charset = getCharset();

			//解析URL中的参数
			final String query = getQuery();
			if(StrUtil.isNotBlank(query)){
				paramsCache.putAll(HttpUtil.decodeParams(query, charset));
			}
						
			// 解析multipart中的参数
			if(isMultipart()){
				paramsCache.putAll(getMultipart().getParamListMap());
			} 			
			
			final String body = getBody();
			if(StrUtil.isNotBlank(body)){
				// 解析body中的json参数:1层
				if(isAjaxRequest(getRequest())) {				
					if(JSONUtil.isJsonObj(body)) {
						new JSONObject(body).toBean(Map.class).forEach((x,y)->{
							paramsCache.putValue(String.valueOf(x), String.valueOf(y));
						});
					}
				}else{
					// 解析body中的中的键值对参数
					paramsCache.putAll(HttpUtil.decodeParams(body, charset));
				}
			}		
		return paramsCache;
	}
	
	/**
	 * 获取客户端IP
	 *
	 * <p>
	 * 默认检测的Header:
	 *
	 * <pre>
	 * 1、X-Forwarded-For
	 * 2、X-Real-IP
	 * 3、Proxy-Client-IP
	 * 4、WL-Proxy-Client-IP
	 * </pre>
	 *
	 * <p>
	 * otherHeaderNames参数用于自定义检测的Header<br>
	 * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
	 * </p>
	 *
	 * @param otherHeaderNames 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
	 * @return IP地址
	 */
	public static String getClientIP(String... otherHeaderNames) {
		String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
		if (ArrayUtil.isNotEmpty(otherHeaderNames)) {
			headers = ArrayUtil.addAll(headers, otherHeaderNames);
		}

		return getClientIPByHeader(headers);
	}

	/**
	 * 获取客户端IP
	 *
	 * <p>
	 * headerNames参数用于自定义检测的Header<br>
	 * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
	 * </p>
	 *
	 * @param headerNames 自定义头，通常在Http服务器（例如Nginx）中配置
	 * @return IP地址
	 * @since 4.4.1
	 */
	public static String getClientIPByHeader(String... headerNames) {
		String ip;
		for (String header : headerNames) {
			ip = getHeader(header);
			if (false == NetUtil.isUnknown(ip)) {
				return NetUtil.getMultistageReverseProxyIp(ip);
			}
		}

		ip = getRequest().getRemoteHost();
		return NetUtil.getMultistageReverseProxyIp(ip);
	}

	/**
	 * 获得MultiPart表单内容，多用于获得上传的文件
	 *
	 * @return MultipartFormData
	 * @throws IORuntimeException IO异常
	 * @since 5.3.0
	 */
	public static MultipartFormData getMultipart() throws IORuntimeException {
	
		return parseMultipart(new UploadSetting());		
	}

	/**
	 * 获得multipart/form-data 表单内容<br>
	 * 包括文件和普通表单数据<br>
	 * 在同一次请求中，此方法只能被执行一次！
	 *
	 * @param uploadSetting 上传文件的设定，包括最大文件大小、保存在内存的边界大小、临时目录、扩展名限定等
	 * @return MultiPart表单
	 * @throws IORuntimeException IO异常
	 * @since 5.3.0
	 */
	public static MultipartFormData parseMultipart(UploadSetting uploadSetting) throws IORuntimeException {
		final MultipartFormData formData = new MultipartFormData(uploadSetting);
		try {
			formData.parseRequestStream(getBodyStream(), getCharset());
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}

		return formData;
	}
    
    

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse()
    {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession()
    {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是Ajax异步请求
     * 
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
       
        String contentType = request.getContentType();
        if (contentType != null && contentType.indexOf("application/json") != -1)
        {
            return true;
        }
        
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1)
        {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
        {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.endsWithIgnoreCase(uri, ".json") || StringUtils.endsWithIgnoreCase(uri, ".xml"))
        {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.endsWithIgnoreCase(ajax, "json") || StringUtils.endsWithIgnoreCase(ajax, "xml"))
        {
            return true;
        }
        return false;
    }
}
