package com.chatbot.app.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.chatbot.app.service.AuthService;
import com.chatbot.app.util.AuthUtil;
import com.chatbot.app.vo.ActionOnGoogleAuthVO;
import com.chatbot.app.vo.MerchantAuthorization;
import com.chatbot.app.vo.MerchantLoginVO;
import com.chatbot.app.vo.MerchantVO;
import com.chatbot.app.vo.View;

@Controller
@RequestMapping("/auth")
public class LoginController {

	private static final String ACTION_ON_GOOGLE_AUTH = "actionOnGoogleAuth";

	private static final String BEARER = "Bearer";

	private static final String ACTIVE = "ACTIVE";

	private static Logger logger = Logger.getLogger(LoginController.class);
	
	private AuthService authService;
	@Autowired
	public LoginController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/login")
	public ModelAndView loadLoginPage(@RequestParam(value = "client_id", defaultValue = "") String clientId,
			@RequestParam(value = "redirect_uri", defaultValue = "") String redirectUri,
			@RequestParam(value = "state", defaultValue = "") String state,
			@RequestParam(value = "response_type", defaultValue = "") String resType, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder("Query Params:\n")
				.append(clientId).append("\n")
				.append(redirectUri).append("\n")
				.append(state).append("\n")
				.append(resType);
		logger.info(sb.toString());

		HttpSession session = request.getSession(true);
		ActionOnGoogleAuthVO actionOnGoogleAuth = new ActionOnGoogleAuthVO();
		actionOnGoogleAuth.clientId = clientId;
		actionOnGoogleAuth.redirectUri = redirectUri;
		actionOnGoogleAuth.state = state;
		actionOnGoogleAuth.resType = resType;
		session.setAttribute(ACTION_ON_GOOGLE_AUTH, actionOnGoogleAuth);
		ModelAndView mav = new ModelAndView("master");
		mav.addObject("view", new View("login", "signin-form"));
		return mav;
	}

	@PostMapping("/login")
	public RedirectView loadLoginPage(
			@Valid MerchantLoginVO merchLogin, 
			BindingResult binding, HttpServletRequest request) {
		RedirectView redirectView = new RedirectView();
		if (binding.hasErrors()) {
			redirectView.setStatusCode(HttpStatus.FORBIDDEN);
			return redirectView;
		}
		
		// Authenticate Service
		boolean isAuth = authService.authenticateMerchant(merchLogin);
		
		// Authorization Service
		MerchantVO merchant = null;
		MerchantAuthorization merchAuthData = null;
		if (isAuth) {
			merchAuthData = authService.getAuthorizationData();
			merchant = authService.getAuthMerchant();
		}
		
		String redirectUri = formRedirectUri(request, redirectView, merchant);
		logger.info("Redirect Uri: " + redirectUri);
		redirectView.setUrl(redirectUri);
		return redirectView;

	}

	private String formRedirectUri(HttpServletRequest request, RedirectView redirectView,
			MerchantVO merchant) {
		String merchantAccessToken = AuthUtil.generateMerchantAccessToken(merchant);
		HttpSession session = request.getSession(false);
		ActionOnGoogleAuthVO actionOnGoogle = (ActionOnGoogleAuthVO) session.getAttribute(ACTION_ON_GOOGLE_AUTH);
		actionOnGoogle.redirectUri += String.format("#access_token=%s&token_type=%s&state=%s", merchantAccessToken, BEARER, ACTIVE);
		return actionOnGoogle.redirectUri;
	}
}
