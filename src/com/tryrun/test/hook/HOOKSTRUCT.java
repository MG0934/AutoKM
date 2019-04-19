package com.tryrun.test.hook;

import com.sun.jna.Structure;
import com.sun.jna.examples.win32.User32;
import com.sun.jna.examples.win32.W32API.LPARAM;

public class HOOKSTRUCT extends Structure {
	
	public User32.POINT pt;
	public LPARAM hwnd;
	public int wHitTestCode;
	public User32.ULONG_PTR dwExtraInfo;
}
