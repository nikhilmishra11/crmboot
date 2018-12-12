package com.nam.crm.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nam.crm.model.response.OperationResponse.ResponseStatusEnum;
import com.nam.crm.model.response.session.SessionItem;
import com.nam.crm.model.response.session.SessionResponse;
import com.nam.crm.web.identity.TokenUser;
import com.nam.crm.web.identity.TokenUtil;

import lombok.extern.slf4j.Slf4j;

/* This filter maps to /session and tries to validate the username and password */
@Slf4j
public class GenerateTokenForUserFilter extends AbstractAuthenticationProcessingFilter {

	private TokenUtil tokenUtil;

	public GenerateTokenForUserFilter(String urlMapping, AuthenticationManager authenticationManager,
			TokenUtil tokenUtil) {
		super(new AntPathRequestMatcher(urlMapping));
		setAuthenticationManager(authenticationManager);
		this.tokenUtil = tokenUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		try {
			String jsonString = IOUtils.toString(request.getInputStream(), "UTF-8");
			/* using org.json */
			//JSONObject userJSON = new JSONObject(jsonString);
			ObjectMapper userJSON = new ObjectMapper();
		    JsonNode actualObj = userJSON.readTree(jsonString);
		    String username = actualObj.get("username").textValue();
			String password = actualObj.get("password").textValue();
			String browser = request.getHeader("User-Agent") != null ? request.getHeader("User-Agent") : "";
			String ip = request.getRemoteAddr();
			log.info("\nip:{} \nbrowser:{} \n----", ip, browser);

			// final UsernamePasswordAuthenticationToken loginToken = new
			// UsernamePasswordAuthenticationToken("demo", "demo");
			final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
					password);
			return getAuthenticationManager().authenticate(authToken); // This will take to successfulAuthentication or
																		// faliureAuthentication function
		} catch (AuthenticationException e) {
			e.printStackTrace();
			throw new AuthenticationServiceException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getAuthenticationManager().authenticate(null);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication authToken) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authToken);
		/*
		 * JSONObject jsonResp = new JSONObject(); TokenUser tokenUser =
		 * (TokenUser)authToken.getPrincipal(); String newToken =
		 * this.tokenUtil.createTokenForUser(tokenUser);
		 * 
		 * jsonResp.put("token",newToken);
		 * jsonResp.put("firstName",tokenUser.getUser().getFirstName());
		 * jsonResp.put("lastName",tokenUser.getUser().getLastName());
		 * jsonResp.put("email",tokenUser.getUser().getEmail());
		 * jsonResp.put("role",tokenUser.getRole());
		 */

		TokenUser tokenUser = (TokenUser) authToken.getPrincipal();
		SessionResponse resp = new SessionResponse();
		SessionItem respItem = new SessionItem();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String tokenString = this.tokenUtil.createTokenForUser(tokenUser);

		respItem.setFirstName(tokenUser.getUser().getFirstName());
		respItem.setLastName(tokenUser.getUser().getLastName());
		respItem.setUserId(tokenUser.getUser().getUserId());
		respItem.setEmail(tokenUser.getUser().getEmail());
		respItem.setToken(tokenString);
		respItem.setRoles(tokenUser.getRoles());

		resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
		resp.setOperationMessage("Login Success");
		resp.setItem(respItem);
		String jsonRespString = ow.writeValueAsString(resp);

		res.setStatus(HttpServletResponse.SC_OK);
		res.getWriter().write(jsonRespString);
		res.getWriter().flush();
		res.getWriter().close();

	}
}
