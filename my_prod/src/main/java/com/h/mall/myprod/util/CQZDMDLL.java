package com.h.mall.myprod.util;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.jna.Library;
import com.sun.jna.Native;

import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 开发者赚钱平台:www.uuwise.com
 * 更多函数细节：dll.uuwise.com
 * 
 */

public class CQZDMDLL
{
	public static String	USERNAME	= "lihui113";							//UU用户名
	public static String	PASSWORD	= "c19891013";							//UU密码
	public static String	DLLPATH		= "D:\\image\\UUWiseHelper-x64";					//DLL
	public static String	IMGPATH		= "D:\\image\\randCode.png";
	public static int		SOFTID		= 95357;								//软件ID
	public static String	SOFTKEY		= "4e2c857e27554c9d952b01258cc37de3";	//软件KEY
	public static String	UUDLLMD5	="B360536FF54ED30091711AD45336B14E";	//优优云DLL MD5,下面两个是校验MD5用到的参数
	
	/*	优优云DLL 文件MD5值校验
	 *  用处：近期有不法份子采用替换优优云官方dll文件的方式，极大的破坏了开发者的利益
	 *  用户使用替换过的DLL打码，导致开发者分成变成别人的，利益受损，
	 *  所以建议所有开发者在软件里面增加校验官方MD5值的函数
	 *  如何获取文件的MD5值，通过下面的GetFileMD5(文件)函数即返回文件MD5
	 */
	//MD5校验函数开始
	
	public static String GetFileMD5(String inputFile) throws IOException {
		int bufferSize = 256 * 1024;
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;
		try {
			MessageDigest messageDigest =MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(inputFile);
			digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
			byte[] buffer =new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0);
			messageDigest= digestInputStream.getMessageDigest();
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}finally {
			try {
				digestInputStream.close();
			}catch (Exception e) {
				
			}try {
				fileInputStream.close();
			}catch (Exception e) {
				
			}
		}
	}
	public static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
		char[] resultCharArray =new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b& 0xf];
		}
		return new String(resultCharArray);
	}
	
	//MD5校验函数结束
	
	public interface DM extends Library
	{
		DM	INSTANCE	= (DM) Native.loadLibrary(DLLPATH, DM.class);		
		public int uu_reportError(int id);		
		public int uu_setTimeOut(int nTimeOut);
		public void uu_setSoftInfoA(int softId, String softKey);
		public int uu_loginA(String UserName, String passWord);
		public int uu_getScoreA (String UserName, String passWord);
		public int uu_recognizeByCodeTypeAndBytesA (byte[] picContent, int piclen, int codeType, byte[] returnResult);
		public void uu_getResultA(int nCodeID,String pCodeResult);
	}
	
	
	
	
	public static void main(String[] args) throws Exception
	{
		/*	优优云DLL 文件MD5值校验
		 *  用处：近期有不法份子采用替换优优云官方dll文件的方式，极大的破坏了开发者的利益
		 *  用户使用替换过的DLL打码，导致开发者分成变成别人的，利益受损，
		 *  所以建议所有开发者在软件里面增加校验官方MD5值的函数
		 *  如何获取文件的MD5值，通过下面的GetFileMD5(文件)函数即返回文件MD5
		 */

		//校验DLL文件的MD5值是否正确
		String md5=GetFileMD5(DLLPATH+".dll");	//获取DLL文件的MD5值，初次可以使用此函数得到MD5，然后放到变量中
		System.out.println(md5);	//显示MD5值,第一次下载新dll可以用此函数获取DLL的MD5值。
		
		if(!md5.equalsIgnoreCase(UUDLLMD5)){	//判断DLL文件是否被替换
			System.out.println("对不起，您替换了图片识别文件。请下载官方原版。");
			System.exit(0);	//退出程序
		}
		//以上代码只做了简单处理，更复杂的处理需要作者再做一下处理。
				
		int userID;
		DM.INSTANCE.uu_setSoftInfoA(SOFTID, SOFTKEY);	//setsoftinfo和login函数只需要执行一次，就可以无限执行图片识别函数了
		
		userID=DM.INSTANCE.uu_loginA(USERNAME, PASSWORD);
		if(userID>0){
			System.out.println("userID is:"+userID);
			System.out.println("user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD)); 
			
			File f = new File(IMGPATH);
			byte[] by = toByteArray(f);

			byte[] resultBtye=new byte[30];		//为识别结果申请内存空间
			int codeID=DM.INSTANCE.uu_recognizeByCodeTypeAndBytesA(by, by.length, 1, resultBtye);	//调用识别函数,resultBtye为识别结果
			String  resultResult = new String(resultBtye,"UTF-8");
			resultResult=resultResult.trim();
			System.out.println("this img codeID:"+codeID);
			System.out.println("return recongize Result:"+resultResult); 
			
			/*
				//测试报错 开始，真实环境不可这样用,需要在实际验证码打错的情况下，执行报错函数进行报错,恶意报错会导致封号
				// 那么如何知道是否打错呢？
				// 一般来说打错码，服务器都会有相应的响应。
				
				System.out.println("报错前 user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD));
				int reportErrorResult;
				reportErrorResult=DM.INSTANCE.uu_reportError(codeID);
				if(reportErrorResult==0)
				{
					System.out.println("报错后 user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD));
				}else
				{
					System.out.println("报错失败，原因未知");
				}
				//测试报错 开始，真实环境不可这样用,需要在实际验证码打错的情况下，执行报错函数进行报错,恶意报错会导致封号
			*/
		}else{
			System.out.println("登录失败，错误代码为："+userID);	//错误代码请对应dll.uuwise.com各函数值查看
		}  
	}

	
	public static byte[] toByteArray(File imageFile) throws Exception
	{
		BufferedImage img = ImageIO.read(imageFile);
		ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());
		try
		{
			ImageIO.write(img, "jpg", buf);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return buf.toByteArray();
	}

	public static byte[] toByteArrayFromFile(String imageFile) throws Exception
	{
		InputStream is = null;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try
		{
			is = new FileInputStream(imageFile);

			byte[] b = new byte[1024];

			int n;

			while ((n = is.read(b)) != -1){
				out.write(b, 0, n);
			}// end while

		} catch (Exception e)
		{
			throw new Exception("System error,SendTimingMms.getBytesFromFile", e);
		} finally
		{

			if (is != null)
			{
				try
				{
					is.close();
				} catch (Exception e)
				{}// end try
			}// end if

		}// end try
		return out.toByteArray();
	}
	
	public static String getCode() throws Exception{
		/*	优优云DLL 文件MD5值校验
		 *  用处：近期有不法份子采用替换优优云官方dll文件的方式，极大的破坏了开发者的利益
		 *  用户使用替换过的DLL打码，导致开发者分成变成别人的，利益受损，
		 *  所以建议所有开发者在软件里面增加校验官方MD5值的函数
		 *  如何获取文件的MD5值，通过下面的GetFileMD5(文件)函数即返回文件MD5
		 */

		//校验DLL文件的MD5值是否正确
		String md5=GetFileMD5(DLLPATH+".dll");	//获取DLL文件的MD5值，初次可以使用此函数得到MD5，然后放到变量中
		//System.out.println(md5);	//显示MD5值,第一次下载新dll可以用此函数获取DLL的MD5值。
		
		if(!md5.equalsIgnoreCase(UUDLLMD5)){	//判断DLL文件是否被替换
			System.out.println("对不起，您替换了图片识别文件。请下载官方原版。");
			System.exit(0);	//退出程序
		}
		//以上代码只做了简单处理，更复杂的处理需要作者再做一下处理。
				
		int userID;
		DM.INSTANCE.uu_setSoftInfoA(SOFTID, SOFTKEY);	//setsoftinfo和login函数只需要执行一次，就可以无限执行图片识别函数了
		
		userID=DM.INSTANCE.uu_loginA(USERNAME, PASSWORD);
		if(userID>0){
//			System.out.println("userID is:"+userID);
//			System.out.println("user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD)); 
			
			File f = new File(IMGPATH);
			byte[] by = toByteArray(f);

			byte[] resultBtye=new byte[30];		//为识别结果申请内存空间
			int codeID=DM.INSTANCE.uu_recognizeByCodeTypeAndBytesA(by, by.length, 1, resultBtye);	//调用识别函数,resultBtye为识别结果
			String  resultResult = new String(resultBtye,"UTF-8");
			resultResult=resultResult.trim();
//			System.out.println("this img codeID:"+codeID);
//			System.out.println("return recongize Result:"+resultResult);
			return resultResult;
			
			/*
				//测试报错 开始，真实环境不可这样用,需要在实际验证码打错的情况下，执行报错函数进行报错,恶意报错会导致封号
				// 那么如何知道是否打错呢？
				// 一般来说打错码，服务器都会有相应的响应。
				
				System.out.println("报错前 user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD));
				int reportErrorResult;
				reportErrorResult=DM.INSTANCE.uu_reportError(codeID);
				if(reportErrorResult==0)
				{
					System.out.println("报错后 user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD));
				}else
				{
					System.out.println("报错失败，原因未知");
				}
				//测试报错 开始，真实环境不可这样用,需要在实际验证码打错的情况下，执行报错函数进行报错,恶意报错会导致封号
			*/
		}else{
			System.out.println("登录失败，错误代码为："+userID);	//错误代码请对应dll.uuwise.com各函数值查看
			return "";
		}  
	}

}
