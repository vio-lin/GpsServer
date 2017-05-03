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
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.*;

import javax.swing.RepaintManager;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.carcarPature.utils.GenCRC;
import com.carcarPature.utils.Utils;

public class DataReceiver extends Thread {
	Logger logger = Logger.getLogger(DataReceiver.class);
	// 设置服务器的端口
	private int port = 81;
	private ServerSocket serverSocket;
	private ExecutorService executorService;// 线程池
	private final int POOL_SIZE = 10;// 单个CPU线程池大小

	public static final int MAX_PACKAGR_LEN = 4096;
	public static int sequence = 0;
	// GT202的协议报
	public static byte[] PEOTOCAL_CODE = new byte[] { 0x01,// login information
			0x22,// location information
			0x13,// state information
			0x15,// String information
			0x26,// alarm data
			0x27,// Time Zone
			0x2A,// Gprs,phone number check information
			(byte) 0x80 // Server send message to the client
	};
	// GT700的协议包
	public static byte[] ProTOCAL_CODE = new byte[] {
			// 登录包
			0x01,
			// GPS定位包（UTC）
			0x22,
			// 心跳包
			0x23,
			// 终端在线指令回复
			0x21,
			// 报警数据（UTC）
			0x26,
			// GPS地址请求包（UTC）
			0x2A,
			// LBS多基站扩展信息包
			0x28,
			// LBS地址请求包
			0x17, (byte) // 在线指令
			0x80, (byte) // 校时包
			0x8A, (byte) // 信息传输通用包
			0x94,
			// 中文地址回复包
			0x17, (byte) // 英文地址回复包
			0x97,

	};

	public DataReceiver() throws IOException {
		serverSocket = new ServerSocket(port);
		// Runtime的availableProcessor()方法返回当前系统的CPU数目.
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * POOL_SIZE);
		logger.info("服务器启动");
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
		private String IMEI;
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
				logger.info("New connection accepted "
						+ socket.getInetAddress() + ":" + socket.getPort());
				// 装饰流BufferedReader封装输入流（接收客户端的流）
				BufferedInputStream bis = new BufferedInputStream(
						socket.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(
						socket.getOutputStream());
				PrintWriter pw = getWriter(socket);
				System.out.println("try");
				String msg = "";
				String msgAll = "";
				byte[] temp = new byte[1];
				DataInputStream dis = new DataInputStream(bis);
				byte[] bytes = new byte[1]; // 一次读取一个byte
				String ret = "";
				byte[] buff = new byte[MAX_PACKAGR_LEN];
				int j = 0;
				while (dis.read(bytes) != -1) {
					// fill the bytes with byte where the end is 0x0D 0x0A
					buff[j++] = bytes[0];
					// the problem return the result with char so change to the
					// byte
					// instrutct the car imme code friom byte

					if (dis.available() == 0) { // 一个请求
						logger.info("接受结果：" + bytesToHexString(buff));
						byte[] response = solve(buff, j);
						j = 0;
						logger.info("返回结果：" + bytesToHexString(response));
						bos.write(response);
						bos.flush();
						// doSomething(ret);
						buff = new byte[MAX_PACKAGR_LEN];
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

		private byte[] solve(byte[] buff, int length) {

			// TODO Auto-generated method stub
			// the method is aim to solve the buff
			// first do the rc code check
			byte[] data = new byte[length - 6];
			System.arraycopy(buff, 2, data, 0, length - 6);
			logger.info("数据位" + bytesToHexString(data));
			char ch = GenCRC.getCrc16(data);
			// Cyclic Redundancy Check code is the last 4 byte of the buff
			// check the msaggele
			String a = Integer.toHexString(ch + 0);
			if (a.length() < 4) {
				a = "0" + a;
			}
			byte[] b = new byte[2];
			System.arraycopy(buff, length - 4, b, 0, 2);
			String c = bytesToHexString(b);
			System.out.println("初次校验" + c + "=" + a);
			// 拿到Crc 中的byte数组 转换成int
			if (!c.equals(a)) {
				logger.error("校验失败 消息损坏");
				return "exception".getBytes();
			}
			byte[] response = new byte[] {};
			byte protocolcode = buff[3];
			switch (protocolcode & 0xff) {
			//GT710登录包
			case 0x01:
				response = doLogin(buff);
				break;
			//GT710 数据校验把包
			case 0x8a:
				response = dochecktime(buff);
				break;
			//GT710 心跳包
			case 0x23:
				response = doheartbeat(buff);
				break;
			//GT710 Loc包
			case 0x28:
				response = doLBSRequest(buff);
				break;
			//GT710GPS包
			case 0x22:
				response = doGps(buff);
				break;
			default:
				break;
			}
			return response;
		}

		private byte[] doLogin(byte[] buff) {
			// TODO Auto-generated method stub
			// instruct thr IMME code from byte
			// pass the crc check and do next
			// 终端id 的号码解析
			byte[] IMEI = new byte[8];
			System.arraycopy(buff, 4, IMEI, 0, 8);
			logger.info("IME码ID" + bytesToHexString(IMEI));
			byte[] type = new byte[2];
			System.arraycopy(buff, 12, type, 0, 2);
			logger.info("类型识别码:" + bytesToHexString(type));
			byte[] timezoo = new byte[2];
			System.arraycopy(buff, 14, timezoo, 0, 2);
			byte high = timezoo[0];
			byte low = timezoo[1];
			int west = low & 0x3;
			int qu = ((high & 0xff) * 16 + (low >>> 4)) / 100;
			System.out.println(west == 1 ? "西时区" : "东时区" + qu + "区域");
			System.out.println("处理结果或过程中的一些结果");
			StringBuilder strb = new StringBuilder();
			strb.append("7878");
			strb.append("0501");
			strb.append("0005");
			// char ch = GenCRC.getCrc16(hexStringToBytes("05011122460"));
			char ch = GenCRC.getCrc16(hexStringToBytes("05010005"));
			String Crc = Integer.toHexString(ch + 0).toUpperCase();
			strb.append(Crc);
			System.out.println("生成的CRC:" + Crc);
			strb.append("0D0A");
			byte[] response = hexStringToBytes(strb.toString());
			return response;
		}
	}

	public static void doSomething(String ret) {
		System.out.println(new Date(System.currentTimeMillis())
				+ "one message :" + ret + " ");
	}

	public byte[] doLBSRequest(byte[] buff) {
		// TODO Auto-generated method stub
		byte[] request = new byte[] {};
		// 无需回复
		String date = + (buff[4]+2000)+"年"  +  buff[5]+"月" + buff[6]+ "日" +buff[7] + "小时"
				+  buff[8] + "分" +buff[9]+ "秒" ;
		// 移动用户所属国家代号
		String MCC = (buff[10] & 0xff) * 256 + buff[11] + " ";
		// 移动网号码Mobile Network Code(MNC)
		String MNC = (buff[12] & 0xff) + " ";
		// LAC移动号码
		String LAC = (buff[13] & 0xff) * 256 + buff[14] + " ";
		// 基站地址 CI
		String CI = buff[15] + " " + buff[16] + "" + buff[17];
		String RSSI = buff[18] + "";
		System.out.println("国家代码号" + MCC + "时间" + date + "移动网号码" + MNC);
		return request;
	}

	public byte[] doheartbeat(byte[] buff) {
		// TODO Auto-generated method stub
		byte[] response;
		byte termInfo = buff[4];
		if ((termInfo & 0x80) ==0x80) {
			System.out.println("油电断开");
		} else {
			System.out.println("油电接通");
		}
		if ((termInfo & 0x40) == 0x40) {
			System.out.println("Gps已定位");
		} else {
			System.out.println("GPS未定位");
		}
		if ((termInfo & 0x01) == 0x01) {
			System.out.println("设防");
		} else {
			System.out.println("撤防");
		}
		if ((termInfo & 0x02) == 0x02) {
			System.out.println("ACC高");
		} else {
			System.out.println("ACC低");
		}
		if ((termInfo & 0x04) == 0x04) {
			System.out.println("已接电源充电");
		} else {
			System.out.println("未接电源充电");
		}
		byte[] butter = new byte[2];
		System.arraycopy(buff, 5, butter, 0, 2);
		int v = (butter[0] & 0xff) * 256 + (butter[1] & 0xff);
		System.out.println("电池的容量是" + v / 100.0);
		byte Gsm = buff[7];
		switch (Gsm) {
		case 0x00:
			System.out.println("无信号");
			break;
		case 0x01:
			System.out.println("信号极弱");
			break;
		case 0x02:
			System.out.println("信号较弱");
			break;
		case 0x03:
			System.out.println("信号良好");
			break;
		case 0x04:
			System.out.println("信号强");
			break;
		default:
			break;
		}
		// 扩展英文状态
		byte Lan = buff[9];
		switch (Lan) {
		case 0x01:
			System.out.println("中文");
			break;
		case 0x02:
			System.out.println("英文");
			break;
		default:
			break;
		}
		// 重新封装 返回
		StringBuilder sb = new StringBuilder();
		sb.append("7878");
		sb.append("05");
		sb.append("23");
		byte[] seq = new byte[2];
		System.arraycopy(buff, 10, seq, 0, 2);
		sb.append(bytesToHexString(seq));
		System.out.println(bytesToHexString(seq));
		// sb.append("0100");
		String data = sb.substring(4);
		System.out.println("校验的位置" + data);
		char ch = GenCRC.getCrc16(hexStringToBytes(data));
		String Crc = Integer.toHexString(ch + 0).toUpperCase();
		sb.append(Crc);
		sb.append("0D0A");
		response = hexStringToBytes(sb.toString());
		return response;
	}

	private byte[] doGps(byte[] buff) {
		// TODO Auto-generated method stub
		// instruct thr IMME code from byte
		//pass the crc check and do next
		//终端id 的号码解析 
		byte[] time = new byte[6];
		System.arraycopy(buff, 4, time, 0, 6);
		int year = time[0] & 0x00ff + 2000;
		int month = time[1] & 0x00ff ;
		int date = time[2] & 0x00ff ;
		int hour = time[3] & 0x00ff ;
		int minute = time[4] & 0x00ff ;
		int second = time[5] & 0x00ff ;
		System.out.println("日期时间:"+year+"年"+month+"月"+date+"日       "+hour+"：" +minute+":" +second);
		byte[] GPSNo = new byte[1];
		System.arraycopy(buff, 10, GPSNo, 0, 1);
		System.out.println("GPS信息长度:"+ ((GPSNo[0]&0x00f0)>>>4) +"   GPS信息卫星数:"+ (GPSNo[0]&0x000f));
		byte[] lat = new byte[4];
		System.arraycopy(buff, 11, lat, 0, 4);
		double latNum =  Integer.parseInt(bytesToHexString(lat), 16)/1800000.0;
//		System.out.println("纬度:"+bytesToHexString(lat));
		System.out.println("纬度:"+latNum);
		byte[] lng = new byte[4];
		System.arraycopy(buff, 15, lng, 0, 4);
		double lngNum =  Integer.parseInt(bytesToHexString(lng), 16)/1800000.0;
//		System.out.println("经度:"+bytesToHexString(lng));
		System.out.println("经度:"+lngNum);
		byte[] speed = new byte[1];
		System.arraycopy(buff, 19, speed, 0, 1);
		System.out.println("速度:"+ (speed[0] & 0x00ff));
		byte[] course = new byte[2];
		System.arraycopy(buff, 20, course, 0, 2);
		int direction = course[1]+((course[0]&0x0003)<<4);
		StringBuilder courseS = new StringBuilder();
		if((course[0] & 0x0020)== 0x0020)
			courseS.append("差分GPS、");
		else
			courseS.append("实时GPS、");
		if((course[0] & 0x0010)== 0x0010)
			courseS.append("GPS已定位、");
		else
			courseS.append("GPS未定位、");
		if((course[0] & 0x0008)== 0x0008)
			courseS.append("西经、");
		else
			courseS.append("东经、");
		if((course[0] & 0x0004)== 0x0004)
			courseS.append("北纬、");
		else
			courseS.append("南纬、");
		courseS.append("航向"+direction+"°");
//		System.out.println("航向状态:"+bytesToHexString(course));
		System.out.println("航向状态:" + courseS);

		byte[] MCC = new byte[2];
		System.arraycopy(buff, 22, MCC, 0, 2);
		int MCCNum = Integer.parseInt(bytesToHexString(MCC), 16);
//		System.out.println("国家代码:"+bytesToHexString(MCC));
		System.out.println("国家代码:"+MCCNum);
		byte[] MNC = new byte[1];
		System.arraycopy(buff, 24, MNC, 0, 1);
		int MNCNum = Integer.parseInt(bytesToHexString(MNC), 16);
//		System.out.println("移动网号码:"+bytesToHexString(MNC));
		System.out.println("移动网号码:"+MNCNum);
		byte[] LAC = new byte[2];
		System.arraycopy(buff, 25, LAC, 0, 2);
		int LACNum = Integer.parseInt(bytesToHexString(LAC), 16);
//		System.out.println("位置区码:"+bytesToHexString(LAC));
		System.out.println("位置区码:"+LACNum);
		byte[] Cell_ID = new byte[3];
		System.arraycopy(buff, 27, Cell_ID, 0, 3);
		int Cell_IDNum = Integer.parseInt(bytesToHexString(Cell_ID), 16);
//		System.out.println("移动基站:"+bytesToHexString(Cell_ID));
		System.out.println("移动基站:"+Cell_IDNum);
		byte[] ACC = new byte[1];
		System.arraycopy(buff, 30, ACC, 0, 1);
		if((ACC[0] & 0x0001) == 0x0001)
			System.out.println("ACC状态:高");
		else
			System.out.println("ACC状态:低");
//		System.out.println("ACC状态:"+bytesToHexString(ACC));
		byte[] mode = new byte[1];
		System.arraycopy(buff, 31, mode, 0, 1);
		switch(mode[0]){
		case 0x0000 : System.out.println("数据上报模式:定时上报");
			break;
		case 0x0001 : System.out.println("数据上报模式:定距上报");
			break;
		case 0x0002 : System.out.println("数据上报模式:拐点上传");
			break;
		case 0x0003 : System.out.println("数据上报模式:ACC状态改变上传");
			break;
		case 0x0004 : System.out.println("数据上报模式:从运动变为静止状态后，补传最后一个定位点");
			break;
		case 0x0005 : System.out.println("数据上报模式:网络断开重连后，上报之前最后一个有效上传点");
			break;
		default:
			break;
		}
//		System.out.println("数据上报模式:"+bytesToHexString(mode));
		byte[] GPStype = new byte[1];
		System.arraycopy(buff, 32, GPStype, 0, 1);
		switch(GPStype[0]){
		case 0x0000 : System.out.println("GPS实时补传:实时上传");
			break;
		case 0x0001 : System.out.println("GPS实时补传:补传");
			break;
		default:
			break;
		}
//		System.out.println("GPS实时补传:"+bytesToHexString(GPStype));
		byte[] mileage = new byte[4];
		System.arraycopy(buff, 33, mileage, 0, 4);
		int mileageNum = Integer.parseInt(bytesToHexString(mileage), 16);
//		System.out.println("里程统计:"+bytesToHexString(mileage));
		System.out.println("里程统计:"+mileageNum);
		
		System.out.println("处理结果或过程中的一些结果");
		StringBuilder strb = new StringBuilder();
		strb.append("7878");
		strb.append("0501");
//		char ch = GenCRC.getCrc16(hexStringToBytes("05011122460"));
		char ch = GenCRC.getCrc16(hexStringToBytes("05010005"));
		String Crc = Integer.toHexString(ch+0).toUpperCase();
		strb.append(Crc);
		System.out.println("生成的CRC:"+Crc);
		strb.append("0D0A");
		byte[] response = hexStringToBytes(strb.toString()); 
		return response;
	}

	public byte[] dochecktime(byte[] buff) {
		// TODO Auto-generated method stub
		byte[] response = null;
		StringBuilder sb = new StringBuilder();
		sb.append("7878");
		sb.append("0B");
		sb.append("8A");
		Calendar cal = Calendar.getInstance();
		// 转换成 byte相关 年（1byte）月（1byte）日（1byte）时（1byte）分（1byte）秒（1byte）（转换为十进制）
		// 6位的 byte 使用cal进行处理
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		// 转换为数字
		String time = appendToByte(Integer.toHexString(year % 2000))
				+ appendToByte(Integer.toHexString(month + 1))
				+ appendToByte(Integer.toHexString(day))
				+ appendToByte(Integer.toHexString(hour))
				+ appendToByte(Integer.toHexString(min))
				+ appendToByte(Integer.toHexString(second));
		sb.append(time);
		// 转换为10进制
		String sequence = "0006";
		// 计算校验码的部分
		sb.append(sequence);
		String str = sb.substring(6);
		System.out.println("校验的位置" + str);
		char ch = GenCRC.getCrc16(hexStringToBytes("str"));
		String Crc = Integer.toHexString(ch + 0).toUpperCase();
		sb.append(Crc);
		System.out.println("生成的CRC:" + Crc);
		// 转换date到数
		sb.append("0D0A");
		response = hexStringToBytes(sb.toString());
		return response;
	}

	public String appendToByte(String str) {
		if (str.length() < 2) {
			str = "0" + str;
		}
		return str;
	}

	/**
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            byte[] data
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
	 * 
	 * @param hexString
	 *            the hex string
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
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	// char转byte
	private byte[] getBytes(char[] chars) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);

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
	public void testgetbyte() throws UnsupportedEncodingException {
		// 获取
		char c = 0xffff;
		System.out.println(c + 1);
		System.out.println("这个字符是" + c);
		String str = c + "";
		byte[] bytes = str.getBytes();
		System.out.println(bytesToHexString(toBytes(new Character(c).toString()
				.toCharArray())));
		System.out.println("16进制" + Integer.toHexString(-85));
		System.out.println();

		System.out.println(Character.SIZE);
		System.out.println(Short.SIZE);
		System.out.println(Integer.SIZE);
		System.out.println(Long.SIZE);
	}

}
