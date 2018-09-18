package cn.sensorsdata.demo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MyHttpURLUtils
{

	public static String getNetStrings(String urlpath)
	{
		/**
		 * 根据传入的url地址，返回一个String字符串,
		 * Get请求，这里用于读取得到json的字符串
		 * */
		BufferedReader br=null;
		try
		{
			HttpURLConnection con=(HttpURLConnection) new URL(urlpath).openConnection();
			con.setDoInput(true);
			//con.setConnectTimeout(1000*8);
			con.connect();
			if(con.getResponseCode()==200)
			{
				br=new BufferedReader(new InputStreamReader(con.getInputStream()));
				String datas="";
				String line;
				while((line=br.readLine())!=null)
				{
					datas+=line;
				}
				
				
				return datas;
			}
		
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			if (br!=null)
			{
			
				try
				{
					br.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 下载图片到手机内存
	 * 
	 * */
	
public static byte[] downLoadImage(String urlpath) {
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			URL url=new URL(urlpath);
			HttpURLConnection con=(HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			
			if (con.getResponseCode()== 200) {
				// 获取内容总长度
				
				long totalLength=con.getContentLength();
				BufferedInputStream bi=new BufferedInputStream(con.getInputStream());
				
				int currentLength = 0;
				int temp = 0;
				byte[] buff = new byte[1024];
				while ((temp = bi.read(buff)) != -1) {
					currentLength += temp;
					int progress = (int) ((1.0f * currentLength / totalLength) * 100);
					//publishProgress用于通知AsyncTask的onProgressUpdate()更新进度
					//publishProgress(progress);
					output.write(buff, 0, temp);
					output.flush();

				}
				return output.toByteArray();

			}

		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (output != null)
					output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	/**
	 * POST请求
	 * */
	
	public static String postNetDatas(String urlpath,Map<String,String> params)
	{
		BufferedOutputStream bo=null;
		BufferedReader br=null;
		try
		{
			HttpURLConnection con=(HttpURLConnection) new URL(urlpath).openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.connect();
			
			//连接之后，向服务器写入POST传递信息
			bo=new BufferedOutputStream(con.getOutputStream());
			Set<String> set=params.keySet();
			Iterator<String> iterator=set.iterator();
			StringBuilder str=new StringBuilder();
			while(iterator.hasNext())
			{
				String key=iterator.next();
				String name=params.get(key);
				str.append(key).append("=").append(name).append("&");
			}
			str=str.deleteCharAt(str.length()-1);
			
			bo.write(str.toString().getBytes());
			bo.flush();
			
			
			if(con.getResponseCode()==200)
			{//响应成功后，读取获得数据
				br=new BufferedReader(new InputStreamReader(con.getInputStream()));
				String datas="";
				String line;
				while((line=br.readLine())!=null)
				{
					datas+=line;
				}
				return datas;
			}
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(br!=null&&bo!=null)
			{
				try
				{
					br.close();bo.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
}
