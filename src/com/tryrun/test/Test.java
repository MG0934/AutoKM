package com.tryrun.test;

import com.tryrun.test.hook.KBEvent;
import com.tryrun.test.hook.KBHook;
import com.tryrun.test.hook.KeyBoardListener;
import com.tryrun.test.hook.MSEvent;
import com.tryrun.test.hook.MSHook;
import com.tryrun.test.hook.MouseListener;

public class Test {
	public static long dlast=0;
	public static long ulast=0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MSHook msh=MSHook.getInstance();
		msh.addKBListener(new MouseListener(){

			@Override
			public int acceptEvent(MSEvent e) {
				
				System.out.println(e.mCode);
				
				long now =System.currentTimeMillis();
				
				if(e.state>=0&&e.mCode==MSHook.WM_LBUTTONDOWN){
					
					if(now-dlast<80||now-ulast<80){
						
						return 1;
					}
					dlast=now;
				}
				if(e.state>=0&&e.mCode==MSHook.WM_LBUTTONUP){
					if(now-ulast<80){
						return 1;
					}
					ulast=now;
				}
				
				return 0;
			}
			
		});
		
		KBHook kbhook = KBHook.getInstance();
		
		kbhook.addKBListener(new KeyBoardListener() {
			
			@Override
			public void acceptEvent(KBEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e.keyCode);
			}
		});
		
		msh.install();	
		kbhook.install();
	}

}
