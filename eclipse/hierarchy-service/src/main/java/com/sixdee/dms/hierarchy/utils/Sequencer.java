package com.sixdee.dms.hierarchy.utils;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Enumeration;

import com.sixdee.dms.utils.service.AbstractSequencer;

/**
 * @author balu.s
 *
 */

public class Sequencer extends AbstractSequencer {
	
	private static final int NODE_ID_BITS = 10;
	private static final long maxNodeId = (1L << NODE_ID_BITS) - 1;

	public Sequencer() {
		super();
	}
	
    private static long createNodeId() {
        long nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for(byte macPort: mac) {
                        sb.append(String.format("%02X", macPort));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (Exception ex) {
            nodeId = (new SecureRandom().nextInt());
        }
        nodeId = nodeId & maxNodeId;
        return nodeId;
    }

	@Override
	public void calculateLimit() {
		long nodeId = createNodeId();
		setSequencerLimit(nodeId);
	}
    
}
