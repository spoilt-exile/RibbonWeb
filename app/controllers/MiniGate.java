package controllers;

/**
 * Basic Ribbon gate prototype implementation.
 * @author Nepochatov Stanislav
 */
public final class MiniGate {
    
    public static Boolean isGateReady = false;
    
    public static String gateErrorStr = "Немає з'язку";
    
    private static GateWorker gate = new GateWorker();
    
    private static Boolean initRun = false;
        
    static {
        init();
    }

    public static void init() {
        if (isGateReady && initRun) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                initRun = true;
                while (true) {
                    gate.tryConnect();
                    if (!gate.isAlive) {
                        try {
                            Thread.sleep(60 * 1000);
                        } catch (InterruptedException ex) {}
                    } else {
                        gate.start();
                        String initErr = gate.sendCommandWithCheck("RIBBON_NCTL_INIT:CLIENT,a2,UTF-8");
                        if (initErr != null) {
                            setError(initErr);
                            continue;
                        }
                        String loginErr = gate.sendCommandWithCheck("RIBBON_NCTL_LOGIN:{root},63a9f0ea7bb98050796b649e85481845");
                        if (loginErr != null) {
                            setError(loginErr);
                            continue;
                        }
                        String remoteErr = gate.sendCommandWithCheck("RIBBON_NCTL_SET_REMOTE_MODE:1");
                        if (remoteErr != null) {
                            setError(remoteErr);
                            continue;
                        }
                        isGateReady = true;
                        initRun = false;
                        break;
                    }
                }
            }
            
            private void setError(String error) {
                System.out.println("ERR:" + error);
                gate.closeGate();
                gate = new GateWorker();
                gateErrorStr = error;
                initRun = false;
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException ex) {}
            }
            
        }.start();
    }
	
}
