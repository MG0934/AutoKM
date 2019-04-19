package com.tryrun.test.hook;

import com.sun.jna.examples.win32.W32API.WPARAM;

public class MSEvent {
	public int state;
	public int mCode;
	public int x;
	public int y;
	public MSEvent(int nCode,WPARAM wParam,HOOKSTRUCT info){
		this.state=nCode;
		this.mCode=wParam.intValue();
		this.x=info.pt.x;
		this.y=info.pt.y;
	}
}
