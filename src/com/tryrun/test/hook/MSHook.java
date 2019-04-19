package com.tryrun.test.hook;




import com.sun.jna.examples.win32.Kernel32;
import com.sun.jna.examples.win32.User32;
import com.sun.jna.examples.win32.User32.HHOOK;
import com.sun.jna.examples.win32.User32.HOOKPROC;
import com.sun.jna.examples.win32.User32.MSG;
import com.sun.jna.examples.win32.W32API.HMODULE;
import com.sun.jna.examples.win32.W32API.LRESULT;
import com.sun.jna.examples.win32.W32API.WPARAM;

public class MSHook  {
	public static final int WM_MOUSEMOVE = 512;
	public static final int WM_LBUTTONDOWN = 513;
	public static final int WM_LBUTTONUP = 514;
	public static final int WM_RBUTTONDOWN = 516;
	public static final int WM_RBUTTONUP = 517;
	public static final int WM_MBUTTONDOWN = 519;
	public static final int WM_MBUTTONUP = 520;
	private static HHOOK hhk;
	private static LowLevelProc mouseHook;
	private final User32 lib = User32.INSTANCE;
	public static boolean isHooked=false;
	private MouseListener kl;
	private static MSHook instance;
	public static MSHook getInstance(){
		if(instance==null){
			instance=new MSHook();
		}
		
		return instance;
	}
	private MSHook(){
		
	}
	
	public void addKBListener(MouseListener kl){
		this.kl=kl;
	}
	

	
	
	public  void install() {
		
		isHooked=true;
		Runnable r=new Runnable(){

			@Override
			public void run() {
				HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
				mouseHook = new LowLevelProc() {
					public LRESULT callback(int nCode, WPARAM wParam,
							HOOKSTRUCT info) {
						int rt=kl.acceptEvent(new MSEvent(nCode, wParam, info));
						
						return rt==1?new LRESULT(1):lib.CallNextHookEx(hhk, nCode, wParam,info.hwnd );
					}
				};
				hhk = lib.SetWindowsHookEx(User32.WH_MOUSE_LL, mouseHook, hMod, 0);
				
				int result;
				MSG msg = new MSG();
				while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
					if (result == -1) {
						System.err.println("error in get message");
						break;
					} else {
						System.err.println("got message");
						lib.TranslateMessage(msg);
						lib.DispatchMessage(msg);
					}
				}
				
			}
			
		};
		new Thread(r).start();
		
		
	}

	public void unInstall() {
		lib.UnhookWindowsHookEx(hhk);
		isHooked=false;
		this.kl=null;
	}
	
	

	public interface LowLevelProc extends HOOKPROC {
		LRESULT callback(int nCode, WPARAM wParam, HOOKSTRUCT lParam);
	}

//	public class Point extends Structure {
//		public class ByReference extends Point implements Structure.ByReference {
//		};
//		public com.sun.jna.NativeLong x;
//		public com.sun.jna.NativeLong y;
//	}


}