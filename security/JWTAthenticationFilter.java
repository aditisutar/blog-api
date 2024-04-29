package com.blogappapi.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
//this is the authorization filter, runs after aunthentication
//this class will validate if the already generated token is valid or not and will run for every request
@Component
public class JWTAthenticationFilter extends OncePerRequestFilter {
    Logger logger= LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader=request.getHeader("Authorization");
        logger.info(" Header.......................... :  {}", requestHeader);
        String token=null;
        String username=null;
        if(requestHeader!=null && requestHeader.startsWith("Bearer ")){
            token=requestHeader.substring(7);
            logger.info("token:   "+token);
            try{
                username=this.jwtHelper.extractUsername(token);
                logger.info("username:  "+username);
            }
            catch (IllegalArgumentException e){
                logger.info("Illegal argument while fetching the username!!");
                e.printStackTrace();
            }
            catch (ExpiredJwtException e){
                logger.info("Token expired");
                e.printStackTrace();
            }
            catch(MalformedJwtException e){
                logger.info("Token has been altered!! This is not the original token");
                e.printStackTrace();
            }
            catch (Exception e){
                logger.info("exception occured!!!");
                e.printStackTrace();
            }
        }
        else{
            logger.info("Invalid header value");
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken= this.jwtHelper.validateToken(token, userDetails);
            if(validateToken){
                UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else{
                logger.info("Validation failed");
            }
        }
        filterChain.doFilter(request,response);
    }
}
