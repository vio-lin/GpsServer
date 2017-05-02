package com.carPature.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.*;

import org.junit.Test;

import com.carcarPature.utils.GenCRC;
import com.carcarPature.utils.Utils;

public class DataReceiver extends Thread {
	// 设置服务器的端口
	private int port = 81;
	private ServerSocket serverSocket;
	private ExecutorService executorService;// 线程池
	private final int POOL_SIZE = 10;// 单个CPU线程池大小
	
	public static final int MAX_PACKAGR_LEN = 4096; 

	//GT202的协议报
	public static byte[] PEOTOCAL_CODE= new byte[]{
		0x01,//login information
		0x22,//location information
		0x13,//state information
		0x15,//String information
		0x26,//alarm data 
		0x27,//Time Zone
		0x2A,//Gprs,phone number check information
		(byte) 0x80// Server send message to the client
	}; 
	//GT700的协议包
	public static byte[] ProTOCAL_CODE = new byte[]{
		//登录包	
		0x01,
		//GPS定位包（UTC）	
		0x22,
		//心跳包
		0x23,
		//终端在线指令回复	
		0x21,
		//报警数据（UTC）	
		0x26,
		//GPS地址请求包（UTC）	
		0x2A,
		//LBS多基站扩展信息包	
		0x28,
		//LBS地址请求包	
		0x17,
		(byte) //在线指令	
		0x80,
		(byte) //校时包	
		0x8A,
		(byte) //信息传输通用包	
		0x94,
		//中文地址回复包	
		0x17,
		(byte) //英文地址回复包	
		0x97,

	};
	public DataReceiver() throws IOException {
		serverSocket = new ServerSocket(port);
		// Runtime的availableProcessor()方法返回当前系统的CPU数目.
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
		System.out.println("服务器启动");
	}

	@Override
	public void run() {
		// int count = 1;
		while (true) {
			Socket socket = null;
			try {
				// 接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
				socket = serverSocket.accept();
				executorService.execute(new Handler(socket));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new DataReceiver().start();
	}

	class Handler implements Runnable {
		private Socket socket;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		private PrintWriter getWriter(Socket socket) throws IOException {
			OutputStream socketOut = socket.getOutputStream();
			return new PrintWriter(socketOut, true);
		}

		private BufferedReader getReader(Socket socket) throws IOException {
			InputStream socketIn = socket.getInputStream();
			return new BufferedReader(new InputStreamReader(socketIn));
		}

		public String echo(String msg) { // 返回客户端:pw.println(echo("msgAll:"+msgAll));
			return "echo:" + msg;
		}

		public void run() {
			try {
				System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort()
						+ "socketContent:" + socket.toString());
				// 装饰流BufferedReader封装输入流（接收客户端的流）
				BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
				PrintWriter pw = getWriter(socket);
				System.out.println("try");
				String msg = "";
				String msgAll = "";
				byte[] temp = new byte[1];
				DataInputStream dis = new DataInputStream(bis);
				byte[] bytes = new byte[1]; // 一次读取一个byte
				String ret = "";
				byte[] buff = new byte[MAX_PACKAGR_LEN];
				int j=0;
				while (dis.read(bytes) != -1) {
					//fill the bytes  with byte where the end is 0x0D 0x0A
					buff[j++] = bytes[0];
					ret += bytesToHexString(bytes) + "";
					//the problem return the result with char  so change to the byte 
					//instrutct  the car imme code  friom byte
					
					if (dis.available() == 0) { // 一个请求
						System.out.println("接受结果："+bytesToHexString(buff));
						byte[] response = solve(buff,j);
						j=0;
						System.out.println("返回结果："+bytesToHexString(response));
						bos.write(response);
						bos.flush();
						doSomething(ret);
						ret="";
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// System.out.println(2);
				try {
					if (socket != null)
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private byte[] solve(byte[] buff,int length) {
			
			// TODO Auto-generated method stub
			//the method is aim to solve the buff
			//first do the rc code check
			byte[] data = new byte[length-6];
			System.arraycopy(buff, 2, data, 0, length-6);
			System.out.println("数据位"+bytesToHexString(data));
			char ch = GenCRC.getCrc16(data);
			//Cyclic Redundancy Check code is the last 4 byte of the buff
			//check the msaggele
			String a  = Integer.toHexString(ch+0);
			if(a.length()<4){
				a = "0"+a;
			}
			byte[] b = new byte[2];
			System.arraycopy(buff, length-4,b, 0, 2);
			String c = bytesToHexString(b);
			System.out.println("初次校验"+c+"="+a);
			//拿到Crc 中的byte数组  转换成int
			if(!c.equals(a)){
				System.out.println("校验失败 消息损坏");
				return "exception".getBytes();
			}
			byte[] response = null;
			byte protocolcode = buff[3];
			switch (protocolcode+0) {
			case 0x01: response = doLogin(buff);
				break;
			case 0x8a : response = checktime(buff);
			break;
			default :
				break;
			}
			return response;
		}

		private byte[] doLogin(byte[] buff) {
			// TODO Auto-generated method stub
			// instruct thr IMME code from byte
			//pass the crc check and do next
			//终端id 的号码解析 
			byte[] IMEI = new byte[8];
			System.arraycopy(buff, 4, IMEI,0, 8);
			System.out.println("IME码ID"+bytesToHexString(IMEI));
			byte[] type = new byte[2];
			System.arraycopy(buff, 12, type,0, 2);
			System.out.println("类型识别码:"+bytesToHexString(type));
			byte[] timezoo = new byte[2];
			System.arraycopy(buff, 14, timezoo,0, 2);
			byte high = timezoo[0];
			byte low = timezoo[1];
			int west = low&0x3;
			int qu = ((high&0xff)*16+(low>>>4))/100;
			
			System.out.println(west==1?"西时区":"东时区"+qu+"区域");
			
			System.out.println("处理结果或过程中的一些结果");
			StringBuilder strb = new StringBuilder();
			strb.append("7878");
			strb.append("0501");
			strb.append("0005");
//			char ch = GenCRC.getCrc16(hexStringToBytes("05011122460"));
			char ch = GenCRC.getCrc16(hexStringToBytes("05010005"));
			String Crc = Integer.toHexString(ch+0).toUpperCase();
			strb.append(Crc);
			System.out.println("生成的CRC:"+Crc);
			strb.append("0D0A");
			byte[] response = hexStringToBytes(strb.toString()); 
			return response;
		}
	}

	public static void doSomething(String ret) {
		System.out.println(new Date(System.currentTimeMillis())+"one message :"+ret+" ");
	}
	
	public byte[] checktime(byte[] buff) {
		// TODO Auto-generated method stub
		byte[] response=null;
		StringBuilder sb = new StringBuilder();
		sb.append("7878");
		sb.append("0B");
		//转换date到数
		return response;
	}

	/** Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * @param src byte[] data
	 * @return hex string
	 */   

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

   /**
    * Convert hex string to byte[]
    * @param hexString the hex string
    * @return byte[]
    */
   public static byte[] hexStringToBytes(String hexString) {
       if (hexString == null || hexString.equals("")) {
           return null;
       }
       hexString = hexString.toUpperCase();
       int length = hexString.length() / 2;
       char[] hexChars = hexString.toCharArray();
       byte[] d = new byte[length];
       for (int i = 0; i < length; i++) {
           int pos = i * 2;
           d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
       }
       return d;
   }
   /**
    * Convert char to byte
    * @param c char
    * @return byte
    */
    private static byte charToByte(char c) {
       return (byte) "0123456789ABCDEF".indexOf(c);
   }
	// char转byte
	private byte[] getBytes (char[] chars) {
	   Charset cs = Charset.forName ("UTF-8");
	   CharBuffer cb = CharBuffer.allocate (chars.length);
	   cb.put (chars);
	   cb.flip ();
	   ByteBuffer bb = cs.encode (cb);
	  
	   return bb.array();
	 }
	private byte[] toBytes(char[] chars) {
	    CharBuffer charBuffer = CharBuffer.wrap(chars);
	    ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
	    byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
	            byteBuffer.position(), byteBuffer.limit());
	    Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
	    Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
	    return bytes;
	}
	@Test
	public void testgetbyte() throws UnsupportedEncodingException{
		//获取
		char c =0xffff;
		System.out.println(c+1);
		System.out.println("这个字符是"+c);
		String str = c+"";
		byte[] bytes = str.getBytes();
		System.out.println(bytesToHexString(toBytes(new Character(c).toString().toCharArray())));
		System.out.println("16进制"+Integer.toHexString(-85));
		System.out.println();
		
		System.out.println(Character.SIZE);
		System.out.println(Short.SIZE);
		System.out.println(Integer.SIZE);
		System.out.println(Long.SIZE);
	}
}
