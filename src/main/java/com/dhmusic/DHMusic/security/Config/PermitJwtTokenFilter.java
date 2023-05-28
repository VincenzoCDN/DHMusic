package com.dhmusic.DHMusic.security.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class PermitJwtTokenFilter extends JwtTokenFilter{
    private final List<String> excludedPaths;

    public PermitJwtTokenFilter(List<String> excludedPaths) {
        super();
        this.excludedPaths = excludedPaths;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludedPaths.stream().anyMatch(path -> path.equals(request.getServletPath()));
    }

}
