package com.tryrun.test.hook;

import com.sun.jna.examples.win32.W32API.WPARAM;

public class KBEvent {
	public int nCode;
	public int state;
	public int keyCode;
	public KBEvent(int nCode,WPARAM wParam,HOOKSTRUCT info){
		this.nCode=nCode;
		this.state=wParam.intValue();
		this.keyCode=info.pt.x;
	}
}
