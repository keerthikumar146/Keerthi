package com.sixdee.dms.hierarchy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sixdee.dms.hierarchy.utils.ApplicationConstants;
import com.sixdee.dms.hierarchy.utils.Sequencer;

/**
 * @author balu.s
 *
 */

@Component
public class RequestInterceptor implements HandlerInterceptor {

	private final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

	public static final String XTRANSACTION_ID = "X-TransctionId";
	public static final String XREQUEST_ID = "X-OrderId";
	public static final String XCHANNEL = "X-Channel";// WEB/APP/USSD
	public static final String XUSERID = "X-UserId";// WEB/APP/USSD

	@Autowired
	Sequencer sequencer;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		long startTime = System.currentTimeMillis();

		String channel = request.getHeader(XCHANNEL);
		String requestId = request.getHeader(XREQUEST_ID);
		String userId = request.getHeader(XUSERID);
		String transactionId = sequencer.getSequenceId();

		MDC.put(ApplicationConstants.TXN_ID, transactionId);
		MDC.put(ApplicationConstants.REQUEST_ID, requestId);
		MDC.put(ApplicationConstants.START_TIME, startTime + "");
		MDC.put(ApplicationConstants.CHANNEL, channel);
		
		if (log.isDebugEnabled()) {
			log.debug("Path : {}, Sender : {}, Channel : {}, TransactionId : {}, Start Time : {}",
					request.getRequestURI(), userId, channel, transactionId, startTime);
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {

		long startTime = Long.valueOf(MDC.get(ApplicationConstants.START_TIME));
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;
		if (log.isInfoEnabled())
			log.info("Request Execution Time : " + executeTime + " ms");

		MDC.clear();
	}

}
