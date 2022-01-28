package net.koreate.mvc.aop;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.koreate.mvc.vo.MessageVO;

@Component
@Aspect
@Slf4j
public class AOPAdvice {

	public AOPAdvice() {
		log.info("AOP Advice ㄱㄱ");
	}
	
	@Before("execution(void net.koreate.mvc.service.MessageService.addMessage(net.koreate.mvc.vo.MessageVO)) && args(vo)")
	public void addMessage(JoinPoint jp, MessageVO vo) {
		log.debug("-- START ADD MESSAGE --");
		log.debug("-- {} --",vo.toString());
		log.debug("--- END ADD MESSAGE ---");
	}
	
	// 전처리
	@Before("execution(* net.koreate.mvc.service.MessageService.*(..))")
	public void startLog(JoinPoint jp) throws RuntimeException{
		log.info("--------------------------------");
		log.info("------------START LOG-----------");
		log.info("target : {}", jp.getTarget());
		log.info("type : {}", jp.getKind());
		log.info("parameters : {}",Arrays.toString(jp.getArgs()));
		log.info("name : {}"+jp.getSignature().getName());
		log.info("start time : {}", new SimpleDateFormat("HH:mm:ss").format(new Date()));
		log.info("--------------------------------");
	}
	
	// 후처리
	@After("execution(* net.koreate.mvc.service.*.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("--------------------------------");
		log.info("--------START AFTER LOG---------");
		log.info("end time : {}", new SimpleDateFormat("HH:mm:ss").format(new Date()));
		log.info("---------END AFTER LOG----------");
	}
	
	@AfterThrowing(value="execution(* net.koreate.mvc.service.*.*(..))", throwing="exception")
	public void endThrowingLog(JoinPoint jp, Exception exception) {
		log.info("----START AFTER THROWING LOG----");
		log.warn("exception value : {}",exception.getMessage());
		log.info("-----END AFTER THROWING LOG-----");
	}
	
	@AfterReturning(pointcut="execution(!void net.koreate.mvc.service.*.*(..))", returning = "returnValue")
	public void returnLog(JoinPoint jp , Object returnValue) {
		log.info("------Start AFTER RETURNING-----");
		log.info("returnValue : {}",returnValue.toString());
		log.info("-------End AFTER RETURNING------");
	}
	
	// Around 전 후 처리
	@Around("execution(* net.koreate.mvc.controller.HomeController.home(..)) && args(request)")
	public Object controllerLog(ProceedingJoinPoint pjp, HttpServletRequest request) throws Throwable{
		log.info("--------------------------------");
		log.info("----START AROUND CONTROLLER-----");
		log.info("IP : "+request.getRemoteAddr());
		long startTime = System.currentTimeMillis();
		// target method 실행되는 시점
		Object o = pjp.proceed();
		if(o != null) {
			log.warn(o.toString());
		}
		long endTime = System.currentTimeMillis();
		log.info("work time : "+(endTime - startTime));
		log.info("-----END AROUND CONTROLLER------");
		return o;
	}
}
